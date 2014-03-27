package it.unibz.krdb.obda.reformulation.owlapi3;

import it.unibz.krdb.obda.model.CQIE;

import it.unibz.krdb.obda.owlapi3.swrl.SWRLToDatalogTranslator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SWRLTest extends TestCase {

	private OWLOntology owlOntology;
	OWLDataFactory fac;
	Logger log = LoggerFactory.getLogger(SWRLTest.class);
	ArrayList<String> inputs = new ArrayList<String>();

	public void setUp() {

		inputs.add("src/test/resources/test/swrl/exampleSWRL.owl");
		inputs.add("src/test/resources/test/swrl/complex_example.owl");
		inputs.add("src/test/resources/test/swrl/propertyExample.owl");

	}

	@Test
	public void testInformation() throws OWLOntologyCreationException {

		for (int i = 0; i < inputs.size(); i++) {
			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			owlOntology = man.loadOntologyFromOntologyDocument(new File(inputs.get(i)));
			fac = man.getOWLDataFactory();

			Set<OWLEntity> entities = owlOntology.getSignature();
			Iterator<OWLEntity> eit = entities.iterator();

			while (eit.hasNext()) {
				OWLEntity entity = eit.next();
				log.info(entity.toString());
			}

			// get the rules
			for (OWLAxiom a : owlOntology.getAxioms()) {

				if (a.getAxiomType().equals(AxiomType.SWRL_RULE)) {

					System.out.println(a);
					SWRLRule rule = (SWRLRule) a;

					Set<SWRLAtom> body = rule.getBody();

					Set<SWRLAtom> head = rule.getHead();
					Set<SWRLVariable> variables = rule.getVariables();

					log.info("head: " + head);
					log.info("body: " + body);
					for (SWRLAtom one : body) {
						log.info("predicate: " + one.getPredicate());
						log.info("arguments: " + one.getAllArguments());
					}
					log.info("variables: " + variables);

				}
			}

		}

	}

	@Test
	public void testVisitorSimpleExample() throws Exception {

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		owlOntology = man.loadOntologyFromOntologyDocument(new File(inputs.get(0)));
		fac = man.getOWLDataFactory();

		SWRLToDatalogTranslator trans = new SWRLToDatalogTranslator(owlOntology);
		log.info(trans.getRules().toString());
		Collection<CQIE> rules = trans.getRules();
		assertEquals(4, rules.size());

		for (CQIE rule : rules) {
			log.info(rule.toString());
			assertNotNull(rule.getHead());
			assertNotNull(rule.getBody());

		}

	}

	@Test
	public void testVisitorComplexExample() throws Exception {

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		owlOntology = man.loadOntologyFromOntologyDocument(new File(inputs.get(1)));
		fac = man.getOWLDataFactory();

		SWRLToDatalogTranslator trans = new SWRLToDatalogTranslator(owlOntology);
		log.info(trans.getRules().toString());
		Collection<CQIE> rules = trans.getRules();
		assertEquals(5, rules.size());

		for (CQIE rule : rules) {
			log.info(rule.toString());
			assertNotNull(rule.getHead());
			assertNotNull(rule.getBody());
		}

	}

	public void testVisitorPropertyExample() throws Exception {

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		owlOntology = man.loadOntologyFromOntologyDocument(new File(inputs.get(2)));
		fac = man.getOWLDataFactory();

		SWRLToDatalogTranslator trans = new SWRLToDatalogTranslator(owlOntology);
		log.info(trans.getRules().toString());
		Collection<CQIE> rules = trans.getRules();
		assertEquals(2, rules.size());

		for (CQIE rule : rules) {
			log.info(rule.toString());
			assertNotNull(rule.getHead());
			assertNotNull(rule.getBody());
		}

	}

}