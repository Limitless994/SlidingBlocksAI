package vecchio;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Main extends JPanel {
	private static final int ROW_COUNT = 5;
	private static final int W = 60;
	private static final int H = W;
	private static final Dimension PREF_SIZE = new Dimension(W, H);
	protected static final Color SELECTION_COLOR = Color.red;
	private JPanel selectedPanel = null;
	private Color originalColor = null;



	public Main() {


		setLayout(new GridLayout(ROW_COUNT, ROW_COUNT, 1, 1));
		setBackground(Color.black);
		for (int i = 0; i < ROW_COUNT * ROW_COUNT; i++) {
			JPanel panel = new JPanel();
//			String name = String.format("[%d, %d]", 
//					i / ROW_COUNT, i % ROW_COUNT);
//			panel.setName(name);
			if (i == 0) {
				originalColor = panel.getBackground();
			}
			panel.setPreferredSize(PREF_SIZE);
			
			
			
			//			BufferedImage myPicture;
			//			try {
			//				myPicture = ImageIO.read(new File("path-to-file"));
			//			} catch (IOException e1) {
			//				// TODO Auto-generated catch block
			//				e1.printStackTrace();
			//			}
			//			JLabel picLabel = new JLabel(new ImageIcon("C:/Users/ricky/git/SlidingBlocksAI/SlidingBlocksAI/blocchi/bloccoPiccolo.PNG"));
			//			selectedPanel.add(picLabel);
			add(panel);
		}
	
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JPanel panel = (JPanel) getComponentAt(e.getPoint());
				if (panel == null || panel == Main.this) {
					return;
				}
				if (selectedPanel != null) {
					selectedPanel.setBackground(originalColor);
					selectedPanel.removeAll();
					selectedPanel.revalidate();
					selectedPanel.repaint();
				}
				int xMouse= e.getX()/60;
				int yMouse = e.getY()/60;	

				selectedPanel = panel;
				selectedPanel.setBackground(SELECTION_COLOR);
				selectedPanel.add(new JLabel(selectedPanel.getName()));
				selectedPanel.revalidate();
				selectedPanel.repaint();
			}
		});
	}

	private static void createAndShowGui() {
		JFrame frame = new JFrame("TestComponentAt");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new Main());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}
}