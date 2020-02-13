# Simple protocol
Simple string protocol communicating over sockets.

## Getting Started

These instructions will guide you on how to configure and run the project

### Prerequisites

* Docker (version that supports multi stage)
    tested with 2.1.0 
Or

* Java 8 or above
* Maven (tested with 3.6.1)

### Run the project
To run the project, there are 2 ways to achieve that

1) Using maven
2) Using docker and make file

for each step below we will explain each command for both of them

## Running the tests

In order to run the unit test you need to run this command

Using maven
```
mvn clean test
```
Using docker
```
make test
```

## Running the server

Using maven
```
mvn clean compile exec:java
```
Using docker
```
make
```
or
```
make run
```
## packaging and execution
* Packaging is only supported using maven
* To generate a fat jar for the project
```
mvn clean compile package
```
this will generate a jar file in the `target` directory 

* In order to execute the generated jar file
```
java -jar <jar-file>
```
## Authors

* Mohamed Waleed
