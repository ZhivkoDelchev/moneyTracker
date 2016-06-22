# README #

### This is open source project for personal use and experiments. ###

The main use of the software is to deliver and satisfy its creators personal need but it also shares the code with the world.

### How do I get set up? ###

* Download and install _tomcat 8 & mysql_
* Configure data source _jdbc/trackerDB_ in server.xml
<br /> For example:

```
<Resource
    name="jdbc/trackerDB"
    auth="Container"
    type="javax.sql.DataSource"
    factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
    timeBetweenEvictionRunsMillis="34000"
    validationQuery="SELECT 1"
    testOnBorrow="true"
    username="youusername"
    password="yourpassword"
    driverClassName="com.mysql.jdbc.Driver"
    url="jdbc:mysql://localhost:3306/money_tracker"
 />
```
* Add resource link in _context.xml_ of the tomcat server
<br />For example:

```
<ResourceLink name="jdbc/tracker"
    global="jdbc/trackerDB"
    auth="Container"
    type="javax.sql.DataSource"
/>
```

* Use maven for building the project
* Deploy the WAR file generated in sourceRoot/webapp-spring/target
* Deployment instructions
* Any other java application servers would be working but some configuration may require changes.
In order to change data source change the value of data source name  in the DataSource bean in Application class.
