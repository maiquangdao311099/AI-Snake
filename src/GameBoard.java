import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameBoard extends JPanel implements KeyListener{
        
	
	public final static int BLOCK_SIZE = 20;
        
        public final static int MAP_WIDTH = 40*BLOCK_SIZE;
	public final static int MAP_HEIGHT = 30*BLOCK_SIZE;

	public final static int GAME_DELAY = 30;
	
	
	public final static int A_STAR =1;
	public final static int DFS =2;
	public final static int BFS =3;
        public final static int FORWARD_A_STAR = 4;
        
        private Block currentFood;
        private Block nextFood;
        public JLabel points;
	
	private LinkedList<Block> pathToCurrentFood;
	
	public Snake mySnake;
	private final GameRunner gameRunner=new GameRunner(this);
	
	private final Algorithm mySnakeAi=new Algorithm(this);
	
	private int searchType=A_STAR;
	
	public GameBoard(){
        setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        setBackground(new Color(135, 206, 250));
        setFocusable(true);

        Block head=new Block(MAP_WIDTH/2, MAP_HEIGHT/2);
        Block tail=new Block(MAP_WIDTH/2,( MAP_HEIGHT/2)+BLOCK_SIZE);

        mySnake=new Snake(head,tail);
        mySnake.getCompleSnake().add(head);
        mySnake.getCompleSnake().add(tail);
        points = new JLabel(""+mySnake.getScore());
                points.setForeground(new Color(248,248,255));
		points.setBackground(new Color(135, 206, 250));
		points.setHorizontalAlignment(SwingConstants.CENTER);
		points.setFont(new Font("NewellsHand", Font.PLAIN, 120));
		points.setBounds(MAP_WIDTH/2-BLOCK_SIZE, MAP_HEIGHT/2-BLOCK_SIZE, MAP_WIDTH/2-BLOCK_SIZE, MAP_HEIGHT/2-BLOCK_SIZE);
		this.add(points);

        setFood();
        new Thread(gameRunner).start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		drawSnake(g);
		drawFood(g);
                points.setText(""+mySnake.getScore());
		
		if(getPathToCurrentFood()==null) {
			

			Block n=mySnake.getFood();

			LinkedList<Block> list=mySnakeAi.Search(searchType,mySnake.getHead(), mySnake.getFood(), mySnake);
			setPathToCurrentFood(list);
			
			if(list==null) {
				//no path found by search Algo take a random move
				moveRandom();
			}
		}
		if(getPathToCurrentFood()!=null) {
			Block node=getPathToCurrentFood().remove();
			mySnake.moveSnake(node);
		}

	}

	private void moveRandom() {
		//here we should make a random move so that snake tail move and maybe we find a 
		//path in next call
		Block position=mySnakeAi.getRandomMove();
		if(position!=null) {
			mySnake.moveSnake(position);
		}
	}
        
        public Block generateFood(Snake snake) {
		while(true){
			int x = 0,y;
			 x = BLOCK_SIZE*(int) (Math.random() * MAP_WIDTH/BLOCK_SIZE);
			 y = BLOCK_SIZE*(int) (Math.random() * MAP_HEIGHT/BLOCK_SIZE);
			 Block n=new Block(x,y);
			
			if(mySnakeAi.canMove(n)){
				return n;
			}
		}
	}
        
        public ArrayList<Block> generateFoods(Snake snake){
            ArrayList<Block> foodList = new ArrayList<Block>();
            int forwardSteps = 0;
            while(forwardSteps < 3){
			int x = 0,y;
			 x = BLOCK_SIZE*(int) (Math.random() * MAP_WIDTH/BLOCK_SIZE);
			 y = BLOCK_SIZE*(int) (Math.random() * MAP_HEIGHT/BLOCK_SIZE);
			 Block n=new Block(x,y);
			
			if(mySnakeAi.canMove(n) && !foodList.contains(n)){
				foodList.add(n);
                                forwardSteps++;
			}
		}
            return foodList;
        }

	private void setFood() {
		Block food=generateFood(mySnake);
		mySnake.setFood(food);
		setCurrentFood(food);
		setPathToCurrentFood(null);
	}
	
	private void drawFood(Graphics g) {
		g.setColor(new Color(255, 160, 122));
		g.fillRect(mySnake.getFood().getX(), mySnake.getFood().getY(), BLOCK_SIZE, BLOCK_SIZE);

	}
        
        public Block getCurretnFood() {
		return currentFood;
	}

	public void setCurrentFood(Block curretnFood) {
		this.currentFood = curretnFood;
	}

	public LinkedList<Block> getPathToCurrentFood() {
		return pathToCurrentFood;
	}

	public void setPathToCurrentFood(LinkedList<Block> pathToCurrentFood) {
		this.pathToCurrentFood = pathToCurrentFood;
	}
        
	private void drawSnake(Graphics g) {
//		System.out.println("snake length is"+mySnake.getCompleSnake().size());
		//getting complete snake to draw
		for(int a=0;a<mySnake.getCompleSnake().size(); a++) {
			Block node=mySnake.getCompleSnake().get(a);
			
			if(node.equals(mySnake.getHead())) {
				g.setColor(new Color(248,248,255)); 
			}else if(node.equals(mySnake.getTail())){
				g.setColor(new Color(248,248,255)); 

			}else {
				g.setColor(new Color(248,248,255));  
			}
			
			if(node.equals(mySnake.getFood())) {
				setFood();
			}
			g.fillRect(node.getX(), node.getY(), BLOCK_SIZE - 1, BLOCK_SIZE - 1);
			
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_A:{
				System.out.println("converting to aStar");

				searchType=A_STAR;
				break;
			}
			case KeyEvent.VK_D:{
				System.out.println("converting to DFS");

				searchType=DFS;
				break;
			}
			case KeyEvent.VK_B:{
				System.out.println("converting to BFS");

				searchType=BFS;
				break;
			}
                        case KeyEvent.VK_F:{
				System.out.println("converting to Forward Checking Astar");

				searchType=FORWARD_A_STAR;
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}


		
}
