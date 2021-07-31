FROM maven:3.8.1-openjdk-16 as builder
RUN mkdir -p /root/.m2 && mkdir /root/.m2/repository
COPY . /app/
RUN cd /app && mvn clean package -Dmaven.test.skip=true

FROM tomcat:9.0-jdk16-openjdk
COPY --from=builder /app/target/octo-spy.war /usr/local/tomcat/webapps
ENV CATALINA_OPTS=""
EXPOSE 8080
CMD ["catalina.sh", "run"]
