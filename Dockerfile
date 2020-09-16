FROM tomcat:latest
COPY target/demo.war /usr/local/tomcat/webapps/
COPY target/img.tar /img.tar