package inf.unibz.it.obda.api.inference.reasoner;

import inf.unibz.it.obda.queryanswering.Statement;

public interface DataQueryReasoner {

	public Statement getStatement(String sparql)throws Exception;

}
