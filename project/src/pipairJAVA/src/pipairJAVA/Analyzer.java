package pipairJAVA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Analyzer {
    protected final int T_SUPPORT;
    protected final double T_CONFIDENCE;

    public Analyzer() {
        this.T_SUPPORT = 3;
        this.T_CONFIDENCE = 0.65;
    }
    
    public Analyzer(int t_support, double t_confidence) {
        this.T_SUPPORT = t_support;
        this.T_CONFIDENCE = t_confidence;
    }
 
    public List<Bug> analyzeGraph(CallGraph g) {
        /*In this method we traverse supportP to find bugs,
         * thus we can use List rather than Set to store bugs
         * (bugs are unique since pairs in supportP are unique)
         * */
        List<Bug> bugList = new ArrayList<Bug>();
        HashMap<Integer, HashMap<Integer, Integer>> supportP = g.getSupportP();
        HashMap<Integer, Integer> supportF = g.getSupportF();
        HashMap<Integer, String> functionMap = g.getFunctionMap();
        HashMap<Integer, HashSet<Integer>> nodeMap = g.getNodeMap();
        /*traverse supportP to find bugs (bugName of Pair with conf # > T_CONF)*/
        for(Integer call1 : supportP.keySet()) {
            HashMap<Integer, Integer> node1Nbr = supportP.get(call1);
            for(Integer call2 : node1Nbr.keySet()) {
                int cnt_pair = node1Nbr.get(call2);
                
                if(cnt_pair < this.T_SUPPORT)
                    continue;
                
                double conf;
                int cnt_n1 = supportF.get(call1);
                int cnt_n2 = supportF.get(call2);
                
                conf = (double) cnt_pair / (double)cnt_n1;
                if(conf > this.T_CONFIDENCE) {
                    Bug b = new Bug(functionMap.get(call1), 
                            functionMap.get(call2),
                            cnt_pair,
                            conf);
                    bugList.add(b);
                }    
                conf = (double) cnt_pair / (double)cnt_n2;
                if(conf > this.T_CONFIDENCE) {
                    Bug b = new Bug(functionMap.get(call2), 
                            functionMap.get(call1),
                            cnt_pair,
                            conf);
                    bugList.add(b);
                }
            }    
        }
        
        for(Bug bug : bugList){
            for(int nodeId : nodeMap.keySet()) {
                HashSet<Integer> nodeSet = nodeMap.get(nodeId);
                if(nodeSet.contains(bug.nameHashCode) &&
                        !nodeSet.contains(bug.p1HashCode))
                        bug.addLocation(functionMap.get(nodeId));
            }
        }
        return bugList;
    }

}
