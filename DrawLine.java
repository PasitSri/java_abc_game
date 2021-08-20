import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.awt.event.MouseAdapter;

public class DrawLine extends JFrame{
	String[][] board={ {"A","B","C","D"},{"E","F","G","H"},{"I","J"," ","K"}};
	int[] blank_position = {2,2};
	int size=200;
	int w=800, h=600;

	public void paint(Graphics g){
		int text_x=80, text_y=100;
		g.setColor(Color.white);
		g.fillRect(0,0,w,h);
		g.setColor(Color.black);
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

	public DrawLine(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.white);
		setSize(w, h);
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				swapChar(e.getX(), e.getY());
				repaint();
			}
		});

		setVisible(true);
	}

	public static void main(String[] args){
		new DrawLine();
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

	public void swapChar(int mouseX, int mouseY){
		int block_x=0;
		int block_y=0;
		for(int r=0; r<3; r++){
			for(int c=0; c<4; c++){
				if(mouseX>block_x && mouseX<block_x+size && mouseY>block_y && mouseY<block_y+size){
					if(((r-1==blank_position[0]||r+1==blank_position[0]) && c==blank_position[1]) || ((c-1==blank_position[1]||c+1==blank_position[1]) &&r==blank_position[0])){
						board[blank_position[0]][blank_position[1]] = board[r][c];
						board[r][c] = " ";
						blank_position[0] = r;
						blank_position[1] = c;
					}
				}
				block_x += size;
			}
			block_x =0;
			block_y += size;
		}
	}

}

