import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.FontMetrics;

public class DrawLine extends JFrame{
	static String[][] board={ {"A","B","C","D"},{"E","F","G","H"},{"I","J"," ","K"}};
	int[] blank_position = {2,2};
	int size=200;
	int w=800, h=637;
	boolean checkWinner = false;

	public void paint(Graphics g){
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.white);
		g.fillRect(0,0,w,h);
		g.setColor(Color.black);
		g.setFont(new Font("Ubuntu", Font.PLAIN, 70));
		for(int r=0; r<3; r++){
			for(int c=0; c<4; c++){
				int n = fm.stringWidth(board[r][c]);
				g.drawString(board[r][c], (c*size)+(size/2-n*2), 37+(r*size)+(size/2+15));
				g.drawRect(600-(c*size),37+(r*size),size,size);
			}
		}
	}

	public DrawLine(){
		setResizable(false);
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
		randomAlpha();
	}

	public static void main(String[] args){
		new DrawLine();
//		checkWinner(board);
//		if(checkWinner(board) == true){
//			winSceen();
//		}
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


	public static boolean checkWinner(String[][] board){
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

//	public static void winSceen(){
//		JFrame f = new JFrame();
//		Label l = new Label("VICTORY");
//		f.add(l);
//		f.setSize(800,600);
//		f.setBackground(Color.black);
//		f.setVisible(true);
//	}

	public void swapChar(int mouseX, int mouseY){
		int block_x=0;
		int block_y=0;
		System.out.printf("%d, %d\n", mouseX, mouseY);
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

