package it.unibz.krdb.obda.codec;

import it.unibz.krdb.obda.model.OBDADataFactory;
import it.unibz.krdb.obda.model.OBDADataSource;
import it.unibz.krdb.obda.model.impl.OBDADataFactoryImpl;
import it.unibz.krdb.obda.model.impl.RDBMSourceParameterConstants;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Element;

/**
 * Note: This is a legacy code. Do not use instances of this class. This code
 * is used by the old test cases which needed to be updated.
 */
public class DatasourceXMLCodec extends ObjectXMLCodec<OBDADataSource> {

	private static final String	TAG	= "dataSource";

	@Override
	public OBDADataSource decode(Element input) {
 		OBDADataSource new_src = null;
		OBDADataFactory fac = OBDADataFactoryImpl.getInstance();
		String id = input.getAttribute("URI");
		if(id.equals("")){
			id = input.getAttribute("name"); //old version
		}
		String uristring = input.getAttribute("ontouri");
		String driver = input.getAttribute("databaseDriver");
		String pwd = input.getAttribute("databasePassword");
		String dburl = input.getAttribute("databaseURL");
		String username = input.getAttribute("databaseUsername");
		String usage = input.getAttribute("isAboxDump");
		URI uri = URI.create(id);
		new_src = fac.getDataSource(uri);
		new_src.setParameter(RDBMSourceParameterConstants.DATABASE_DRIVER, driver);
		new_src.setParameter(RDBMSourceParameterConstants.DATABASE_PASSWORD, pwd);
		new_src.setParameter(RDBMSourceParameterConstants.DATABASE_URL, dburl);
		new_src.setParameter(RDBMSourceParameterConstants.DATABASE_USERNAME, username);
		new_src.setParameter(RDBMSourceParameterConstants.ONTOLOGY_URI, uristring);
		new_src.setParameter(RDBMSourceParameterConstants.USE_DATASOURCE_FOR_ABOXDUMP, usage);
		return new_src;
	}

	@Override
	public Element encode(OBDADataSource input) {
		Element element = createElement(TAG);
		String id = input.getSourceID().toString();
		String driver = input.getParameter(RDBMSourceParameterConstants.DATABASE_DRIVER);
		String  uristring= input.getParameter(RDBMSourceParameterConstants.ONTOLOGY_URI);
		String pwd = input.getParameter(RDBMSourceParameterConstants.DATABASE_PASSWORD);
		String dburl = input.getParameter(RDBMSourceParameterConstants.DATABASE_URL);
		String username = input.getParameter(RDBMSourceParameterConstants.DATABASE_USERNAME);
		String usage = input.getParameter(RDBMSourceParameterConstants.USE_DATASOURCE_FOR_ABOXDUMP);
		
		element.setAttribute("URI", id);
		element.setAttribute("ontouri",uristring);
		element.setAttribute("databaseDriver",driver);
		element.setAttribute("databasePassword", pwd);
		element.setAttribute("databaseURL", dburl);
		element.setAttribute("databaseUsername", username);
		element.setAttribute("isAboxDump", usage);
		return element;
	}

	public Collection<String> getAttributes() {
		ArrayList<String> fixedAttributes = new ArrayList<String>();
		fixedAttributes.add("name");
		fixedAttributes.add("URI");
		return fixedAttributes;
	}

	public String getElementTag() {
		return TAG;
	}
}
