package it.unibz.krdb.obda.owlrefplatform.owlapi3;

import it.unibz.krdb.obda.model.GraphResultSet;
import it.unibz.krdb.obda.model.OBDAException;
import it.unibz.krdb.obda.ontology.Assertion;
import it.unibz.krdb.obda.ontology.ClassAssertion;
import it.unibz.krdb.obda.ontology.DataPropertyAssertion;
import it.unibz.krdb.obda.ontology.ObjectPropertyAssertion;
import it.unibz.krdb.obda.owlapi3.OWLConnection;
import it.unibz.krdb.obda.owlapi3.OWLResultSet;
import it.unibz.krdb.obda.owlapi3.OWLStatement;
import it.unibz.krdb.obda.owlrefplatform.core.QuestStatement;
import it.unibz.krdb.obda.owlrefplatform.core.S_QuestStatement;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class S_QuestOWLStatement implements OWLStatement {

	private S_QuestStatement st;
	private S_QuestOWLConnection conn;

	public S_QuestOWLStatement(S_QuestStatement questStatement, S_QuestOWLConnection conn) {
		this.conn = conn;
		this.st = questStatement;
	}
	
	public S_QuestStatement getQuestStatement() {
		return st;
	}

	@Override
	public void cancel() throws OWLException {
		try {
			st.cancel();
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public void close() throws OWLException {
		try {
			st.close();
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public OWLResultSet execute(String query) throws OWLException {
		try {
			return new S_QuestOWLResultSet(st.execute(query), this);
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public List<OWLAxiom> executeConstruct(String query) throws OWLException {
		try {
			GraphResultSet resultSet = st.executeConstruct(query);
			return createOWLIndividualAxioms(resultSet);
		} catch (Exception e) {
			throw new OWLOntologyCreationException(e);
		}
	}

	@Override
	public List<OWLAxiom> executeDescribe(String query) throws OWLException {
		try {
			GraphResultSet resultSet = st.executeDescribe(query);
			return createOWLIndividualAxioms(resultSet);
		} catch (Exception e) {
			throw new OWLOntologyCreationException(e);
		}
	}
	
	@Override
	public int executeUpdate(String query) throws OWLException {
		try {
			return st.executeUpdate(query);
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public OWLConnection getConnection() throws OWLException {
		return conn;
	}

	@Override
	public int getFetchSize() throws OWLException {
		try {
			return st.getFetchSize();
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public int getMaxRows() throws OWLException {
		try {
			return st.getMaxRows();
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public void getMoreResults() throws OWLException {
		try {
			st.cancel();
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public OWLResultSet getResultSet() throws OWLException {
		try {
			return new S_QuestOWLResultSet(st.getResultSet(), this);
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public int getQueryTimeout() throws OWLException {
		try {
			return st.getQueryTimeout();
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public void setFetchSize(int rows) throws OWLException {
		try {
			st.setFetchSize(rows);
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public void setMaxRows(int max) throws OWLException {
		try {
			st.setMaxRows(max);
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public boolean isClosed() throws OWLException {
		try {
			return st.isClosed();
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public void setQueryTimeout(int seconds) throws Exception {
		try {
			st.setQueryTimeout(seconds);
		} catch (OBDAException e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	@Override
	public int getTupleCount(String query) throws OWLException {
		try {
			return st.getTupleCount(query);
		} catch (Exception e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	public String getRewriting(String query) throws OWLException {
		try {
			return st.getRewriting(query);
		} catch (Exception e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}

	public String getUnfolding(String query) throws OWLException {
		try {
			return st.getUnfolding(query);
		} catch (Exception e) {
			OWLException owlException = new OWLException(e) {
			};
			owlException.setStackTrace(e.getStackTrace());
			throw owlException;
		}
	}
		
	private List<OWLAxiom> createOWLIndividualAxioms(GraphResultSet resultSet) throws Exception {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		List<OWLAxiom> axiomList = new ArrayList<OWLAxiom>();
		while (resultSet.hasNext()) {
			for (Assertion assertion : resultSet.next()) {
				if (assertion instanceof ClassAssertion) {
					String subjectIRI = ((ClassAssertion) assertion).getPredicate().toString();
					String classIRI = ((ClassAssertion) assertion).getObject().getValue();
					OWLAxiom classAxiom = createOWLClassAssertion(classIRI, subjectIRI, factory);
					axiomList.add(classAxiom);
				} else if (assertion instanceof ObjectPropertyAssertion) {
					String propertyIRI = ((ObjectPropertyAssertion) assertion).getPredicate().toString();
					String subjectIRI = ((ObjectPropertyAssertion) assertion).getFirstObject().getValue();
					String objectIRI = ((ObjectPropertyAssertion) assertion).getSecondObject().getValue();
					OWLAxiom objectPropertyAxiom = createOWLObjectPropertyAssertion(propertyIRI, subjectIRI, objectIRI, factory);
					axiomList.add(objectPropertyAxiom);
				}  else if (assertion instanceof DataPropertyAssertion) {
					String propertyIRI = ((DataPropertyAssertion) assertion).getPredicate().toString();
					String subjectIRI = ((DataPropertyAssertion) assertion).getObject().getValue();
					String objectValue = ((DataPropertyAssertion) assertion).getValue().getValue();
					OWLAxiom dataPropertyAxiom = createOWLDataPropertyAssertion(propertyIRI, subjectIRI, objectValue, factory);
					axiomList.add(dataPropertyAxiom);
				}
			}
		}		
		return axiomList;
	}
	
	private OWLClassAssertionAxiom createOWLClassAssertion(String classIRI, String subjectIRI, OWLDataFactory factory) {
		OWLClass classExpression = factory.getOWLClass(IRI.create(classIRI));
		OWLIndividual individual = factory.getOWLNamedIndividual(IRI.create(subjectIRI));
		return factory.getOWLClassAssertionAxiom(classExpression, individual);
	}
	
	private OWLObjectPropertyAssertionAxiom createOWLObjectPropertyAssertion(String propertyIRI, String subjectIRI, String objectIRI, OWLDataFactory factory) {
		OWLObjectProperty propertyExpression = factory.getOWLObjectProperty(IRI.create(propertyIRI));
		OWLIndividual individual1 = factory.getOWLNamedIndividual(IRI.create(subjectIRI));
		OWLIndividual individual2 = factory.getOWLNamedIndividual(IRI.create(objectIRI));
		return factory.getOWLObjectPropertyAssertionAxiom(propertyExpression, individual1, individual2);
	}
	
	private OWLDataPropertyAssertionAxiom createOWLDataPropertyAssertion(String propertyIRI, String subjectIRI, String objectValue, OWLDataFactory factory) {
		OWLDataProperty propertyExpression = factory.getOWLDataProperty(IRI.create(propertyIRI));
		OWLIndividual individual1 = factory.getOWLNamedIndividual(IRI.create(subjectIRI));
		OWLLiteral individual2 = factory.getOWLLiteral(objectValue);
		return factory.getOWLDataPropertyAssertionAxiom(propertyExpression, individual1, individual2);
	}
}
