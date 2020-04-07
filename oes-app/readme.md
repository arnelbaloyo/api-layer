## how to launch

```log
java -Dapiml.service.port=9999 -jar oes-app/build/libs/oes-app-1.5.0-SNAPSHOT.jar
java -Dapiml.service.port=9998 -Dapiml.service.authentication.scheme=zoweJwt -jar oes-app/build/libs/oes-app-1.5.0-SNAPSHOT.jar
java -Dapiml.service.port=9997 -Dapiml.service.authentication.scheme=bypass -jar oes-app/build/libs/oes-app-1.5.0-SNAPSHOT.jar

java -Dapiml.service.port=9995 -Dapiml.service.serviceId=pass-app -jar oes-app/build/libs/oes-app-1.5.0-SNAPSHOT.jar
java -Dapiml.service.port=9994 -Dapiml.service.serviceId=pass-app -jar oes-app/build/libs/oes-app-1.5.0-SNAPSHOT.jar

```
