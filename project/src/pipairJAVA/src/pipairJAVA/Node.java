package pipairJAVA;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * implemented based on following assumption:
 * 1. initNode is a one-time process, which means inner state of node is immutable
 * @author charleszhuochen
 *
 */
public class Node {
	protected String nodeName;
	protected Map<Integer, String> callsMap;
	protected Set<List<Integer>> supportP;
	protected boolean isRefreshed;
	
	public Node(String name) {
		this.nodeName = name;
		this.callsMap = new HashMap<Integer, String>();
		this.supportP = new HashSet<List<Integer>>();
		this.isRefreshed = false;
	}
	
    /**
     * this function is lazy evaluation for supportP
     * called by getSupportP()
     */
	protected void refreshSupportP() {
		List<Integer> sortedList = new ArrayList<Integer>(this.callsMap.keySet());
		Collections.sort(sortedList);
		for(int i = 0; i < sortedList.size()-1; i++)
			for(int j = i+1; j < sortedList.size(); j++) {
				List<Integer> newPair = new ArrayList<Integer>(2);
				newPair.add(sortedList.get(i));
				newPair.add(sortedList.get(j));
				this.supportP.add(newPair);
			}
		this.isRefreshed = true;
	}
	
	public void addCall(String fName) {
		this.callsMap.put(fName.hashCode(), fName);
	}
	
	public Set<List<Integer>> getSupportP() {
		if(!this.isRefreshed) {
			this.refreshSupportP();
		}
		return this.supportP;
	}
	
	public void print() {
		System.out.println("nodeName: "+this.nodeName);
		for(String callsName : this.callsMap.values()) 
			System.out.println("\tcall function: "+callsName);
		System.out.println("\tsupport pairs are:");
		
		Set<List<Integer>> supportP = this.getSupportP();
		for(List<Integer>pair : supportP) {
			System.out.println("< "+this.callsMap.get(pair.get(0))
					+ " , "+ this.callsMap.get(pair.get(1)) + " >");
		}
	}
}
