import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Block implements Comparable<Block>  {

	int x;
	int y;
	int g,h=0;
	private Block parent=null;
	
	
	public Block(int x, int y) {
		this.x=x;
		this.y=y;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}
	
	
	
	public int getG() {
		return g;
	}


	public void setG(int g) {
		this.g = g;
	}


	public int getH() {
		return h;
	}


	public void setH(int h) {
		this.h = h;
	}

	public int getF() {
		return g+h;
	}
	
	

	public Block getParent() {
		return parent;
	}


	public void setParent(Block parent) {
		this.parent = parent;
	}

	public List<Block> myNeighbors(){
//		System.out.println("Me= ("+getX()+","+getY()+")");

		Block up = new Block(getX(),getY()-GameBoard.BLOCK_SIZE);
		Block down = new Block(getX(), getY()+GameBoard.BLOCK_SIZE);
		Block left = new Block(getX()-GameBoard.BLOCK_SIZE, getY());
		Block right = new Block(getX()+GameBoard.BLOCK_SIZE, getY());
		
//		System.out.println("Up= ("+up.getX()+","+up.getY()+")");
//		System.out.println("Down= ("+down.getX()+","+down.getY()+")");
//		System.out.println("Left= ("+left.getX()+","+left.getY()+")");
//		System.out.println("Right= ("+right.getX()+","+right.getY()+")");

		
//		MySnakeNode northEast = new MySnakeNode(getX()+MyContants.MY_SNAKE_WIDTH_HEIGHT, getY()-MyContants.MY_SNAKE_WIDTH_HEIGHT);
//		MySnakeNode northWest = new MySnakeNode(getX()-MyContants.MY_SNAKE_WIDTH_HEIGHT, getY()-MyContants.MY_SNAKE_WIDTH_HEIGHT);
//		MySnakeNode southEast = new MySnakeNode(getX()+MyContants.MY_SNAKE_WIDTH_HEIGHT, getY()+MyContants.MY_SNAKE_WIDTH_HEIGHT);
//		MySnakeNode southWest = new MySnakeNode(getX()-MyContants.MY_SNAKE_WIDTH_HEIGHT, getY()+MyContants.MY_SNAKE_WIDTH_HEIGHT);

		
//		System.out.println("northEast= ("+northEast.getX()+","+northEast.getY()+")");
//		System.out.println("northWest= ("+northWest.getX()+","+northWest.getY()+")");
//		System.out.println("southEast= ("+southEast.getX()+","+southEast.getY()+")");
//		System.out.println("southWest= ("+southWest.getX()+","+southWest.getY()+")");

		
		List<Block> neighborsList=new ArrayList<>();
		neighborsList.add(up);
		neighborsList.add(down);
		neighborsList.add(left);
		neighborsList.add(right);
		
//		lis.add(northWest);
//		lis.add(northEast);
//		lis.add(southEast);
//		lis.add(southWest);

		return neighborsList;

	}

        public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(obj instanceof Block){
			Block secondNode = (Block) obj;
			if(this.x == secondNode.x && this.y == secondNode.y){
				return true;
			}
		}
		return false;
	}


	/**
	 * Used for sorting the queue
	 * We add the node at start of queue which has lowest F
	 * Because we will poll the node which has lowest F first
	 */
	@Override
	public int compareTo(Block o) {
		int thisVal = this.getF();
		int otherVal = o.getF();
		
		int value = thisVal - otherVal;
		
		return (value>0)?1:(value<0)? -1: 0;
	}
	
}
