import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class Algorithm {
	GameBoard map;
	
	public Algorithm(GameBoard panel) {
		this.map=panel;
	}

	public LinkedList<Block> Search(int type,Block startNode, Block destinationNode, Snake mySnake){
		switch(type) {
			case GameBoard.A_STAR:{
				return Astar(startNode, destinationNode);
			}
			case GameBoard.BFS:{
				return BFS(startNode, destinationNode);
			}
			case GameBoard.DFS:{
				return DFS(startNode, destinationNode);
			}
                        case GameBoard.FORWARD_A_STAR:{
                                return ForwardCheckingAstar(startNode, destinationNode, mySnake);
                        }
		}
		
		throw new RuntimeException("Wrong search Type " + type);
	}
	
	/**
	 * Calculate Manhattan distance
	 */
	public int manhattanDis(Block source,Block destination){
		int xAbs=Math.abs(source.getX()-destination.getX());
		int yAbs=Math.abs(source.getY()-destination.getY());
		return xAbs+yAbs;
	}
	
	/**
	 * Calculate euclidian distance
	 */
	public int euclidianDis(Block source,Block destination){
		int xAbs=Math.abs(source.getX()-destination.getX());
		int yAbs=Math.abs(source.getY()-destination.getY());
		
		return (int)Math.sqrt(xAbs * xAbs + yAbs * yAbs);
	}
	
	
	/**
	 * 
	 * @param startNode
	 * @param destinationNode
	 * @return LinkedList<MySnakeNode>
	 */
	private LinkedList<Block> Astar(Block startNode, Block destinationNode){
		PriorityQueue<Block> openNodesQueue=new PriorityQueue<Block>();
	
		LinkedList<Block> closedNodesList = new LinkedList<Block>();

		startNode.setG(0);

		startNode.setH(euclidianDis(startNode,destinationNode));
		
		startNode.setParent(null);
		openNodesQueue.add(startNode);
		
		//Run loop until openNodesQueue became empty
		while(!openNodesQueue.isEmpty()){
			
			//remove a node from the queue 
			//because it is PriorityQueue so it will give us the node which has lowest F
			Block polledNode = (Block)openNodesQueue.remove();

			//if polledNode is destination then just make path and return
			if(polledNode.equals(destinationNode)){
				return tracePath(polledNode);
                                
			}
			
			//find all neighbour of the polledNode
			List<Block> neighbourNodes = polledNode.myNeighbors();

			for(int i=0; i < neighbourNodes.size(); i++){
				
				//get neighbour one by one
				Block neighborNode = neighbourNodes.get(i);
				
				boolean isInOpen = openNodesQueue.contains(neighborNode);
				boolean isInClosed = closedNodesList.contains(neighborNode);
				
				
				//calculate distance between polledNode and this neighborNode
				//and add that distance into the polledNode G which was his own distance from startNode
				
				int neighborDistanceFromStart = polledNode.getG() + manhattanDis(polledNode, neighborNode);
			
				if((!isInOpen && !isInClosed) /*|| neighborDistanceFromStart < neighborNode.getG() */){

					//set the parameters of this neighborNode
					neighborNode.setParent(polledNode); 
					neighborNode.setG(neighborDistanceFromStart); 
					neighborNode.setH(euclidianDis(neighborNode,destinationNode));
					
					
					if(isInClosed){
						closedNodesList.remove(neighborNode);
					}
					if(!isInOpen && canMove(neighborNode)){
						openNodesQueue.add(neighborNode);
					}
				}
			}
			closedNodesList.add(polledNode);
		}
		
		
		return null;
	}
	
        private LinkedList<Block> ForwardCheckingAstar(Block startNode, Block destinationNode, Snake mySnake){
		PriorityQueue<Block> openNodesQueue=new PriorityQueue<Block>();
	
		LinkedList<Block> closedNodesList = new LinkedList<Block>();

		startNode.setG(0);

		startNode.setH(manhattanDis(startNode,destinationNode));
		
		startNode.setParent(null);
		openNodesQueue.add(startNode);
		
		//Run loop until openNodesQueue became empty
		while(!openNodesQueue.isEmpty()){
			
			//remove a node from the queue 
			//because it is PriorityQueue so it will give us the node which has lowest F
			Block polledNode = (Block)openNodesQueue.remove();

			//if polledNode is destination then just make path and return
                        List<Block> fwneighbourNodes = mySnake.getTail().myNeighbors();
			if(polledNode.equals(destinationNode)){
                            Block clone = new Block(polledNode.getX(), polledNode.getY());
                            for(Block iterator: fwneighbourNodes) {
                                LinkedList<Block> forwardCheckingList = Astar(clone, iterator);
                                if(forwardCheckingList != null)
				return tracePath(polledNode);
                                else
                                return null;
                            }
			}
			
			//find all neighbour of the polledNode
			List<Block> neighbourNodes = polledNode.myNeighbors();

			for(int i=0; i < neighbourNodes.size(); i++){
				
				//get neighbour one by one
				Block neighborNode = neighbourNodes.get(i);
				
				boolean isInOpen = openNodesQueue.contains(neighborNode);
				boolean isInClosed = closedNodesList.contains(neighborNode);
				
				
				//calculate distance between polledNode and this neighborNode
				//and add that distance into the polledNode G which was his own distance from startNode
				
				int neighborDistanceFromStart = polledNode.getG() + manhattanDis(polledNode, neighborNode);
			
				if((!isInOpen && !isInClosed) /*|| neighborDistanceFromStart < neighborNode.getG() */){

					//set the parameters of this neighborNode
					neighborNode.setParent(polledNode); 
					neighborNode.setG(neighborDistanceFromStart); 
					neighborNode.setH(manhattanDis(neighborNode,destinationNode));
					
					
					if(isInClosed){
						closedNodesList.remove(neighborNode);
					}
					if(!isInOpen && canMove(neighborNode)){
						openNodesQueue.add(neighborNode);
					}
				}
			}
			closedNodesList.add(polledNode);
		}
		
		
		return null;
	}
        
        
        
        
	/**
	 * Discover Neighbors and add them in ArrayList at end
	 * Then pop one Node from end of ArrayList because it is DFS
	 * @param startNode
	 * @param destinationNode
	 * @return LinkedList<MySnakeNode>
	 */
	private LinkedList<Block> DFS(Block startNode, Block destinationNode){

		ArrayList<Block> open=new ArrayList<Block>();
		ArrayList<Block> closed=new ArrayList<Block>();

		open.add(startNode);
		startNode.setParent(null);
		while(!open.isEmpty()) {
			
			Block node=open.remove(open.size()-1);
			
			if(node.equals(destinationNode)) {
				LinkedList<Block> path=tracePath(node);
                                closed.clear();
				return path;
			}
			closed.add(node);
			List<Block> neighbourNodes = node.myNeighbors();
			
			for(Block iterator: neighbourNodes) {
				boolean isInOpen = open.contains(iterator);
				boolean isInClosed = closed.contains(iterator);
				
				if(!isInOpen && !isInClosed && canMove(iterator)){
					iterator.setParent(node);
					open.add(iterator);
				}
			}
			

		}
		
		return null;
	}
	
	
	/**
	 * Discover Neighbors and add them in ArrayList at end
	 * Then pop one Node from start of ArrayList because it is BFS
	 * @param startNode
	 * @param destinationNode
	 * @return LinkedList<MySnakeNode>
	 */
	private LinkedList<Block> BFS(Block startNode, Block destinationNode){

		ArrayList<Block> open=new ArrayList<Block>();
		ArrayList<Block> closed=new ArrayList<Block>();

		open.add(startNode);
		startNode.setParent(null);
		while(!open.isEmpty()) {
			
			Block node=open.remove(0);
			
			if(node.equals(destinationNode)) {
				LinkedList<Block> path=tracePath(node);
                                closed.clear();
				return path;
			}
			closed.add(node);
			List<Block> neighbourNodes = node.myNeighbors();
			
			for(Block child: neighbourNodes) {
				boolean isInOpen = open.contains(child);
				boolean isInClosed = closed.contains(child);
				
				if(!isInOpen && !isInClosed && canMove(child)){
					child.setParent(node);
					open.add(child);
				}
			}
			
		}
		
		return null;
	}
	
	
	/**
	 * Find a appropriate random Neighbor
	 * @return MySnakeNode
	 */
	public Block getRandomMove() {
		List<Block> list = map.mySnake.getHead().myNeighbors();

		for(int a=0; a<list.size();a++) {
			Block node=list.get(a);
			
			if(canMove(node)) {
				return node;
			}
			
		}
		return null;
	}
	
	
	
	public LinkedList<Block> tracePath(Block node){

		LinkedList<Block> path = new LinkedList<Block>();
		
		while(node.getParent() !=null){
			path.addFirst(node);
			node = node.getParent();
		}
					
		return path;
	}
	/**
	 * @return should we process this node
	 */
	public boolean canMove(Block n){
		//if node is out of screen MAX
		if(n.getX()>(GameBoard.MAP_WIDTH-GameBoard.BLOCK_SIZE) ||
				n.getY()>(GameBoard.MAP_HEIGHT-GameBoard.BLOCK_SIZE)) {
			return false;
		}
		//if node is out of screen MIN
		if(n.getX()<0 ||
				n.getY()<0) {
			return false;
		}

		boolean  shouldProceed = !map.mySnake.getCompleSnake().contains(n);
//		System.out.println("shouldProcess: "+shouldProceed);
		return shouldProceed;
		
	}
	
	
}
