

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.function.Predicate;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.net.httpserver.Filter;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.Cell;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import logica.Cella;
import logica.Cella2;



public class SlidingBlocks extends JPanel{ //jpanel disegna la griglia in un pannello dedicato

	private int size;

	//Numero di Tile usati
	private int nbTiles;

	//Dimensione della Griglia UI
	private int dimension ;

	//colore di foreground
	private static final Color FOREGROUND_COLOR = new Color(239,83,80); //colore arbitrario

	// Random Object to shuffle tiles
	private static final Random RANDOM = new Random();

	//Salviamo i tiles in un array 1D di interi
	private int[] tiles;

	//Dimensione dei tile nella UI
	private int tileSize;

	//Posizione del tile bianco
	private int blankPos;

	//Margine per la griglia sul frame
	private int margin;

	//Dimensione Griglia UI
	private int gridSize;

	private boolean gameOver; //Vero se il gioco finisce, falso altrimenti


	public String encoding = " "; //qua va impostata la lettura del file : Sliding-Blocks-Rules
	public String instance= " ";	//qua vanno inserite le celle ( cioè i fatti ) dopo che è stato fatto lo shuffle, NB non dimenticare di aggiornare questo file ogni volta che si esegue un movimento delle celle. Vedi funzione setInstance
	private static String encodingResource="C:\\Users\\ricky\\git\\SlidingBlocksAI\\SlidingBlocksAI\\encodings\\Sliding-blocks-Rules";
	private static String instanceResource="C:\\Users\\ricky\\git\\SlidingBlocksAI\\SlidingBlocksAI\\encodings\\Sliding-blocks-instance";
	private static String nextInstanceResource="C:\\Users\\ricky\\git\\SlidingBlocksAI\\SlidingBlocksAI\\encodings\\Sliding-blocks-Next-Step";

	private static Handler handler;  

	private int coordinataXMouse;
	private int coordinataYMouse;

	ArrayList<Cella> listaCelle;

	public SlidingBlocks (int size, int dim, int mar) {
		this.size = size;
		dimension = dim;
		margin= mar;

		//inizializzazione tiles
		nbTiles = size*size -1 ; //perchÃ¨ non viene contato il tile bianco
		tiles = new int[size*size];

		//calcoliamo la grandezza della griglia e del tile
		gridSize = (dim-2*margin);
		tileSize = gridSize/ size;

	//Finestra di scelta modalità
		if(JOptionPane.showConfirmDialog(null,"Vuoi avviare la versione automatizzata?", "Scelta Modalità", 1) == 0) {
			setGameFrame();
			gameOver = true;
			addMouse();
			newGame();
		}
		else {
			
		}
	}




