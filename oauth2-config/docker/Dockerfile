FROM imagesregistry-test:5000/centos7-jdk1.8:latest

MAINTAINER nariis-dicp

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

VOLUME ["/usr/local/Nari-OAuth2-Server/config", "/usr/local/modules/Nari-OAuth2-Server/log"]

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' > /etc/timezone \
    && mkdir -p /usr/local/Nari-OAuth2-Server/config \
    && mkdir -p /usr/local/Nari-OAuth2-Server/log

ENV TARGET_JAR ${TARGET_JAR:-oauth2-server-1.0.0-SNAPSHOT.jar}
ENV JAVA_OPTS="-server -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m -Xms1024m -Xmx1024m -Dspring.profiles.active=prod"
ENV SERVER_CONFIG_PATH /usr/local/Nari-OAuth2-Server/config
ENV SERVER_LOG_PATH /usr/local/Nari-OAuth2-Server/log

COPY ${TARGET_JAR} /usr/local/Nari-OAuth2-Server/${TARGET_JAR}
COPY application.yml /usr/local/Nari-OAuth2-Server/config/
COPY logback.xml /usr/local/Nari-OAuth2-Server/config/

WORKDIR /usr/local/Nari-OAuth2-Server

EXPOSE 8080

ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_OPTS -jar -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom -Djava.net.preferIPv4Stack=true -DOAUTH2_CONFIG_PATH=${SERVER_CONFIG_PATH} -DOAUTH2_LOG_PATH=${SERVER_LOG_PATH} ${TARGET_JAR} --spring.config.additional-location=file:${SERVER_CONFIG_PATH}/"]