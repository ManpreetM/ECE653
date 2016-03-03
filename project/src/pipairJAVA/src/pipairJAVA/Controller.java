package pipairJAVA;

import java.util.List;

public interface Controller {
	public CallGraph readCallGraph(String fileName);
	public void printBugList(List<Bug> bugList);
}