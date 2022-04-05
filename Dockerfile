FROM maven:3.8.1-openjdk-17 as builder
RUN mkdir -p /root/.m2 && mkdir /root/.m2/repository
COPY . /app/
RUN cd /app && mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-alpine
COPY --from=builder /app/target/octo-spy.jar octo-spy.jar
ENTRYPOINT ["java","-jar","/octo-spy.jar"]