	//Aggiungi un moise che al click chiama risolviazione e ricolora il pannello
	private void addMouse() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mousePos) {
				//listener per interagire con il mouse sulla griglia

				if(gameOver) {

					newGame();
				}else {
					coordinataXMouse= mousePos.getX();
					coordinataYMouse= mousePos.getY();
					resolveAction();
				}
				//Ricolorazione panel
				repaint();

				initDlv();
				setInstance();  
				//				muoviCellaDlv();


				//System.out.println(instance);

			}
		});
	}


	//metodo contenente la logica del gioco
	private void resolveAction() {
		int coordinataX= coordinataXMouse - margin;
		int coordinataY= coordinataYMouse -margin;
		//Clic sulla griglia da scartare
		if(coordinataX<0 || coordinataX>gridSize || coordinataY<0 || coordinataY> gridSize)
			return;

		//Ottieni posizione sulla griglia
		int colonnaBloccoGriglia = coordinataX/ tileSize;
		int rigaBloccoGriglia = coordinataY/ tileSize; 
		// Ottieni posizione della cella bianca
		int colonnaCellaBianca = blankPos% size;
		int rigaCellaBianca = blankPos /size;
		//Conversione in coordinate 1D
		int clickPos = rigaBloccoGriglia*size+colonnaBloccoGriglia;
		System.out.println("Click pos: " +clickPos);
		int dir =0;

		//Ricerca della direzione per il movimento multiplo dei tile uno alla volta
		if(colonnaBloccoGriglia ==colonnaCellaBianca && Math.abs(rigaBloccoGriglia-rigaCellaBianca)>0) {
			dir = (rigaBloccoGriglia-rigaCellaBianca)>0 ? size: -size;
			System.out.println("DIR: " +dir);
		}	
		else if(rigaBloccoGriglia == rigaCellaBianca && Math.abs(colonnaBloccoGriglia-colonnaCellaBianca)>0) {
			dir = (colonnaBloccoGriglia-colonnaCellaBianca) >0 ? 1: -1;
			System.out.println("DIR: " +dir);
		}

		if(dir!=0) {
			//Muovi i tiles in quella direzione
			//Se dir è negativo va verso le posizioni negative, in caso contrario positive (immagina asse x e y)
			//Dir indica di quanti blocchi indietro o avanti deve andare il blocco bianco.
			do {
				int newBlankPos = blankPos+dir;
				System.out.println("OldBlank: " +blankPos);
				System.out.println("NewBlank: " +newBlankPos);
				//Scambia il tile vuoto con la casella numerata
				tiles[blankPos] = tiles[newBlankPos];
				blankPos= newBlankPos;
			}while(blankPos!=clickPos);
			tiles[blankPos] =0;
		}
		//Check per controllare che il gioco sia finito
		gameOver= isSolved();

		//debug caselle decommenta solo se vuoi vedere l'ordine della lista
		//				for(int i=0;i<tiles.length;i++) {
		//					System.out.println("Tile "+i+" : "+ tiles[i]);
		//				}

	}

	private void newGame() {
		do {
			reset(); //resetta lo stato iniziale
			shuffle(); //shuffle

		}
		while(!isSolvable()); //Fa fino a quando la griglia Ã¨ risolvibile
		gameOver=false;
		//		
		initDlv();

		setInstance();
		//		muoviCellaDlv();
	}

	private void reset() {
		for(int i= 0; i<tiles.length;i++) {
			tiles[i] = (i+1) % tiles.length;

		}
		//mettiamo la cella bianca ultima
		blankPos = tiles.length -1;
	}

	private void shuffle() {
		// non includiamo la cella bianca nello shuiffle, viene lasciata nella posizione di solve
		int n = nbTiles;

		while(n>1) {
			int r = RANDOM.nextInt(n--);
			int tmp = tiles[r];


			tiles[r] = tiles[n];


			tiles[n] = tmp;


		}
	}

	public void initDlv() {

		// register the class for reflection
		try {
			ASPMapper.getInstance().registerClass(Cella.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//	private void muoviCellaDlv() {
	//		handler.removeAll();
	//		
	//		InputProgram  facts = new ASPInputProgram();
	//		
	//		for(Cella cell: listaCelle) {
	//			try {
	//				facts.addObjectInput(cell);
	//			} catch (Exception e) {
	//				e.printStackTrace();
	//			}
	//		}
	//		handler.addProgram(facts);
	//		facts.toString();
	//		
	//		InputProgram encode = new ASPInputProgram();
	//		encode.addFilesPath(encodingResource);
	//		encode.addFilesPath(instanceResource);
	//		handler.addProgram(encode);
	//		
	//		Output o =  handler.startSync();
	//		AnswerSets answers = (AnswerSets) o;
	//		// register the class for reflection	
	//		
	//		for(AnswerSet a : answers.getAnswersets()){
	//			// System.out.println("AS n.: " + ++n + ": " + a);
	//			try {
	//				for(Object obj:a.getAtoms()){
	//					if(obj instanceof Cella)  {
	//						Cella cella = (Cella) obj;
	//						System.out.print(cella + " ");
	//					}
	//				}
	//				System.out.println();
	//			} catch (Exception e) {
	//				e.printStackTrace();
	//			} 			
	//		}
	//
	//	}

	private void setInstance() {
		Handler handler = new DesktopHandler(new DLVDesktopService("C:\\Users\\ricky\\git\\SlidingBlocksAI\\SlidingBlocksAI\\lib\\dlv.mingw.exe"));
		int s=0;
		instance="";
		for(int i= 1; i<=size;i++) {
			for(int j= 1; j<=size;j++) {
				if(tiles[s]==0) instance=(instance + new String("empty("+i+","+j+").\n"));
				else instance=(instance + new String("cell("+i+","+j+","+tiles[s]+","+ s +").\n"));
				s++;
			}
		}
		Path path = Paths.get("C:\\Users\\ricky\\git\\SlidingBlocksAI\\SlidingBlocksAI\\encodings\\Sliding-blocks-instance");

		try {
			Files.write(path, instance.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputProgram  program = new ASPInputProgram();
		//			program.addProgram(encoding);
		//			program.addProgram(instance);
		program.addFilesPath(encodingResource);
		program.addFilesPath(instanceResource);
		handler.addProgram(program);

		// register the class for reflection
		try {
			ASPMapper.getInstance().registerClass(Cella2.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Output o =  handler.startSync();

		AnswerSets answers = (AnswerSets) o;

		int n=0;

		for(AnswerSet a:answers.getAnswersets()){
			//if(a.toString()=="canMove")
			//System.out.println("AS n.: " + ++n + ": " + a);
			String s2 = a.toString();
			String nextInstance="";
			StringTokenizer st = new StringTokenizer(s2);
			while (st.hasMoreTokens()) { //per ognuno di questi answerset il programma deve essere in grado di orlare il file next-step con l'istanza aggiornata della mossa, ad esempio se canMoveDown sposti giù quella casella e 
				String temp=st.nextToken();//ne calcoli le rispettive conseguenze. Questo va fatto per ognuno di questi fatti. Il tutto deve essere ricorsivo e deve continuare finché non trova la condizione di stop.

				if(temp.contains("canMoveDown")||temp.contains("canMoveRight")||temp.toString().contains("canMoveUP")||temp.toString().contains("canMoveLeft")||temp.toString().contains("inRightPlace")) {
					temp = temp.replace(temp.substring(temp.length()-1), "");
					nextInstance=(nextInstance +temp+".\n");
				}
				Path path2 = Paths.get("C:\\Users\\ricky\\git\\SlidingBlocksAI\\SlidingBlocksAI\\encodings\\Sliding-blocks-Next-Step");

				try {
					Files.write(path2, nextInstance.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}






				/*if(temp.contains("canMoveDown")) { //Il metodo risolutivo deve essere tipo un albero, O(N)^N se ce lo chiedono. 



//					}else if(temp.contains("canMoveRight")){
//						System.out.println("Destra: " + temp );
//	
//					}else if(temp.toString().contains("canMoveUP")){
//						System.out.println("Sopra: " + temp );
//	
//					}else if(temp.toString().contains("canMoveLeft")){
//						System.out.println("Sinistra: " + temp );

					}*/
			}

		}
	}




	// Solo la metÃ  delle permutazioni del puzzle sono risolvibili
	// Fino a quando un tile Ã¨ preceduto da un tile con un valore piÃ¹ alto viene contato
	// come un inversion. Nel nostro case con il tile bianco nella solved position,
	// il numero di inversions must be even for the puzzle to be solvable

	private boolean isSolvable() {
		int countInversions=0;

		for(int i =0;i<nbTiles;i++) {
			for(int j =0; j<i;j++) {
				if(tiles[j] >tiles[i])
					countInversions++;
			}
		}
		return countInversions % 2==0;

	}

	private boolean isSolved() {
		if(tiles[tiles.length -1] !=0) // se il tile bianco non Ã¨ nella solved position ==> non solved
			return false;

		for(int i = nbTiles-1;i>=0;i--) {
			if(tiles[i]!= i+1)
				return false;
		}
		return true;
	}




	//PARTE GRAFICA
	//setta proprietà grafiche del gioco
	public void setGameFrame() {
		setPreferredSize(new Dimension(dimension, dimension +margin));
		setBackground(Color.WHITE);
		setForeground(FOREGROUND_COLOR);
		setFont(new Font("TimesRoman" ,Font.BOLD, 60));
	}

	private void drawGrid(Graphics2D g) {
		for(int i =0;i<tiles.length;i++) {

			// Convertiamo le coordinate 1D in coordinate 2D dando la size dell'array 2D
			int r = i/size;
			int c = i% size;

			//convertiamo in coord della UI
			int x = margin+c*tileSize;
			int y = margin+r*tileSize;

			//Controlla casi speciali per il tile BIANCO
			if(tiles[i] ==0) {
				if(gameOver) {
					g.setColor(FOREGROUND_COLOR);
					drawCenteredString(g, "V", x,y);
				}
				continue;
			}

			//per gli altri tile
			g.setColor(getForeground());
			g.fillRoundRect(x,y,tileSize, tileSize,25,25);
			g.setColor(Color.BLACK);
			g.drawRoundRect(x, y, tileSize, tileSize, 25, 25);
			g.setColor(Color.WHITE);

			drawCenteredString(g, String.valueOf(tiles[i]) , x , y);


		}

	}

	private void drawStartMessage(Graphics2D g) {
		if(gameOver) {
			g.setFont(getFont().deriveFont(Font.BOLD, 18));
			g.setColor(FOREGROUND_COLOR);
			String s = "Clicca per startare un nuovo gioco";
			g.drawString(s, (getWidth()-g.getFontMetrics().stringWidth(s))/2, 
					getHeight()- margin	);
		}
	}

	private void drawCenteredString(Graphics2D g, String s, int x, int y) {
		// centra la stringa s per il tile dato(x,y)

		FontMetrics fm= g.getFontMetrics();
		int asc = fm.getAscent();
		int desc = fm.getDescent();

		g.drawString(s, x+(tileSize - fm.stringWidth(s))/2,
				y+(asc+(tileSize-(asc+desc)) /2));

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawGrid(g2D);
		drawStartMessage(g2D);

	}

	public void run(int D,SlidingBlocks s) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame= new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Sliding Blocks");
			frame.setResizable(false);
			frame.add(s, BorderLayout.CENTER);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

		});

	}

}
