package it.unibz.krdb.obda.ontology;

/*
 * #%L
 * ontop-obdalib-core
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import it.unibz.krdb.obda.model.Predicate;

import java.io.Serializable;
import java.util.Set;

public interface Ontology extends Cloneable, Serializable {

	public void addAssertion(Axiom assertion);

	public void addAssertionWithEntities(Axiom assertion);
	
	public void addConcept(Predicate c);

	public void addRole(Predicate role);

	public Set<Predicate> getRoles();

	public Set<Predicate> getConcepts();
	
	public Set<Axiom> getAssertions();

	public String getUri();

	public Ontology clone();

	public Set<Assertion> getABox();
	
	public Set<PropertyFunctionalAxiom> getFunctionalPropertyAxioms();
	
	public Set<DisjointDescriptionAxiom> getDisjointDescriptionAxioms();
}
