package pipairJAVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerImpl implements Controller {
	public static void main(String[] args){
		ControllerImpl controller = new ControllerImpl();
		controller.readCallGraph("mainCallGraph.txt");
		//let Analyzer analyze CallGraph
		//let Controller report bugs
	}

	@Override
	public CallGraph readCallGraph(String fileName) {
		// TODO Auto-generated method stub
		CallGraph graph = new CallGraph();
		File f = new File (fileName);
		FileReader r = null;
		try {
			r = new FileReader(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader b = new BufferedReader(r);
		String text;
		try {
			while((text = b.readLine())!= null){
			System.out.println(text);
			
			Pattern p = Pattern.compile("^Call graph node for function: \'(\\w+)\'\.*$");
			Matcher m = p.matcher(text);
			}
			
			
			//loop extract node to a graph
			//judge whether this line is node
				//TRUE: extract node name
				//loop extract calls of this node
			//add this node to the graph
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return graph;
	}

}
