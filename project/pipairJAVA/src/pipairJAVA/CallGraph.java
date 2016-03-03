package pipairJAVA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CallGraph {
    protected HashMap<Integer, String> functionMap;
    protected HashMap<Integer, HashSet<Integer>> nodeMap;
    protected HashMap<Integer, HashMap<Integer, Integer>> supportP;
    protected HashMap<Integer, Integer> supportF;
    protected HashSet<String> funcSet;

    public CallGraph() {
        functionMap = new HashMap<Integer, String>();
        nodeMap = new HashMap<Integer, HashSet<Integer>>();
        supportP = new HashMap<Integer, HashMap<Integer, Integer>>();
        supportF = new HashMap<Integer, Integer>();
        funcSet = new HashSet<String>();
    }

    /**
     * update supportP by callSet
     * @param callIdSet : a callSet of a new added node
     */
    protected void updateSupportP(HashSet<Integer> callIdSet) {
        List<Integer> sortedList = new ArrayList<Integer>(callIdSet);
        Collections.sort(sortedList);
        for(int i = 0; i < sortedList.size()-1; i++) {
            HashMap<Integer, Integer> supportPi;
            int callAId = sortedList.get(i);
            if((supportPi = this.supportP.get(callAId)) == null) {
                supportPi = new HashMap<Integer, Integer>();
                this.supportP.put(callAId, supportPi);
            }
            for(int j = i+1; j < sortedList.size(); j++) {
                int callBId = sortedList.get(j);
                if(supportPi.get(callBId) == null) {
                    supportPi.put(callBId, 1);
                } else {
                    supportPi.put(callBId, supportPi.get(callBId)+1);
                }
            }
        }
    }

    /**
     * update supportF by callSet
     * @param callIdSet: the callSet of a new added node
     */
    protected void updateSupportF(HashSet<Integer> callIdSet) {
        for(int callId : callIdSet) {
            if(this.supportF.get(callId) == null) {
                this.supportF.put(callId, 1);
            }
            else {
                this.supportF.put(callId, this.supportF.get(callId)+1); 
            }
        }
    }

    /**
     * add a new node to this call graph
     * If there is a node already has the same name, do nothing
     * @param nodeName
     * @param callNameList
     */
    public void addNode(String nodeName, List<String> callNameList) {
        int nodeId = nodeName.hashCode();
        if(this.nodeMap.containsKey(nodeId))
            return;

        this.functionMap.put(nodeId, nodeName);
        
        HashSet<Integer> callIdSet = new HashSet<Integer>();
        for(String callName : callNameList) {
            int callId = callName.hashCode();
            this.functionMap.put(callId, callName);
            callIdSet.add(callId);
        }
        this.updateSupportF(callIdSet);
        this.updateSupportP(callIdSet);
        this.nodeMap.put(nodeId, callIdSet);
    }

    public HashMap<Integer, 
	    HashMap<Integer, Integer>> getSupportP() {
        return this.supportP;
    }

    public HashMap<Integer, Integer> getSupportF() {
        return this.supportF;
    }
    
    public HashMap<Integer, String> getFunctionMap() {
        return this.functionMap;
    }
    
    public HashMap<Integer,HashSet<Integer>> getNodeMap() {
        return this.nodeMap;
    }
    /**
     * check whether a node has a pair composed by A B (<A, B> or <B, A>)
     * better to call this method by callAId is the checked call for checkPair <A, B> or <B,A>
     * because if callSet doesn't have callAId, it would return false immediately
     * after first contains check and won't check for B.
     * @param nodeId
     * @param callAId: the call Id that need to check
     * @param callBId
     * @return true if nodeId has pair <callAId, CallBId> or <callBId, callAId>
     */
    public boolean isNodeHasPair(int nodeId, int callAId, int callBId) { 
        HashSet<Integer> callSet = this.nodeMap.get(nodeId);
        return callSet.contains(callAId) && callSet.contains(callBId);
    }
    
    public void printNode(String nodeName) {
        int nodeId = nodeName.hashCode();
        System.out.format("node %s calls:\n",
                this.functionMap.get(nodeId));
        for(int callId : this.nodeMap.get(nodeId)) {
            System.out.println(this.functionMap.get(callId));
        }
        System.out.println("has pair:");
        for(Integer node1 : this.supportP.keySet()) {
            HashMap<Integer, Integer> node1Nbr = this.supportP.get(node1);
            for(Integer node2 : node1Nbr.keySet()) {
                if(this.isNodeHasPair(nodeId, node1, node2)) {
                    System.out.format("<%s,%s>\n",
                               this.functionMap.get(node1),
                               this.functionMap.get(node2));
                }
            }
        }
    }
    
    public void printGraphInfo() {
        System.out.println("size of supportP is:"+this.supportP.size());
        System.out.println("size of supportF is:"+this.supportF.size());
        System.out.println("Pair\t\tcount");
        for(Integer node1 : this.supportP.keySet()) {
            HashMap<Integer, Integer> node1Nbr = this.supportP.get(node1);
            for(Integer node2 : node1Nbr.keySet()) {
                System.out.format("<%s,%s>\t%d\n", 
                        this.functionMap.get(node1),
                        this.functionMap.get(node2),
                        node1Nbr.get(node2));
            }
        }
        System.out.println("Func\tcount");
       for(Integer func : this.supportF.keySet()) {
           System.out.format("%s\t%d\n",
                   this.functionMap.get(func),
                   this.supportF.get(func));
       }
    }
}
