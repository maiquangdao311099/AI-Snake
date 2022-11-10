import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class AISnakeMain extends JFrame{

	public AISnakeMain() {

        GameBoard map = new GameBoard();
        add(map);
        pack();
        setTitle("AI Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        //addKeyListener does not work without it stack (286727)
        setFocusable(true);
        
        addKeyListener(map);
        
	}
        
        public static void main(String[] args) {
		
		System.out.println("Press A for Astar");
		System.out.println("Press B for BFS");
		System.out.println("Press D for DFS");


		AISnakeMain window=new AISnakeMain();
		window.setVisible(true);

	}
}
