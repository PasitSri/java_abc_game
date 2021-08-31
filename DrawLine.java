import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.FontMetrics;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class DrawLine extends JFrame{
    int RowAmount = 5 , ColAmount = 5;
    int w=800, h=600;
    int ColSize = w/ColAmount , RowSize = h/RowAmount;
    String[ ][ ] board = new String[RowAmount][ColAmount];
    String[ ][ ] boardwinner = new String[RowAmount][ColAmount];
    int[] blank_position = {2,2};
	boolean winner = false;

    public static void main(String[] args){

        DrawLine play = new DrawLine();
        //play.IsSaved();
        //play.SetBoard();
        //System.out.println("win");
        //System.out.println(play.checkWinner());
        
        //if(play.checkWinner()){
            //System.out.println("win");
            //play.winSceen();
        //}
        //main(args);
    }
    /*public void IsSaved(){
        // เอาไว้ Check ว่าเซฟไว้รึป่าว
        File inputFile = new File("java_abc_game.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);

        NodeList list = doc.getElementsByTagName("Map");
        System.out.println(list);

        //System.out.println(doc.getChildNodes());
    
    }*/
    public void SetBoard(){
        int i = 65;
        for (int r = 0;r < RowAmount;r++) {
            for (int c = 0; c < ColAmount; c++) {
                if(i == 65 + ( RowAmount*ColAmount - 1)){
                    board[r][c] = " ";
                    boardwinner[r][c] = " ";
					blank_position[0] =r;
					blank_position[1] =c;
                }
                else {
                    board[r][c] = String.valueOf((char) i);
                    boardwinner[r][c] = String.valueOf((char) i);
                }
                i++;
            }
        }
		printBoard();
        /*System.out.print("---------------------\n");
        for (int r = 0;r < RowAmount;r++) {
            for (int c = 0; c < ColAmount; c++) {
                System.out.print(board[r][c]);
            }
            System.out.print("\n");
        }
        System.out.print("---------------------");
        System.out.print("---------------------\n");
        for (int r = 0;r < RowAmount;r++) {
            for (int c = 0; c < ColAmount; c++) {
                System.out.print(boardwinner[r][c]);
            }
            System.out.print("\n");
        }
        System.out.print("---------------------");*/
    }
	public void printBoard(){
		for(int r=0; r<5; r++){
			for(int c=0; c<5; c++){
				System.out.printf("%s", board[r][c]);
			}
		}
		System.out.println(' ');
	}
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

            for (int r = 0;r < RowAmount;r++) {
                String data = "";
                String Name = "Row" + String.valueOf(r+1);
                for (int c = 0;c < ColAmount;c++) {
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

            for (int y = 0;y < RowAmount;y++) {
                String Name = "Row" + String.valueOf(y+1);
                NodeList rowList = doc.getElementsByTagName(Name);
                Element row = (Element) rowList.item(0);
                String rowText = row.getTextContent();
                String[] rowString = rowText.split("");
                //System.out.println(rowText);
                for (int x = 0;x < ColAmount;x++) {
                    board[y][x] = rowString[x];
                    if(Character.isWhitespace(rowText.charAt(x))){
                        blank_position[0]=y;
                        blank_position[1]=x;
                        //System.out.printf("%d %d", x,y);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void paint(Graphics g){
		if(winner){
			g.setColor(Color.white);
			g.fillRect(0,0,w,h);
			g.setColor(Color.black);
			g.setFont(new Font("Ubuntu", Font.PLAIN, RowSize/2));
			g.drawString("Winner", 300, 300);
		}
		else{
			FontMetrics fm = g.getFontMetrics();
			g.setColor(Color.white);
			g.fillRect(0,0,w,h);
			g.setColor(Color.black);
			g.setFont(new Font("Ubuntu", Font.PLAIN, RowSize/2));
			for(int r=0; r<RowAmount; r++){
				for(int c=0; c<ColAmount; c++){
					int n = fm.stringWidth(board[r][c]);
					g.drawString(board[r][c], (c*ColSize)+(ColSize/2-n*2), 37+(r*RowSize)+(RowSize/2+15));
					g.drawRect(c*ColSize,(r*RowSize),ColSize,RowSize);
				}
			}

		}
    }

    public DrawLine(){
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.white);
        setTitle("Sorting Game");
        setSize(w, h);
		SetBoard();
		LoadGame();
        repaint();
        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
				 if(winner){
					System.out.println("winner");
					repaint();
				 }else{
					swapChar(e.getX(), e.getY());
					SaveGame();
					checkWinner();
					System.out.println(winner);
					repaint();
				 }

            }

        });

        setVisible(true);
        //randomAlpha();
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


    public boolean checkWinner(){
        for(int r=0; r<3; r++){
            for(int c=0; c<4; c++){
                if(!board[r][c].equals(boardwinner[r][c])){
					System.out.printf("%s %s\n", board[r][c], boardwinner[r][c]);
					winner = false;
                    return false;
                }
            }
        }
		winner = true;
        return true;
    }

    public void winSceen(){
        System.out.println("Win");
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
		int row = mouseY/RowSize;
		int col = mouseX/ColSize;
		if(((row-1==blank_position[0]||row+1==blank_position[0]) && col==blank_position[1]) || ((col-1==blank_position[1]||col+1==blank_position[1]) &&row==blank_position[0])){
			board[blank_position[0]][blank_position[1]] = board[row][col];
			board[row][col] = " ";
			blank_position[0] = row;
			blank_position[1] = col;
		}
		//for(int r=0; r<RowSize; r++){
		//for(int c=0; c<ColSize; c++){
		//if(mouseX>block_x && mouseX<block_x+ColSize && mouseY>block_y && mouseY<block_y+RowSize){
		//if(((r-1==blank_position[0]||r+1==blank_position[0]) && c==blank_position[1]) || ((c-1==blank_position[1]||c+1==blank_position[1]) &&r==blank_position[0])){
		//board[blank_position[0]][blank_position[1]] = board[r][c];
		//board[r][c] = " ";
		//blank_position[0] = r;
		//blank_position[1] = c;
		//}
		//}
		//block_x += ColSize;
		//}
		//block_x =0;
		//block_y += RowSize;
		//}
    }

}
