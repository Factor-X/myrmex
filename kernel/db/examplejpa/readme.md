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


==================================== JPA sample ======================================


>>>> for karaf 3.x (please install karaf 3.x - do not work on karaf 2.x as included in servicemix 5.4)

feature:install jdbc
feature:install openjpa jndi
install -s mvn:eu.factorx.myrmex.kernel.db/db-examplejpa/1.0-SNAPSHOT









