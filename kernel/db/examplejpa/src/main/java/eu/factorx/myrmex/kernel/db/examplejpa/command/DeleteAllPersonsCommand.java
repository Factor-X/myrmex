/*
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
package eu.factorx.myrmex.kernel.db.examplejpa.command;

import eu.factorx.myrmex.kernel.db.examplejpa.PersonService;

import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.service.command.CommandSession;

@Command(scope = "person", name = "deleteAll", description = "Delete all persons")
public class DeleteAllPersonsCommand implements Action {
    private PersonService personService;
    
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public Object execute(CommandSession session) throws Exception {
        personService.deleteAll();
        return null;
    }

}
