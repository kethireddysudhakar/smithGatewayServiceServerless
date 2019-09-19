From tomcat:8.0.51-jre8-alpine
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/smithgateway-*.war /usr/local/tomcat/webapps/ROOT.war
RUN sed -i 's/port="8080"/port="8084"/' /usr/local/tomcat/conf/server.xml
CMD ["catalina.sh","run"]

# Make port 8761 available to the world outside this container
EXPOSE 8084
