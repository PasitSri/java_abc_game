import java.awt.*;
import javax.swing.*;

public class DrawLine extends JPanel{
	String[][] board={ {"A","B","C","D"},{"E","F","G","H"},{"I","J"," ","K"}};
	int size=200;
	int w=800, h=600;
	public void paintComponent(Graphics g){
		int text_x=80, text_y=100;
		g.setFont(new Font("Ubuntu", Font.PLAIN, 70));
		for(int r=0; r<3; r++){
			g.drawLine(0, size*r, w, size*r);
			for(int c=0; c<4; c++){
				g.drawString(board[r][c], text_x, text_y);
				g.drawLine(size*c, 0, size*c, h);
				text_x += size;
			}
			text_y += size;
			text_x = 65;
		}
	}

	public static void main(String[] args){
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBackground(Color.white);
		window.setSize(800, 600);

		DrawLine panel = new DrawLine();

		window.add(panel);
		window.setVisible(true);
	}

}
