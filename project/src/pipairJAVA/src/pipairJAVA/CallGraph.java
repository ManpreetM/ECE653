package pipairJAVA;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CallGraph {
	protected Set<Node> nodeSet;
	protected Set<String> functionSet;
	
//	protected Map<Integer, String> functionMap;
	protected Map<List<Integer>, Integer> supportP;
	protected Map<Integer, Integer> supportF;
	
	public CallGraph() {
		nodeSet = new HashSet<Node>();
		functionSet = new HashSet<String>();
		supportP = new HashMap<List<Integer>, Integer>();
		supportF = new HashMap<Integer, Integer>();
	}
	
	public boolean addNode(Node newNode) {
		if(nodeSet.contains(newNode)) {
			return false;
		}
		/*update functionSet by newNode
		 * update supportP by newNode
		 * update supportF by newNode
		 * update nodeSet
		 */
		
		nodeSet.add(newNode);
		
		return true;
	}
}
