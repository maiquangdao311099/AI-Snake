import java.util.ArrayList;
import java.util.List;

public class Snake {
	
	private Block head;
	private Block tail;
	private Block food;
        private int score = 0;
	
	private List<Block> SnakeBody=new ArrayList<>();
	
	public Snake(Block head,Block tail) {
		this.head=head;
		this.tail=tail;
	}

	public List<Block> getCompleSnake() {
		return SnakeBody;
	}
	

	public void setCompleSnake(List<Block> compleSnake) {
		this.SnakeBody = SnakeBody;
	}

	public Block getHead() {
		return head;
	}

	public void setHead(Block head) {
		this.head = head;
	}

	public Block getTail() {
		return tail;
	}

	public void setTail(Block tail) {
		this.tail = tail;
	}

	public Block getFood() {
		return food;
	}

	public void setFood(Block food) {
		this.food = food;
	}
        
        public int getScore(){
            String strScore;
            return score;
        }
        
        
	
	public void moveSnake(Block positiom) {
		SnakeBody.add(0,positiom);

		setHead(SnakeBody.get(0));
		
                
		if(positiom.equals(food)) {
                    score += 10;
		}else {
			SnakeBody.remove(getTail());
			setTail(SnakeBody.get(SnakeBody.size()-1));
		}
	}
	
	
	
}
