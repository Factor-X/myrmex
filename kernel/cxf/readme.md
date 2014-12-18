Sample mainly based on Karaf Tutorial.
=====================================

Person Service Example
======================

Shows how to publish and use a simple REST and SOAP service in servicemix using cxf and blueprint.

To run the example you need to install the http feature of karaf. The default http port is 8080 and can be configured using the 
config admin pid "org.ops4j.pax.web". You also need to install the cxf feature. The base url of the cxf servlet is by default "/cxf". 
It can be configured in the config pid "org.apache.cxf.osgi". 

The "business case" is to manage a list of persons. As service should provide the typical CRUD operations. Front ends should be a REST service, a SOAP service and a web UI.

The example consists of five projects:

- model: Person class and PersonService interface
- server: Service implementation and logic to publish the service using REST and SOAP
- proxy: Accesses the SOAP service and publishes it as an OSGi service
- webui: Provides a simple servlet based web ui to list and add persons. Uses the OSGi service

alternative: 

- proxy-rest: Accesses the REST service and publishes it as an OSGi service


Diagram:
========

>>> insert here diagram

https://github.com/Factor-X/myrmex/blob/master/kernel/cxf/Overview.png


Some remarks
============

The encapsulating the service client as an OSGi service is not strictly necessary but it has the advantage that the webui is then completely independent of cxf. So it is very easy to exchange the way the service is accessed.

The service is implemented java first. SOAP and rest are used quite transparently. This is very suitable to communicate between a client and server of the same apphttp://www.liquid-reality.de/download/resources/com.gliffy.integration.confluence/icons/icn-logo.pnglication. If the service is to be used by other applications the wsdl first approach is more suitable. In this case the model project should be configured to generate the data classes and service interface from a wsdl (see cxf wsdl_first example). For rest services the java first approach is quite common in general as the client typically does not use proxy classes anyway.

