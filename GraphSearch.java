package graphSearch;

import graphClient.XGraphClient;

import graphClient.SEdge;
//import java.util.LinkedList;
//import java.util.Stack;
//import java.util.Queue;
//import java.util.HashSet;
//import java.util.HashMap;
import java.util.*;

public class GraphSearch {

	public int afm = 58352; // AFM should be in form 5XXXX
	public String firstname = "Γεωργιος";
	public String lastname = "Τοκατλιδης";

	XGraphClient xgraph;

	public GraphSearch(XGraphClient xgraph) {
		this.xgraph = xgraph;
	}


	
	public Result findResults() {
		Result res = null;

		// ////////////////////
		// WRITE YOUR OWN CODE
		// ////////////////////

		// EXAMPLE CODE

		// Example of creating the Result object
		res = new Result();
			

		// Retrieve the first node of the unknown graph
		long firstNode = xgraph.firstNode();

		// Print the ID of the first node
		System.out.println("The id of the first node is: " + firstNode);

		// Inform that GraphSearch starts
		System.out.println("Graph search from node : " + firstNode);

		// Retrieve the neighbors of the first node
		long[] neighbors = xgraph.getNeighborsOf(firstNode);

		// Convert long[] to ArrayList<Long>
//		Long[] a = new Long[10];
//		Arrays.fill(a, 123L);
//		ArrayList<Long> n = new ArrayList<Long>(Arrays.asList(a));
//				
//		long[] input = new long[]{1,2,3,4};
//		List<Long> output = new ArrayList<Long>();
//		for (long value : input) {
//		    output.add(value);
//		}
//			
		// Print all the neighbors of the firstNode
		// Approach A
		int numOfNeighbors = neighbors.length;

		for (int i = 0; i < numOfNeighbors; i++) {
			System.out.println("Neighbor " + i + ", id: " + neighbors[i]);
		}

		// newline
		System.out.println();

		// Print all the neighbors of the firstNode
		// Approach B
		for (long id : neighbors) {
			System.out.println("Neighbor id: " + id);
		}
		
		
		
		//Needed variables
		HashMap <Long,Integer> EXPLORED = new HashMap<Long,Integer>();  
		Queue<Long> EXPLORER = new LinkedList<>();
		int edges=0,KEY=0;
		int iter;
		boolean flag;
        ArrayList <Long> temp = new ArrayList <Long> (); 
		
		//Initialization of our queue
		EXPLORER.add(firstNode);
		
		//Beginning of BFS exploration routine
		while(!EXPLORER.isEmpty()) {
		   	
			 iter=EXPLORER.size();
			 KEY++;
			 
			for(int i=0; i<iter; i++) {                        //every new 'for' loop brings one more level to the graph. Length is defined by our queue
				                                               //after ending our graph search there are some remaining elements that don't affect our leveling method
				                                               //thanks to the next 'if' condition for already explored nodes
				flag=false;
				long NEW_NODE = EXPLORER.remove();              //removal of head
				
				if(!EXPLORED.containsKey(NEW_NODE)) {          
					EXPLORED.put(NEW_NODE,KEY);
					temp.add(NEW_NODE);
	            	flag=true;
				}
				
				if(flag) {                    //only if it's a new value-node it will be added else it's already explored so are it's neighbors 
					
				neighbors = xgraph.getNeighborsOf(NEW_NODE);
				
				for(long node : neighbors) {                    //(translates as) for each "node" of the array "neighbors"
					
					if(!EXPLORED.containsKey(node)) {              //neighbor nodes that are new...
						EXPLORER.add(node);		                //add them to our queue
						edges++;                                //which means one more linkage=one more edge					
					}
				}
				}	
			}
				
		} 
		
		boolean isBipartite=true;                                  //'for' loops to test bipartite-ness
		SEdge obj=null;
		
		for(int i=1; i<EXPLORED.size()-1; i++) {                   //for every node               
			
			neighbors = xgraph.getNeighborsOf(temp.get(i));        //gimme-gimme-gimme your neighbors  
			int c_key = EXPLORED.get(temp.get(i));                 //and the node's level
			
			for(int j=0; j<neighbors.length-1; j++) {              //for every neighbor 
				
				int n_key = EXPLORED.get(neighbors[j]);            //return its level
				
				if(n_key==c_key) {                                 //if neighbor's and current node's levels are identical 
					isBipartite=false;                             //be-gone bipartite graph 
					obj = new SEdge (temp.get(i),neighbors[j]);
					break;
				}
			}
			
		}
		
		// WRITE ALL RESULTS INTO THE RESULT OBJECT
		
		// COMPULSORY questions
		res.n = EXPLORED.size(); //(Number of nodes, type: int)
		res.m = edges; //(Number of edges, type: int)

		// BONUS
		res.graphIsBipartite=isBipartite;//, must be true if the graph is bipartite
		res.oddCycleEdge=obj;//: Any edge (u,v) of any odd cycle, u:long, v:long 
						
		// Return the result Object with the results of the computation
		return res;
	}

}
