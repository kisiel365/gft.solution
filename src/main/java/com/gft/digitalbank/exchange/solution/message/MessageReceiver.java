package com.gft.digitalbank.exchange.solution.message;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gft.digitalbank.exchange.solution.message.handlers.CancellationOrderMessageHandler;
import com.gft.digitalbank.exchange.solution.message.handlers.MessageHandler;
import com.gft.digitalbank.exchange.solution.message.handlers.ModificationOrderMessageHandler;
import com.gft.digitalbank.exchange.solution.message.handlers.PositionOrderMessageHandler;
import com.gft.digitalbank.exchange.solution.message.handlers.ShutdownNotificationMessageHandler;

public class MessageReceiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

	private Map<String, MessageHandler> mappings;
	private String queueName;
	private MessageCollector messageCollector;

	public MessageReceiver(String queueName, MessageCollector messageCollector) {
		this.messageCollector = messageCollector;
		this.queueName = queueName;
		mappings = new HashMap<>();
		mappings.put("ORDER", new PositionOrderMessageHandler(messageCollector));
		mappings.put("SHUTDOWN_NOTIFICATION", new ShutdownNotificationMessageHandler());
		mappings.put("MODIFICATION", new ModificationOrderMessageHandler(messageCollector));
		mappings.put("CANCEL", new CancellationOrderMessageHandler(messageCollector));
	}

	public void start() {
		Connection connection = null;
		MessageConsumer consumer = null;
		Session session = null;
		try {
			connection = obtainConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(queueName);
			consumer = session.createConsumer(queue);
			connection.start();
			boolean shouldContinue = true;
			while (shouldContinue)
				shouldContinue = receiveMessage(consumer);
		} catch (Exception e) {
			LOGGER.error("Problem obtaining connection to queue", e);
		} finally {
			tryToClean(connection, consumer, session);
			messageCollector.brokerFinished(queueName);
		}
	}

	private Connection obtainConnection() throws NamingException, JMSException {
		Context context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		return connectionFactory.createConnection();
	}

	private boolean receiveMessage(MessageConsumer consumer) {
		try {
			TextMessage mqMessage = (TextMessage) consumer.receive();
			String type = mqMessage.getStringProperty("messageType");
			MessageHandler handler = mappings.get(type);
			return handler.handle(mqMessage.getText());
		} catch (Exception e) {
			LOGGER.error("Failed to receive message: ", e);
		}
		return false;
	}

	private void tryToClean(Connection connection, MessageConsumer consumer, Session session) {
		try {
			if (consumer != null)
				consumer.close();
			if (session != null)
				session.close();
			if (connection != null)
				connection.close();
		} catch (JMSException e) {
			LOGGER.error("Problem closing consumer or connection", e);
		}
	}

}
