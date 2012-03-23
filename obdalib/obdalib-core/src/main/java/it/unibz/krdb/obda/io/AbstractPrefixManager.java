package it.unibz.krdb.obda.io;

import java.util.Collection;
import java.util.Iterator;

/**
 * The prefix manager is administrating the prefixes for ontolgyie. It allows to
 * register and unregister prefixes for ontolgies and to query them.
 * 
 * @author Manfred Gerstgrasser
 * 
 */

public abstract class AbstractPrefixManager implements PrefixManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2034866002642539028L;
	private String defaultNamespace = null;

	@Override
	public String getDefaultNamespace() {
		return defaultNamespace;
	}

	@Override
	public void setDefaultNamespace(String uri) {
		if ((uri.charAt(uri.length() - 1) == '#') || (uri.charAt(uri.length() - 1) == '/')) {
			defaultNamespace = uri;
			return;
		}
		if (uri.charAt(uri.length() - 1) != '/') {
			defaultNamespace = uri + "#";
		}
	}

	public String getShortForm(String uri) {
		return getShortForm(uri, true, false);
	}

	public String getShortForm(String uri, boolean useDefaultPrefix) {
		return getShortForm(uri, useDefaultPrefix, false);
	}

	public String getShortForm(String uri, boolean useDefaultPrefix, boolean isLiteral) {
		StringBuilder result = new StringBuilder(uri);
		if (getDefaultNamespace() != null && useDefaultPrefix) {
			if (uri.length() > getDefaultNamespace().length()) {
				if (uri.substring(0, getDefaultNamespace().length()).equals(getDefaultNamespace())) {
					result.replace(0, getDefaultNamespace().length(), "");
					if (isLiteral) {
						result.insert(0, "<");
						result.append(">");
					} else {
						result.insert(0, ":");
					}
					return result.toString();
				}
			}
		}
		
		Collection<String> longnamespacesset = this.getPrefixMap().values();
		Iterator<String> longnamespaces = longnamespacesset.iterator();
		while (longnamespaces.hasNext()) {
			String longnamespace = longnamespaces.next();
			if (uri.length() > longnamespace.length()) {
				if (uri.substring(0, longnamespace.length()).equals(longnamespace)) {
					if (!isLiteral) {
						result.replace(0, longnamespace.length(), this.getPrefixForURI(longnamespace) + ":");
					} else {
						result.replace(0, longnamespace.length(), "&" + this.getPrefixForURI(longnamespace) + ";");
					}
					break;
				}
			}
		}
		return result.toString();
	}
}