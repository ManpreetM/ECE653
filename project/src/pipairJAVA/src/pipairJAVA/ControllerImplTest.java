package pipairJAVA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Just for testing upper modules which needs controller
 * @author charleszhuochen
 *
 */
public class ControllerImplTest implements Controller{
    public static void main(String [] args) {
    		Controller c = new ControllerImplTest();
    		c.readCallGraph("mainCallGraph.txt");
    }
    
    protected Pattern callsP;
    protected Pattern nodeP;
    
    public ControllerImplTest() {
    		this.callsP = Pattern.compile("^.*calls function "
				+ "\'(\\w+)\'"
				+ ".*$");
    		this.nodeP = Pattern.compile("^Call graph node for function: "
    				+ "\'(\\w+)\'"
    				+ ".*$");
    }
    
	@Override
	public CallGraph readCallGraph(String fileName) {
		CallGraph g = null;
		BufferedReader in= null;
		
		try {
			in = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		g = new CallGraph();	
		String thisLine = null;
		try {
			thisLine = in.readLine();
			while(true) {
				if(thisLine == null)
					break;
				Matcher nodeM = this.nodeP.matcher(thisLine);
				if(nodeM.matches()) {
//					System.out.println("nodeName: "+nodeM.group(1)+"\n");
//					Node newNode = new Node(nodeM.group(1));
				    String nodeName = nodeM.group(1);
				    List<String> callNameList = new ArrayList<String>();
					Matcher callsM = this.callsP.matcher("");
					while(true) {
						thisLine = in.readLine();
						if(thisLine == null)
							break;
						if(callsM.reset(thisLine).matches()) {
//							System.out.println("\tcall function: "+callsM.group(1)+"\n");
//							newNode.addCall(callsM.group(1));
						    callNameList.add(callsM.group(1));
						} else if(nodeM.reset(thisLine).matches())
							break;
					}
//				newNode.print();
				g.addNode(nodeName, callNameList);
				g.printNode(nodeName);
				} else {
					thisLine = in.readLine();
				}
			}

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.printGraphInfo();
		return g;
	}
}
