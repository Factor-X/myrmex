/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.factorx.myrmex.osgi.service.personservice.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eu.factorx.myrmex.osgi.service.personservice.person.Person;
import eu.factorx.myrmex.osgi.service.personservice.person.PersonService;

@Produces(MediaType.APPLICATION_XML)
@WebService
public class PersonServiceImpl implements PersonService {

    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public PersonServiceImpl() {
       System.out.print("Hello world... step 1");
    }
    
    /* (non-Javadoc)
     * @see net.lr.tutorial.karaf.cxf.personservice.rest.PersonService#getAll()
     */
    @Override
    @GET
    @Path("/")
    public Person[] getAll() {
        return (Person[]) em.createQuery("select p from Person p", Person.class).getResultList().toArray();
    }
    
    /* (non-Javadoc)
     * @see net.lr.tutorial.karaf.cxf.personservice.rest.PersonService#getPerson(java.lang.String)
     */
    @Override
    @GET
    @Path("/{id}")
    public Person getPerson(@PathParam("id") String id) {
        return em.createQuery("select p from Person p where id = "+id, Person.class).getSingleResult();
    }

    /* (non-Javadoc)
     * @see net.lr.tutorial.karaf.cxf.personservice.rest.PersonService#updatePerson(java.lang.String, net.lr.tutorial.karaf.cxf.personservice.person.Person)
     */
    @Override
    @PUT
    @Path("/{id}")
    public void updatePerson(@PathParam("id") String id, Person person) {
        Person personToUpdate = getPerson(id);

        if(personToUpdate!=null){
            personToUpdate.setName(person.getName());
            personToUpdate.setUrl(person.getUrl());

            em.persist(personToUpdate);
        }
    }
    
    /* (non-Javadoc)
     * @see net.lr.tutorial.karaf.cxf.personservice.rest.PersonService#addPerson(net.lr.tutorial.karaf.cxf.personservice.person.Person)
     */
    @Override
    @POST
    @Path("/")
    public void addPerson(Person person) {
        em.persist(person);
    }

    
    
}
