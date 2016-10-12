 package com.gft.digitalbank.exchange.solution;
 
 import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.google.common.collect.Sets;
 
 public class Scenario26 extends Scenario
 {
   public List<PositionOrder> orders()
   {
     return java.util.Arrays.asList(
       PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.SELL).product("A")
       .details(OrderDetails.builder().amount(100).price(100).build()).build(), 
       PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.SELL).product("A")
       .details(OrderDetails.builder().amount(200).price(90).build()).build(), 
       PositionOrder.builder().id(3).timestamp(3L).client("102").broker("1").side(Side.SELL).product("A")
       .details(OrderDetails.builder().amount(300).price(95).build()).build(), 
       PositionOrder.builder().id(4).timestamp(4L).client("103").broker("3").side(Side.BUY).product("A")
       .details(OrderDetails.builder().amount(1000).price(110).build()).build(), 
       PositionOrder.builder().id(5).timestamp(5L).client("100").broker("1").side(Side.SELL).product("A")
       .details(OrderDetails.builder().amount(100).price(100).build()).build() );
   }
   
 
   public Set<Transaction> transactions()
   {
     return Sets.newHashSet(new Transaction[] {
       Transaction.builder().id(1).amount(200).price(90).brokerBuy("3").brokerSell("2").clientBuy("103")
       .clientSell("101").product("A").build(), 
       Transaction.builder().id(2).amount(300).price(95).brokerBuy("3").brokerSell("1").clientBuy("103")
       .clientSell("102").product("A").build(), 
       Transaction.builder().id(3).amount(100).price(100).brokerBuy("3").brokerSell("1").clientBuy("103")
       .clientSell("100").product("A").build(), 
       Transaction.builder().id(4).amount(100).price(110).brokerBuy("3").brokerSell("1").clientBuy("103")
       .clientSell("100").product("A").build() });
   }
   
 
   public Set<OrderBook> orderBooks()
   {
     return Sets.newHashSet(new OrderBook[] {
       OrderBook.builder().product("A")
       .buyEntry(com.gft.digitalbank.exchange.model.OrderEntry.builder().id(1).amount(300).price(110).broker("3").client("103").build())
       .build() });
   }
 }


