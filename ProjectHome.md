This program built with Java, JFLEX (scanner), CUP (parser) and JDBC allows you to simulate a command line to be validated.

Let me explain, I have defined a series of commands and parameters, each command depending on the parameters passed does a certain thing.
The recovery of the accepted token is delegated to the scanner and the parser is allowed to validate the command.

You can find some example of accepted command here:
/ConsoleJFlexCUPJavaJDBC/src/net/appuntivari/jflexcup/standalone/Main.java

Behind this is a mysql db managed through JDBC connector.