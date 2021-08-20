import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class DrawLine extends JPanel{
	String[][] board={ {"A","B","C","D"},{"E","F","G","H"},{"I","J"," ","K"}};
	int[] blank_position = {2,2};
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

	public void randomAlpha(){
		String buffer;
		Random rand = new Random();
		for(int r=0; r<3; r++){
			for(int c=0; c<4; c++){
				int ranRow = rand.nextInt(3);
				int ranCol = rand.nextInt(4);
				buffer = board[r][c];
				board[r][c] = board[ranRow][ranCol];
				board[ranRow][ranCol] = buffer;
				if(board[ranRow][ranCol] == " "){
					blank_position[0] = ranRow;
					blank_position[1] = ranCol;
				}
				else if(board[r][c] == " "){
					blank_position[0] = r;
					blank_position[1] = c;
				}
			}
		}  
	}


	boolean checkWinner(String[][] board){
		String[][] boardWinner = { {"A","B","C","D"},{"E","F","G","H"},{"I","J","K"," "}};  
		for(int r=0; r<3; r++){
			for(int c=0; c<4; c++){
				if(board[r][c] != boardWinner[r][c]){
					return false;
				}
			}
		}
		return true;
	}


}
