docker run --name=tomcat-demo -e CATALINA_OPTS=-Dimgserver="http://10.100.6.3:8881/img/" --rm -p 8880:8080 -v $(pwd)/target:/usr/local/tomcat/webapps tomcat:latest 
