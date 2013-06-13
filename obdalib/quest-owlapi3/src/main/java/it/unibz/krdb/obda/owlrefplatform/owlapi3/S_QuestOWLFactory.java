package it.unibz.krdb.obda.owlrefplatform.owlapi3;

import it.unibz.krdb.obda.model.OBDAModel;
import it.unibz.krdb.obda.owlapi3.OBDAOWLReasonerFactory;

import java.util.Properties;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.IllegalConfigurationException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implementation of the factory for creating reformulation's platform
 * reasoner
 */

public class S_QuestOWLFactory implements OBDAOWLReasonerFactory {

	private OBDAModel apic;
	private Properties preferences = null;
	private String id = "Quest";
	private String name = "Quest";

	private final Logger log = LoggerFactory.getLogger(S_QuestOWLFactory.class);

	// /**
	// * Sets up some prerequirements in order to create the reasoner
	// *
	// * @param manager
	// * the owl ontology manager
	// * @param id
	// * the reasoner id
	// * @param name
	// * the reasoner name
	// */
	// public void setup(OWLOntologyManager manager, String id, String name) {
	// this.id = id;
	// this.name = name;
	// this.owlOntologyManager = manager;
	// }

	// /**
	// * Return the current OWLOntologyManager
	// *
	// * @return the current OWLOntologyManager
	// */
	// public OWLOntologyManager getOWLOntologyManager() {
	// return owlOntologyManager;
	// }
	//
	// /**
	// * Returns the current reasoner id
	// *
	// * @return the current reasoner id
	// */
	// public String getReasonerId() {
	// return id;
	// }

	public void setOBDAController(OBDAModel apic) {
		this.apic = apic;
	}

	@Override
	public void setPreferenceHolder(Properties preference) {
		this.preferences = preference;
	}

	public String getReasonerName() {
		return name;
	}

	// public void initialise() throws Exception {
	//
	// }
	//
	// public void dispose() throws Exception {
	//
	// }

	/**
	 * Returns the current api controller
	 * 
	 * @return the current api controller
	 */
	public OBDAModel getApiController() {
		return apic;
	}

	@Override
	public OWLReasoner createNonBufferingReasoner(OWLOntology ontology) {
		return new S_QuestOWL(ontology, apic, new SimpleConfiguration(), BufferingMode.NON_BUFFERING, preferences);
	}

	@Override
	public OWLReasoner createNonBufferingReasoner(OWLOntology ontology, OWLReasonerConfiguration config) throws IllegalConfigurationException {
		return new S_QuestOWL(ontology, apic, config, BufferingMode.NON_BUFFERING, preferences);
	}

	@Override
	public OWLReasoner createReasoner(OWLOntology ontology) {
		return new S_QuestOWL(ontology, apic, new SimpleConfiguration(), BufferingMode.BUFFERING, preferences);
	}

	@Override
	public OWLReasoner createReasoner(OWLOntology ontology, OWLReasonerConfiguration config) throws IllegalConfigurationException {
		return new S_QuestOWL(ontology, apic, config, BufferingMode.BUFFERING, preferences);
	}

//	@Override
//	public OBDAOWLReasoner createReasoner(OWLOntologyManager manager) {
//		return new QuestOWL(null, apic, new SimpleConfiguration(), BufferingMode.BUFFERING, preferences);
//	}
}
