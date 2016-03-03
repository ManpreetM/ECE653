package pipairJAVA;

import java.io.IOException;
import java.util.List;

public interface Controller {
	public CallGraph readCallGraph(String fileName) throws IOException;
	public void printBugList(List<Bug> bugList);
}