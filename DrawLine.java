import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.FontMetrics;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;


public class DrawLine extends JFrame{
	static String[][] board={ {"A","B","C","D"},{"E","F","G","H"},{"I","J"," ","K"}};
	int[] blank_position = {2,2};
	int size=200;
	int w=800, h=637;

	public void SaveGame() {
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			Element rootElement = doc.createElement("ABCBlockMAP");
			doc.appendChild(rootElement);

			Element information = doc.createElement("information");
			rootElement.appendChild(information);
			Element GameVersion = doc.createElement("GameVersion");
			GameVersion.appendChild(doc.createTextNode("0"));
			Element PlayTime = doc.createElement("PlayTime");
			PlayTime.appendChild(doc.createTextNode("0"));
			Element MoveCount = doc.createElement("MoveCount");
			MoveCount.appendChild(doc.createTextNode("0"));

			Element Map = doc.createElement("Map");
			rootElement.appendChild(Map);

			for (int r = 0;r < 3;r++) {
				String data = "";
				String Name = "Row" + String.valueOf(r+1);
				for (int c = 0;c < 4;c++) {
					data += board[r][c];
				}
				Element Row = doc.createElement(Name);
				Row.appendChild(doc.createTextNode(data));
				Map.appendChild(Row);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("java_abc_game.xml"));
			transformer.transform(source, result);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void LoadGame() {
		try{
			File inputFile = new File("java_abc_game.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);

			for (int y = 0;y < 3;y++) {
				String Name = "Row" + String.valueOf(y+1);
				NodeList rowList = doc.getElementsByTagName(Name);
				Element row = (Element) rowList.item(0);
				String rowText = row.getTextContent();
				String[] rowString = rowText.split("");
				System.out.println(rowText);
				for (int x = 0;x < 4;x++) {
					board[y][x] = rowString[x];
					if(Character.isWhitespace(rowText.charAt(x))){
						blank_position[0]=y;
						blank_position[1]=x;
						System.out.printf("%d %d", x,y);
					}
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}


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
		setTitle("Sorting Game");
		setSize(w, h);
		LoadGame();
		repaint();
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				swapChar(e.getX(), e.getY());
				SaveGame();
				repaint();
				checkWinner(board);
				if(checkWinner(board) == true){
					winSceen();
				}
			}

		});

		setVisible(true);
		//randomAlpha();
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
				if(board[ranRow][ranCol].contains(" ")){
					blank_position[0] = ranRow;
					blank_position[1] = ranCol;
				}
				else if(board[r][c].contains(" ")){
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

	public static void winSceen(){
		JFrame f = new JFrame();
		Label l = new Label("VICTORY");
		f.add(l);
		f.setSize(800,600);
		f.setBackground(Color.black);
		f.setVisible(true);
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