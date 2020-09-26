FROM maven:3.6.1-jdk-8-alpine as builder
COPY . /app/
RUN cd /app && mvn clean package -Dmaven.test.skip=true

FROM tomcat:9.0-jre8-alpine
COPY --from=builder /app/target/octo-spy.war /usr/local/tomcat/webapps
ENV CATALINA_OPTS=""
EXPOSE 8080
CMD ["catalina.sh", "run"]
