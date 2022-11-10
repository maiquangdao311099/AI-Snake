
public class GameRunner implements Runnable {

	private GameBoard map;
	
	
	public GameRunner(GameBoard map) {
		this.map=map;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(GameBoard.GAME_DELAY);
				map.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public GameBoard getPanel() {
		return map;
	}

	public void setPanel(GameBoard map) {
		this.map = map;
	}
	
}
