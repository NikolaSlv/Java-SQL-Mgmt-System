# Java-SQL-Mgmt-System
- Built and ran using IntelliJ IDEA
- Main is at src/app/Application.java

The database details are set in src/app/MySQLDataAdapter.java :

private final String dbPass = "admin";
This has to be changed if the password is different for the system to
successfully establish connection.

This project is written in Java and uses a local MySQL database.
The name of the database that is created when running the system is
"store". So, if not already initialized for this project specifically, 
the system assumes that there does not exist a local MySQL
database with the name "store", or if it exists, it is an empty set.
In the configuration window, the tables need to be set up first. SampleData.sql
can be used to test the system after creating the database and initializing the
tables.

RunApplication.bat can be used to run the system from production. The contents
of this .bat file are:

cd out\production\Project-1
java -cp ".;../../../mysql-connector-j-8.3.0.jar" app.Application

Otherwise, IntelliJ or another IDE can also be used.

The project is organized using Model-View-Controller architecture design pattern.
