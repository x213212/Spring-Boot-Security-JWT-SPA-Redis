@echo off
start "auth1" java -jar -Xms4000m -Xmx4000m -Xmn2000m target/spring-boot-security-jwt-0.0.1-SNAPSHOT.jar
start "auth2" java -jar -Xms4000m -Xmx4000m -Xmn2000m target/spring-boot-security-jwt-0.0.1-SNAPSHOT2.jar
start "nginx-client" D:\Programming\java\Spring-Boot-Security-JWT-SPA-master\nginx-client\start.bat
start "nginx-client2" D:\Programming\java\Spring-Boot-Security-JWT-SPA-master\nginx-client2\start.bat
start "nginx-master" D:\Programming\java\Spring-Boot-Security-JWT-SPA-master\nginx-master\start.bat
echo 'run'
