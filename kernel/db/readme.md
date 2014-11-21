>>>>>>>>>>>>JDBC and JPA samples

================================= JDBC =========================================

>>>> DB command

install JDBC driver

* features:install jdbc
* osgi:list -> show new JDBC components as active

[ 213] [Active     ] [            ] [       ] [   80] Commons DBCP (1.4)
[ 214] [Active     ] [Created     ] [       ] [   80] Apache Karaf :: JDBC :: Core (2.3.7)
[ 215] [Active     ] [Created     ] [       ] [   80] Apache Karaf :: JDBC :: Command (2.3.7)


install postgress drivers

* install -s wrap:mvn:postgresql/postgresql/9.1-901.jdbc4
* osgi:list -> show postgress driver installed as bundle

[ 254] [Active     ] [            ] [       ] [   80] wrap_mvn_postgresql_postgresql_9.1-901.jdbc4 (0)

install datasource

* cp ../datasource/datasource-postgres.xml /opt/apache-servicemix/deploy
* osgi:list -> show datasource installed as bundle

[ 255] [Active     ] [Created     ] [       ] [   80] datasource-postgres.xml (0.0.0)

check all OK using log display

* log:display

2014-11-20 15:16:20,963 | INFO  | mix-5.1.2/deploy | fileinstall                      | ?                                   ? | 6 - org.apache.felix.fileinstall - 3.4.0 | Installing bundle datasource-postgres.xml / 0.0.0
2014-11-20 15:16:21,009 | INFO  | mix-5.1.2/deploy | fileinstall                      | ?                                   ? | 6 - org.apache.felix.fileinstall - 3.4.0 | Started bundle: blueprint:file:/opt/apache-servicemix-5.1.2/deploy/datasource-postgres.xml


>> install DB command bundle

install -s mvn:eu.factorx.myrmex.kernel.db/db-command/1.0-SNAPSHOT

[ 219] [Active     ] [Created     ] [       ] [   80] db-command (1.0.0.SNAPSHOT)

>> now the command bundle is up and running. Let's try it.

>> check available datasources

* db:select

Sel | Name            | Product    | Version | URL                      
------------------------------------------------------------------------
    | jdbc/postgresds | PostgreSQL | 9.3.5   | jdbc:postgresql://localh 

>>> select the postgress DS

db:select jdbc/postgresds

>> check selection

* db:select

Sel | Name            | Product    | Version | URL                      
------------------------------------------------------------------------
*   | jdbc/postgresds | PostgreSQL | 9.3.5   | jdbc:postgresql://localh 

>> test query

* db:query "select * from account"

==================================== Hibernate/JPA sample ======================================


>>> work with features.xml file (aggragation of configuration commands

* features:addurl mvn:eu.factorx.myrmex.hibernate/hibernate-features/1.0.0-SNAPSHOT/xml
* features:install jndi hibernate

[ 257] [Active     ] [Created     ] [       ] [   80] Apache Karaf :: JNDI :: Command (2.3.7)
[ 265] [Active     ] [            ] [       ] [   80] Javassist (3.18.1.GA)
[ 267] [Active     ] [            ] [       ] [   80] JBoss Logging 3 (3.1.4.GA)
[ 268] [Active     ] [            ] [       ] [   80] hibernate-commons-annotations (4.0.4.Final)
[ 269] [Active     ] [            ] [       ] [   80] wrap_mvn_org.jboss_jandex_1.1.0.Final (0)
[ 270] [Active     ] [            ] [       ] [   80] Apache ServiceMix :: Bundles :: dom4j (1.6.1.5)
[ 271] [Active     ] [            ] [       ] [   80] Apache ServiceMix :: Bundles :: antlr (2.7.7.5)
[ 272] [Active     ] [            ] [       ] [   80] ClassMate (0.9.0)
[ 273] [Active     ] [            ] [       ] [   80] Apache ServiceMix :: Bundles :: ant (1.8.2.2)
[ 274] [Active     ] [            ] [       ] [  100] hibernate-osgi (4.3.6.Final)
[ 275] [Active     ] [            ] [       ] [  100] hibernate-entitymanager (4.3.6.Final)
[ 276] [Active     ] [            ] [       ] [  100] hibernate-core (4.3.6.Final)

>>> uninstall servicemix base bundle :  org.apache.geronimo.specs.geronimo-jpa_2.0_spec [202.0]

install -s mvn:eu.factorx.myrmex.kernel.db/jpa-hibernate/1.0-SNAPSHOT


















