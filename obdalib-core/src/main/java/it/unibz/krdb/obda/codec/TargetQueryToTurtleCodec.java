package it.unibz.krdb.obda.codec;

import it.unibz.krdb.obda.io.PrefixManager;
import it.unibz.krdb.obda.model.Atom;
import it.unibz.krdb.obda.model.CQIE;
import it.unibz.krdb.obda.model.OBDAModel;
import it.unibz.krdb.obda.model.OBDAQuery;
import it.unibz.krdb.obda.model.Predicate;
import it.unibz.krdb.obda.model.Term;
import it.unibz.krdb.obda.model.URIConstant;
import it.unibz.krdb.obda.model.ValueConstant;
import it.unibz.krdb.obda.model.Variable;
import it.unibz.krdb.obda.model.impl.FunctionalTermImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TargetQueryToTurtleCodec extends ObjectToTextCodec<OBDAQuery> {

	private static final long serialVersionUID = 1L;

	/**
	 * The Prefix Manager to obtain the prefix name and its complete URI.
	 */
	private PrefixManager prefMan = apic.getPrefixManager();

	/**
	 * Default constructor.
	 * 
	 * @param apic
	 *            The OBDA model.
	 */
	public TargetQueryToTurtleCodec(OBDAModel apic) {
		super(apic);
	}

	@Override
	public String encode(OBDAQuery input) {
		if (!(input instanceof CQIE)) {
			return "";
		}
		TurtleContainer turtle = new TurtleContainer();
		List<Atom> body = ((CQIE) input).getBody();
		for (Atom atom : body) {
			String subject, predicate, object = "";
			if (isUnary(atom)) {
				subject = getDisplayName(atom.getTerm(0));
				predicate = "a";
				object = getAbbreviatedName(atom.getPredicate());
			} else {
				subject = getDisplayName(atom.getTerm(0));
				predicate = getAbbreviatedName(atom.getPredicate());
				object = getDisplayName(atom.getTerm(1));
			}
			turtle.put(subject, predicate, object);
		}
		return turtle.print();
	}

	/**
	 * Checks if the atom is unary or not.
	 */
	private boolean isUnary(Atom atom) {
		return atom.getArity() == 1 ? true : false;
	}

	/**
	 * Prints the short form of the predicate (by omitting the complete URI and
	 * replacing it by a prefix name).
	 */
	private String getAbbreviatedName(Predicate predicate) {
		return prefMan.getShortForm(predicate.toString(), true, false);
	}

	/**
	 * Prints the text representation of different terms.
	 */
	private String getDisplayName(Term term) {
		StringBuffer sb = new StringBuffer();
		if (term instanceof FunctionalTermImpl) {
			FunctionalTermImpl function = (FunctionalTermImpl) term;
			String fname = getAbbreviatedName(function.getFunctionSymbol());
			sb.append(fname);
			sb.append("(");
			boolean separator = false;
			for (Term innerTerm : function.getTerms()) {
				if (separator) {
					sb.append(", ");
				}
				sb.append(getDisplayName(innerTerm));
				separator = true;
			}
			sb.append(")");
		} else if (term instanceof Variable) {
			sb.append("$");
			sb.append(term.toString());
		} else if (term instanceof URIConstant) {
			sb.append("<");
			sb.append(term.toString());
			sb.append(">");
		} else if (term instanceof ValueConstant) {
			sb.append("\"");
			sb.append(term.toString());
			sb.append("\"");
		}
		return sb.toString();
	}

	@Override
	public OBDAQuery decode(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * A utility class to store the Turtle main components, i.e., subject,
	 * predicate and object. The data structure simulates a tree structure where
	 * the subjects are the roots, the predicates are the intermediate nodes and
	 * the objects are the leaves.
	 * <p>
	 * An example:
	 * 
	 * <pre>
	 *   $s1 :p1 $o1
	 *   $s1 :p2 $o2
	 *   $s1 :p2 $o3
	 * </pre>
	 * 
	 * The example is stored to the TurtleContainer as shown below.
	 * 
	 * <pre>
	 *         :p1 - $o1    
	 *        / 
	 *   $s1 <      $o2
	 *        :p2 <
	 *              $o3
	 * </pre>
	 * <p>
	 * This data structure helps in printing the short Turtle syntax by
	 * traversing the tree.
	 * 
	 * <pre>
	 * $s1 :p1 $o1; :p2 $o2, $o3 .
	 * </pre>
	 */
	class TurtleContainer {

		private HashMap<String, ArrayList<String>> subjectToPredicates = new HashMap<String, ArrayList<String>>();
		private HashMap<String, ArrayList<String>> predicateToObjects = new HashMap<String, ArrayList<String>>();

		TurtleContainer() { /* NO-OP */
		}

		/**
		 * Adding the subject, predicate and object components to this
		 * container.
		 * 
		 * @param subject
		 *            The subject term of the Atom.
		 * @param predicate
		 *            The Atom predicate.
		 * @param object
		 *            The object term of the Atom.
		 */
		void put(String subject, String predicate, String object) {
			// Subject to Predicates map
			ArrayList<String> predicateList = subjectToPredicates.get(subject);
			if (predicateList == null) {
				predicateList = new ArrayList<String>();
			}
			insert(predicateList, predicate);
			subjectToPredicates.put(subject, predicateList);

			// Predicate to Objects map
			ArrayList<String> objectList = predicateToObjects.get(predicate);
			if (objectList == null) {
				objectList = new ArrayList<String>();
			}
			objectList.add(object);
			predicateToObjects.put(predicate, objectList);
		}

		// Utility method to insert the predicate
		private void insert(ArrayList<String> list, String input) {
			if (!list.contains(input)) {
				if (input.equals("a") || input.equals("rdf:type")) {
					list.add(0, input);
				} else {
					list.add(input);
				}
			}
		}

		/**
		 * Prints the container.
		 * 
		 * @return The Turtle short representation.
		 */
		String print() {
			StringBuffer sb = new StringBuffer();
			for (String subject : subjectToPredicates.keySet()) {
				sb.append(subject);
				sb.append(" ");
				boolean semiColonSeparator = false;
				for (String predicate : subjectToPredicates.get(subject)) {
					if (semiColonSeparator) {
						sb.append("; ");
					}
					sb.append(predicate);
					sb.append(" ");
					semiColonSeparator = true;

					boolean commaSeparator = false;
					for (String object : predicateToObjects.get(predicate)) {
						if (commaSeparator) {
							sb.append(", ");
						}
						sb.append(object);
						commaSeparator = true;
					}
				}
				sb.append(" ");
				sb.append(".\n");
			}
			return sb.toString();
		}
	}
}