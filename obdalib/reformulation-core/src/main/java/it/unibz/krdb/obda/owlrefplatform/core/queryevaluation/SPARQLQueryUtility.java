package it.unibz.krdb.obda.owlrefplatform.core.queryevaluation;

import it.unibz.krdb.obda.model.Constant;
import it.unibz.krdb.obda.model.URIConstant;
import it.unibz.krdb.obda.model.impl.OBDADataFactoryImpl;

public class SPARQLQueryUtility {
	
	private String query;
	
	private static final String ASK_KEYWORD = "ask";
	private static final String SELECT_KEYWORD = "select";
	private static final String CONSTRUCT_KEYWORD = "construct";
	private static final String DESCRIBE_KEYWORD = "describe";
	
	public SPARQLQueryUtility(String query) {
		this.query = query;
	}
	
	public SPARQLQueryUtility() {
	}
	
	public String getQueryString() {
		return query;
	}
	
	public boolean isAskQuery() {
		return query.toLowerCase().contains(ASK_KEYWORD);
	}
	
	public boolean isSelectQuery() {
		return query.toLowerCase().contains(SELECT_KEYWORD);
	}
	
	public boolean isConstructQuery() {
		return query.toLowerCase().contains(CONSTRUCT_KEYWORD);
	}
	
	public boolean isDescribeQuery() {
		return query.toLowerCase().contains(DESCRIBE_KEYWORD);
	}
	
	public static boolean isAskQuery(String query) {
		return query.toLowerCase().contains(ASK_KEYWORD);
	}
	
	public static boolean isSelectQuery(String query) {
		return query.toLowerCase().contains(SELECT_KEYWORD);
	}
	
	public static boolean isConstructQuery(String query) {
		return query.toLowerCase().contains(CONSTRUCT_KEYWORD);
	}
	
	public static boolean isDescribeQuery(String query) {
		return query.toLowerCase().contains(DESCRIBE_KEYWORD);
	}

	public static boolean isVarDescribe(String strquery) {
		if (strquery.contains("where"))
		{
			if (strquery.indexOf('?') < strquery.indexOf("where"))
				return true;
		}
		else
		{
			if (strquery.contains("?"))
				return true;
		}
		return false;
	}

	public static URIConstant getDescribeURI(String strquery) {
		int firstIdx = strquery.indexOf('<');
		int lastIdx = strquery.indexOf('>');
		String uri = strquery.substring(firstIdx+1, lastIdx);
		return OBDADataFactoryImpl.getInstance().getURIConstant(uri);
	}

	public static boolean isURIDescribe(String strquery) {
		if (strquery.contains("where"))
		{
			if (strquery.indexOf('<') < strquery.indexOf("where"))
				return true;
		}
		else
		{
			if (strquery.contains("<") && strquery.contains(">"))
				return true;
		}
		return false;
	 }

	public static String getSelectVarDescribe(String strquery) {
		strquery = strquery.toLowerCase().replace("describe", "select distinct");
		return strquery;
	}

	public static String getConstructObjQuery(Constant constant) {
		return "CONSTRUCT { ?s ?p <" + constant.toString()
				+ "> } WHERE { ?s ?p <" + constant.toString() + ">}";
	}

	public static String getConstructSubjQuery(Constant constant) {
		return "CONSTRUCT {<" + constant.toString() + "> ?p ?o} WHERE {<"
				+ constant.toString() + "> ?p ?o}";
	}
}