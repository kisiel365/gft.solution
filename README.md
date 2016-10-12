# gft.solution.2016
My solution to Java contest organized by GFT in 2016.

In short: requires to obtain buy and sell offers for different products from couple brokers via JMS, match them to perform transactions and report the results. Buy and sell offers can be modified or even canceled.

- Task description in task.png
- Requires Java 1.8
- Compiles with maven "mvn clean install"
- To run integration and performance tests (delivered by contest organizators) just run "mvn test"
- You can run local sonar analysis "mvn sonar:sonar -P sonar"
