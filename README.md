# JSON-database-mvn

## Description
A client-server application that allows the clients to store their data on the server in JSON format

## Build
mvn clean package

## Start server
java -jar target/server-jar-with-dependencies.jar

## Run client with arguments
Supported command types:
- set
- get
- delete
- exit

-t=type, -k=key, -v=value

java -jar target/client-jar-with-dependencies.jar -t set -k 1 -v "Hello world"

## Run client with input file

java -jar target/client-jar-with-dependencies.jar -in setFile.json

## Sample run

> java Main -t set -k 1 -v "Hello world!" 

Client started!\
Sent: {"type":"set","key":"1","value":"Hello world!"}\
Received: {"response":"OK"}

> java Main -in setFile.json 

Client started!\
Sent:\
{\
   "type":"set",\
   "key":"person",\
   "value":{\
      "name":"Elon Musk",\
      "car":{\
         "model":"Tesla Roadster",\
         "year":"2018"\
      },\
      "rocket":{\
         "name":"Falcon 9",\
         "launches":"87"\
      }\
   }\
}\
Received: {"response":"OK"}

> java Main -in getFile.json

Client started!\
Sent: {"type":"get","key":["person","name"]}\
Received: {"response":"OK","value":"Elon Musk"}

> java Main -in updateFile.json

Client started!\
Sent: {"type":"set","key":["person","rocket","launches"],"value":"88"}\
Received: {"response":"OK"}

> java Main -in secondGetFile.json

Client started!\
Sent: {"type":"get","key":["person"]}\
Received:\
{\
   "response":"OK",\
   "value":{\
      "name":"Elon Musk",\
      "car":{\
         "model":"Tesla Roadster",\
         "year":"2018"\
      },\
      "rocket":{\
         "name":"Falcon 9",\
         "launches":"88"\
      }\
   }\
}

> java Main -in deleteFile.json

Client started!\
Sent: {"type":"delete","key":["person","car","year"]}\
Received: {"response":"OK"}

> java Main -in secondGetFile.json

Client started!\
Sent: {"type":"get","key":["person"]}\
Received:\
{\
   "response":"OK",\
   "value":{\
      "name":"Elon Musk",\
      "car":{\
         "model":"Tesla Roadster"\
      },\
      "rocket":{\
         "name":"Falcon 9",\
         "launches":"88"\
      }\
   }\
}

> java Main -t exit

Client started!\
Sent: {"type":"exit"}\
Received: {"response":"OK"}
