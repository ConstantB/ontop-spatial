package inf.unibz.it.obda.api.io;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import inf.unibz.it.dl.domain.NamedPredicate;
import inf.unibz.it.obda.api.controller.APIController;
import inf.unibz.it.obda.api.controller.APICoupler;
import inf.unibz.it.ucq.domain.BinaryQueryAtom;
import inf.unibz.it.ucq.domain.ConceptQueryAtom;
import inf.unibz.it.ucq.domain.ConstantTerm;
import inf.unibz.it.ucq.domain.FunctionTerm;
import inf.unibz.it.ucq.domain.QueryAtom;
import inf.unibz.it.ucq.domain.QueryTerm;
import inf.unibz.it.ucq.domain.TypedConstantTerm;
import inf.unibz.it.ucq.domain.VariableTerm;


/**
 * 
 * The entity name renderer is rendering the names to display of all entities of all loaded
 * ontologies. It will provide the names of entities together with the prefix of the ontologie it 
 * belongs to. If for an ontology no prefix is registered the whole URI will be shown. In case
 * a prefix is registered it will be used e.g. The name of the entity A of an ontology with a registered prefix 
 * onto1 will be displayed as onto1:A. In case the entity belongs the currently selected ontology the
 * prefix will be omitted and the entity name will be display as :A
 * 
 * @author Manfred Gerstgrasser
 *
 */

public class EntityNameRenderer {

	/**
	 * The current API coupler
	 */
	protected APICoupler coupler = null;
	/**
	 * The current API controller
	 */
	protected APIController controller = null;
	
	/**
	 * the constructor creates a new instance of the entity name renderer
	 * @param controller the current API controller
	 */
	public EntityNameRenderer(APIController controller){
		this.controller = controller;
		this.coupler = this.controller.getCoupler();
	}

	/**
	 *	Based on the URI which identifies the given atom, the method returns the name of the atom.
	 *  If the atom belongs to the currently selected ontolgy no prefix will be displayed. If it
	 *  belongs to an other ontolgy, the registerd prefix will be displayed. If for an ontolgy no prefix
	 *  is registered the whole ontology URI will be displayed
	 * @param bqa a binary query atom
	 * @return the name to display
	 */
	public String getPredicateName(BinaryQueryAtom bqa){
		NamedPredicate np = bqa.getNamedPredicate();
		URI uri = np.getUri();
		String prefix = coupler.getPrefixForUri(uri);
		boolean validPrefix = false;
		URL test = null;
		try {
			test = new URL(prefix);
		} catch (MalformedURLException e) {
			validPrefix = true; //if the string is not an valid URI, this means it is a prefix. The getPrefixForUri 
			// method returns either a valid prefix or the whole ontology URI if no prefix is defined.
		}
		if(validPrefix || prefix.equals("")){
			return prefix+":"+uri.getFragment();
		}else{
			String current_onto = controller.getCurrentOntologyURI().toString();
			String test_uri = test.getProtocol()+"://" + test.getAuthority()+test.getFile();			if(test_uri.equals(current_onto)){
				return ":"+uri.getFragment();
			}else{
				return prefix;
			}
		}
	}
	
	/**
	 *	Based on the URI which identifies the given atom, the method returns the name of the atom.
	 *  If the atom belongs to the currently selected ontolgy no prefix will be displayed. If it
	 *  belongs to an other ontolgy, the registerd prefix will be displayed. If for an ontolgy no prefix
	 *  is registered the whole ontology URI will be displayed
	 * @param bqa a binary query atom
	 * @return the name to display
	 */
	public String getPredicateName(QueryAtom qa){
		if(qa instanceof BinaryQueryAtom){
			return getPredicateName((BinaryQueryAtom)qa);
		}else{
			return getPredicateName((ConceptQueryAtom)qa);
		}
	}
	
	/**
	 *	Based on the URI which identifies the given atom, the method returns the name of the atom.
	 *  If the atom belongs to the currently selected ontolgy no prefix will be displayed. If it
	 *  belongs to an other ontolgy, the registerd prefix will be displayed. If for an ontolgy no prefix
	 *  is registered the whole ontology URI will be displayed
	 * @param bqa a binary query atom
	 * @return the name to display
	 */
	public String getPredicateName(ConceptQueryAtom cqa){
		
		if(cqa != null){
			NamedPredicate np = cqa.getNamedPredicate();
			URI uri = np.getUri();
			String prefix = coupler.getPrefixForUri(uri);
			boolean validPrefix = false;
			URL test = null;
			try {
				test = new URL(prefix);
			} catch (MalformedURLException e) {
				validPrefix = true; //if the string is not an valid URI, this means it is a prefix. The getPrefixForUri 
				// method returns either a valid prefix or the whole ontology URI if no prefix is defined.
			}
			if(validPrefix || prefix.equals("")){
				return prefix+":"+uri.getFragment();
			}else{
				String current_onto = controller.getCurrentOntologyURI().toString();
				String test_uri = test.getProtocol()+"://" + test.getAuthority()+test.getFile();				if(test_uri.equals(current_onto)){
					return ":"+uri.getFragment();
				}else{
					return prefix;
				}
			}
		}else{
			return "";
		}
	}
	
	/**
	 * 
	 *	Based on the URI which identifies the given function term, the method returns the name of it.
	 *  If the term belongs to the currently selected ontolgy no prefix will be displayed. If it
	 *  belongs to an other ontolgy, the registerd prefix will be displayed. If for an ontolgy no prefix
	 *  is registered the whole ontology URI will be displayed
	 * 
	 * @param ft the function term to display
	 * @return the name to display
	 */
	public String getFunctionName(FunctionTerm ft){
		URI uri = ft.getURI();
		String prefix = coupler.getPrefixForUri(uri);
		boolean validPrefix = false;
		URL test = null;
		try {
			test = new URL(prefix);
		} catch (MalformedURLException e) {
			validPrefix = true; //if the string is not an valid URL, this means it is a prefix. The getPrefixForUri 
			// method returns either a valid prefix or the whole ontology URI if no prefix is defined.
		}
		if(validPrefix || prefix.equals("")){
			return prefix+":"+uri.getFragment();
		}else{
			String current_onto = controller.getCurrentOntologyURI().toString();
			String test_uri = test.getProtocol()+"://" + test.getAuthority()+test.getFile();
			if(test_uri.equals(current_onto)){
				return ":"+uri.getFragment();
			}else{
				return prefix;
			}
		}
	}
	
	/**
	 * Replaced the current coupler with the given one.
	 * @param coupler the new coupler to use.
	 */
	public void setCoupler(APICoupler coupler){
		this.coupler = coupler;
	}
}