The example uses blueprint instead of spring dm as it works much better in an OSGi environment. The bundles are created using the maven bundle plugin. A fact that shows how well blueprint works is that the maven bundle plugin is just used with default settings. In spring dm the imports have to be configured as spring needs access to many implementation classes of cxf. For spring dm examples take a look at the Talend Service Factory examples (https://github.com/Talend/tsf/tree/master/examples).

The example shows that writing OSGi applications is quite simple with aries and blueprint. It needs only 153 lines of java code (without comments) for a complete little application. The blueprint xml is also quite small and readable.    



Installation
============

>>>> Install and verify servicemix 5.1.2 is up and running

* download servicemix (http://servicemix.apache.org/)
* sudo -s
* $cd /opt
* $unzip apache-servicemix-5.1.2.zip
* chmod -R a+rw apache-servicemix-5.1.2
* exit (sudo mode)
* cd apache-servicemix-5.1.2/bin
* ./servicemix
* $karaf@root> osgi:list
* $karaf@root> osgi:shutdown
* $karaf@root> yes

Startup Example
===============

>>>> Run CXF sample:

Show diagram to explain goals of demo: 

https://github.com/Factor-X/myrmex/blob/master/kernel/cxf/Overview.png

$karaf@root> features:addurl mvn:org.apache.cxf.karaf/apache-cxf/2.5.1/xml/features
$karaf@root> features:install http
$karaf@root> features:install cxf

>>>> Erase local maven repository

$> rm -r ~/.m2/eu*

>>>> Build maven packages

$> mvn clean install

>>>> Show maven repository

$> cd ~/.m2/repository
$> ls

>>>> Install packages into osgi repository:

$karaf@root> install -s mvn:eu.factorx.myrmex.osgi.service.personservice/personservice-model/1.0-SNAPSHOT
$karaf@root> install -s mvn:eu.factorx.myrmex.osgi.service.personservice/personservice-server/1.0-SNAPSHOT
$karaf@root> install -s mvn:eu.factorx.myrmex.osgi.service.personservice/personservice-proxy/1.0-SNAPSHOT
$karaf@root> install -s mvn:eu.factorx.myrmex.osgi.service.personservice/personservice-webui/1.0-SNAPSHOT


>>>> Demo/Test the service:

stop proxy
stop webui

>>>> go to main service ref page

http://localhost:8181/cxf/

-> show wsdl and wadl
-> show jaxrs definition in blueprint (personeservice-server/src/main/resources/OSGI-INF.bluepring/blueprint.xml)
-> show jaxws definition in blueprint


>>> start proxy and webui

start proxy
start webui

>>>> Show main ui page working with osgi service binded to SOAP service. 

http://localhost:8181/personui

>>> add some information

>>> stop proxy and show error on page

>>> install proxy-rest

$karaf@root> install -s mvn:eu.factorx.myrmex.osgi.service.personservice/personservice-proxy/1.0-SNAPSHOT

>>>> Show main ui page working with osgi service binded to REST service.

http://localhost:8181/personui


>>> stop service (server) and reload ui page -> show error -> linked to REST service

>>> stop proxy-rest and start proxy and reload ui page -> show error -> linked to SOAP service.

>>> Show OSGI services and present that OSGI service link is done using the interface name (and not the 

osgi:ls

How it works
======================
>>>>> How it works:

h1. How it works

h2. Defining the model

The model project is a simple java maven project that defines a JAX-WS service and a JAXB data class. It has no dependencies to cxf. The service interface is just a plain java interface with the @WebService annotation.

{code}
@WebService
public interface PersonService {
    public abstract Person[] getAll();
    public abstract Person getPerson(String id);
    public abstract void updatePerson(String id, Person person);
    public abstract void addPerson(Person person);
}
{code}

The [Person|https://github.com/Factor-X/myrmex/blob/master/kernel/cxf/personservice/model/src/main/java/eu/factorx/myrmex/osgi/service/personservice/person/Person.java] class is just a simple pojo with getters and setters for id, name and url and the necessary JAXB annotations. Additionally you need an ObjectFactory to tell JAXB what xml element to use for the Person class. 
There is also no special code for OSGi in this project. So the model works perfectly inside and outside of an OSGi container.

{note}
The service is defined java first. SOAP and rest are used quite transparently. This is very suitable to communicate between a client and server of the same application. If the service is to be used by other applications the wsdl first approach is more suitable. 
In this case the model project should be configured to generate the data classes and service interface from a wsdl (see cxf wsdl_first example pom file). 
For rest services the java first approach is quite common in general as the client typically does not use proxy classes anyway.
{note}

h2. Service implementation (server)

[PersonServiceImpl|https://github.com/Factor-X/myrmex/blob/master/kernel/cxf/personservice/server/src/main/java/eu/factorx/myrmex/osgi/service/personservice/impl/PersonServiceImpl.java] 
is a java class the implements the service interface and contains some additional JAX-RS annotations. The way the class is defined allows it to implement a REST service and a SOAP service 
at the same time.

The production deployment of the service is done in [https://github.com/Factor-X/myrmex/blob/master/kernel/cxf/personservice/server/src/main/resources/OSGI-INF/blueprint/blueprint.xml].

As the file is in the special location OSGI-INF/blueprint it is automatically processed by the blueprint implementation aries in karaf. The REST service is published using the 
jaxrs:server element and the SOAP service is published using the jaxws:endpoint element. 
The blueprint namespaces are different from spring but apart from this the xml is very similar to a spring xml.

h2. Service proxy

The service proxy project only contains a blueprint xml that uses the CXF JAXWS client to consume the SOAP service and exports it as an OSGi Service.
 Encapsulating the service client as an OSGi service (proxy project) is not strictly necessary but it has the advantage that the webui 
 is then completely independent of cxf. So it is very easy to change the way the service is accessed. So this is considered a best practice in OSGi.

See [blueprint.xml|https://github.com/Factor-X/myrmex/blob/master/kernel/cxf/personservice/proxy/src/main/resources/OSGI-INF/blueprint/blueprint.xml]

h2. Web UI (webui)

This project consumes the PersonService OSGi service and exports the PersonServlet as an OSGi service. 
The pax web whiteboard extender will then publish the servlet on the location /personui.
The [PersonServlet|https://github.com/Factor-X/myrmex/blob/master/kernel/cxf/personservice/webui/src/main/java/eu/factorx/myrmex/osgi/service/personservice/webui/PersonServlet.java] 
gets the PersonService injected and uses to get all persons and also to add persons.

The wiring is done using a [blueprint context|https://github.com/Factor-X/myrmex/blob/master/kernel/cxf/personservice/webui/src/main/resources/OSGI-INF/blueprint/blueprint.xml].

h2. Some further remarks

The example uses blueprint instead of spring dm as it works much better in an OSGi environment. The bundles are created using the maven bundle plugin. A fact that shows how well blueprint works is that the maven bundle plugin is just used with default settings. In spring dm the imports have to be configured as spring needs access to many implementation classes of cxf. For spring dm examples take a look at the Talend Service Factory examples (https://github.com/Talend/tsf/tree/master/examples).

The example shows that writing OSGi applications is quite simple with aries and blueprint. The blueprint xml is also quite small and readable.    






