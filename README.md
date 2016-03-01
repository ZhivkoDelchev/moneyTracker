# README #

### This is open source project for personal use and experiments. ###

The main use of the software is to deliver and satisfy its creators personal need but it also shares the code with the world.

### How do I get set up? ###

* Download and install _wildfly 9_
* Configure data source _java:jboss/datasources/trackerDS_
* Configure security-domain _trackerRealm_ that uses just created datasource as follows:
```
<security-domain name="trackerRealm" cache-type="default">
  <authentication>
    <login-module code="Database" flag="sufficient">
      <module-option name="dsJndiName" value="java:jboss/datasources/trackerDS"/>
      <module-option name="principalsQuery" value="select password from principles where principal_id=?"/>
      <module-option name="rolesQuery" value="select r.user_role, 'Roles' from roles r inner join principles p on r.principal_id = p.principal_id where p.principal_id=?"/>
      <module-option name="hashAlgorithm" value="MD5"/>
      <module-option name="hashEncoding" value="hex"/>
    </login-module>
  </authentication>
</security-domain>
```
* Use maven for building the project
* Deploy the EAR file generated in sourceRoot/ear/target
* Deployment instructions
* Any other java application servers would be working but some configuration may require changes. In order to change data source change the value of jta-data-source in _sourceRoot/webapp/src/main/resources/META-INF/persistence.xml_. In order to change security domain you may need to add new configuration instead of _sourceRoot/webapp/src/main/webapp/WEB-INF/jboss-web.xml_.
