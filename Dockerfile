FROM openjdk:11
VOLUME /tmp
ADD target/war-lab3.war war-lab3.war
EXPOSE 8787
ENTRYPOINT ["java", "-jar", "war-lab3.war"]
