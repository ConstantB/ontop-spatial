package it.unibz.krdb.obda.model.impl;

import java.net.URI;

import com.hp.hpl.jena.iri.IRI;

import it.unibz.krdb.obda.model.NonBooleanOperationPredicate;

public class NonBooleanOperationPredicateImpl extends PredicateImpl implements
		NonBooleanOperationPredicate {

	private static final long serialVersionUID = 1L;

	public NonBooleanOperationPredicateImpl(IRI name) {
		super(name, 1, null);
	}

	public NonBooleanOperationPredicateImpl(IRI name, int arity, COL_TYPE[] types) {
		super(name, arity, types);
	}

	@Override
	public NonBooleanOperationPredicateImpl clone() {
		return this;
	}
}