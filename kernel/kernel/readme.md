h1. Overview

Myrmex example of a vaadin bundel.
Based on examples of the Karaf Tutorial

implements a Vaadin based UI on top of the tasklist
The Vaadin UI reuses the original Tasklist model and persistence service. Below we deploy it alongside the original Servlet UI. 
As both use the same persistence service the UIs keep in sync.    

h1. Build

On top level of Karaf-Tutorial do:
mvn clean install

h1. Installation

features:removeurl mvn:eu.factorx.myrmex.kernel.vaadin/kernel-vaadin-features/1.0.0-SNAPSHOT/xml
features:addurl mvn:eu.factorx.myrmex.kernel.vaadin/kernel-vaadin-features/1.0.0-SNAPSHOT/xml

features:install example-kernel-vaadin

h1. Test

Old Servlet UI:
http://localhost:8181/kernel

Vaadin UI:

http://localhost:8181/tasklist-vaadin
