package myWork;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class Frame extends JPanel
		implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	ControlHandler ch;
	JFrame window;
	
	String algorithms;
	Astar astar;
	BFS bfs;
	DFS dfs;
	Dijkstra dijkstra;
	Greedy greedy;
	
	int size;
	char currentKey = (char) 0;
	boolean visualizationCheck;
	Node startNode, endNode;
	Timer timer = new Timer(1, this);
	
	public static void main(String[] args) {
		new Frame();
	}

	public Frame() {
		ch = new ControlHandler(this);
		size = 25;
		visualizationCheck = true; 
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		//Set Up algorithms
		
		bfs = new BFS(this, size);
		dfs = new DFS(this, size);
		dijkstra = new Dijkstra(this, size);
		greedy =  new Greedy(this, size);
		astar = new Astar(this, size);

		// Set up window
		window = new JFrame();
		window.setContentPane(this);
		window.setTitle("FYP");
		window.getContentPane().setPreferredSize(new Dimension(999, 499));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		// Add all controls
		ch.addAll();
		
		this.revalidate();
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//getting values from checkboxes and coboxes
		visualizationCheck = ch.getC("visualizationCheck").isSelected();
		algorithms = String.valueOf(ch.getCo("algorithms").getSelectedItem());
		
		if(algorithms.equals("Astar")) {
			
			// Draws all open Nodes (path finding nodes)
			for (int i = 0; i < astar.getOpen().size(); i++) {
				Node current = astar.getOpen().get(i);
				g.setColor(Color.green);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
			}
			
			// Draws all closed nodes
			for (int i = 0; i < astar.getClosed().size(); i++) {
				Node current = astar.getClosed().get(i);

				g.setColor(Color.red);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
			}

			// Draw all final path nodes
			for (int i = 0; i < astar.getPath().size(); i++) {
				Node current = astar.getPath().get(i);

				g.setColor(Color.blue);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);

			}
			// Setting numbers in pathfinding lists
			ch.getL("computedC").setText(String.valueOf(astar.getClosed().size()));
			ch.getL("lengthC").setText(String.valueOf(astar.getPath().size()));
			
			// if pathfinding is complete 
			if(astar.isComplete()) {
				ch.getB("run").setText("clear");
				if(!visualizationCheck) {
					ch.getL("timeC").setText(astar.getRunTime() + " ms");
				}
			}
		}else if(algorithms.equals("BFS")) {
			// Draws all open Nodes (path finding nodes)
			for (int i = 0; i < bfs.getOpen().size(); i++) {
				Node current = bfs.getOpen().get(i);
				g.setColor(Color.green);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
			}
			
			// Draws all closed nodes
			for (int i = 0; i < bfs.getClosed().size(); i++) {
				Node current = bfs.getClosed().get(i);
		
				g.setColor(Color.red);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
			}
		
			// Draw all final path nodes
			for (int i = 0; i < bfs.getPath().size(); i++) {
				Node current = bfs.getPath().get(i);
		
				g.setColor(Color.blue);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
		
			}
			// Setting numbers in pathfinding lists
			ch.getL("computedC").setText(String.valueOf(bfs.getClosed().size()));
			ch.getL("lengthC").setText(String.valueOf(bfs.getPath().size()));
			
			// if pathfinding is complete 
			if(bfs.isComplete()) {
				ch.getB("run").setText("clear");
				if(!visualizationCheck) {
					ch.getL("timeC").setText(bfs.getRunTime() + " ms");
				}
			}
			
		}else if(algorithms.equals("DFS")) {
			// Draws all open Nodes (path finding nodes)
			for (int i = 0; i < dfs.getOpen().size(); i++) {
				Node current = dfs.getOpen().get(i);
				g.setColor(Color.green);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
			}
			
			// Draws all closed nodes
			for (int i = 0; i < dfs.getClosed().size(); i++) {
				Node current = dfs.getClosed().get(i);
	
				g.setColor(Color.red);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
			}
	
			// Draw all final path nodes
			for (int i = 0; i < dfs.getPath().size(); i++) {
				Node current = dfs.getPath().get(i);
	
				g.setColor(Color.blue);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
	
			}
			// Setting numbers in pathfinding lists
			ch.getL("computedC").setText(String.valueOf(dfs.getClosed().size()));
			ch.getL("lengthC").setText(String.valueOf(dfs.getPath().size()));
			
			// if pathfinding is complete 
			if(dfs.isComplete()) {
				ch.getB("run").setText("clear");
				if(!visualizationCheck) {
					ch.getL("timeC").setText(dfs.getRunTime() + " ms");
				}
		}
			
		}else if(algorithms.equals("Dijkstra")) {
			// Draws all open Nodes (path finding nodes)
			for (int i = 0; i < dijkstra.getOpen().size(); i++) {
				Node current = dijkstra.getOpen().get(i);
				g.setColor(Color.green);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
			}
			
			// Draws all closed nodes
			for (int i = 0; i < dijkstra.getClosed().size(); i++) {
				Node current = dijkstra.getClosed().get(i);

				g.setColor(Color.red);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
			}

			// Draw all final path nodes
			for (int i = 0; i < dijkstra.getPath().size(); i++) {
				Node current = dijkstra.getPath().get(i);

				g.setColor(Color.blue);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);

			}
			// Setting numbers in pathfinding lists
			ch.getL("computedC").setText(String.valueOf(dijkstra.getClosed().size()));
			ch.getL("lengthC").setText(String.valueOf(dijkstra.getPath().size()));
			
			// if pathfinding is complete 
			if(dijkstra.isComplete()) {
				ch.getB("run").setText("clear");
				if(!visualizationCheck) {
					ch.getL("timeC").setText(dijkstra.getRunTime() + " ms");
				}
			}			
		}else if(algorithms.equals("Greedy")) {
			// Draws all open Nodes (path finding nodes)
			for (int i = 0; i < greedy.getOpen().size(); i++) {
				Node current = greedy.getOpen().get(i);
				g.setColor(Color.green);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
			}
			
			// Draws all closed nodes
			for (int i = 0; i < greedy.getClosed().size(); i++) {
				Node current = greedy.getClosed().get(i);

				g.setColor(Color.red);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);
			}

			// Draw all final path nodes
			for (int i = 0; i < greedy.getPath().size(); i++) {
				Node current = greedy.getPath().get(i);

				g.setColor(Color.blue);
				g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);

			}
			// Setting numbers in pathfinding lists
			ch.getL("computedC").setText(String.valueOf(greedy.getClosed().size()));
			ch.getL("lengthC").setText(String.valueOf(greedy.getPath().size()));
			
			// if pathfinding is complete 
			if(greedy.isComplete()) {
				ch.getB("run").setText("clear");
				if(!visualizationCheck) {
					ch.getL("timeC").setText(greedy.getRunTime() + " ms");
				}
			}
		}
		
		// Draws all borders
		g.setColor(Color.black);
		for (int i = 0; i < astar.getBorders().size(); i++) {
			g.fillRect(astar.getBorders().get(i).getX() + 1, astar.getBorders().get(i).getY() + 1,
					size - 1, size - 1);
		}
		// Draws grid
		g.setColor(Color.lightGray);
		for (int j = 0; j < this.getHeight(); j += size) {
			for (int i = 0; i < this.getWidth(); i += size) {
				g.drawRect(i, j, size, size);
			}
			
		}
		
		// Draws start of path
		if (startNode != null) {
			g.setColor(Color.orange);
			g.fillRect(startNode.getX() + 1, startNode.getY() + 1, size - 1, size - 1);
		}
		// Draws end of path
		if (endNode != null) {
			g.setColor(Color.magenta);
			g.fillRect(endNode.getX() + 1, endNode.getY() + 1, size - 1, size - 1);
		}
		
		// Drawing control panel rectangle
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(10, getHeight()-96, 250, 90);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(300, getHeight()-96, 250, 90);

		// Setting algorithms text
		ch.getL("algorithmsT").setText("Algorithms: ");
		
		// Position all controls
		ch.position();
				
		// if visualization is selected show speed but no time
		if(ch.getC("visualizationCheck").isSelected()) {
			ch.getL("speedC").setText(Integer.toString(ch.getS("speed").getValue()));
			ch.getL("timeC").setText("N/A");
		}
		//if visualization is not selected show time no speed
		else {
			ch.getL("speedC").setText("N/A");
		}
		
		
	}
	

	public void MapCalculations(MouseEvent e) {
		// If left mouse button is clicked
		if (SwingUtilities.isLeftMouseButton(e)) {
			// If 's' is pressed create start node
			if (currentKey == 's' ) {
				int xRollover = e.getX() % size;
				int yRollover = e.getY() % size;

				if (startNode == null) {
					startNode = new Node(e.getX() - xRollover, e.getY() - yRollover);
				} else {
					startNode.setXY(e.getX() - xRollover, e.getY() - yRollover);
				}
				repaint();
			} 
			// If 'e' is pressed create end node
			else if (currentKey == 'e') {
				int xRollover = e.getX() % size;
				int yRollover = e.getY() % size;

				if (endNode == null) {
					endNode = new Node(e.getX() - xRollover, e.getY() - yRollover);
				} else {
					endNode.setXY(e.getX() - xRollover, e.getY() - yRollover);
				}
				repaint();
			} 
			// Otherwise, create a wall
			else {
				int xBorder = e.getX() - (e.getX() % size);
				int yBorder = e.getY() - (e.getY() % size);

				Node newBorder = new Node(xBorder, yBorder);
				astar.addBorder(newBorder);
				bfs.addBorder(newBorder);
				dfs.addBorder(newBorder);
				dijkstra.addBorder(newBorder);
				greedy.addBorder(newBorder);
				repaint();
			}
		} 
		// If right mouse button is clicked
		else if (SwingUtilities.isRightMouseButton(e)) {
			int mouseBoxX = e.getX() - (e.getX() % size);
			int mouseBoxY = e.getY() - (e.getY() % size);

			// If 's' is pressed remove start node
			if (currentKey == 's') {
				if (startNode != null && mouseBoxX == startNode.getX() && startNode.getY() == mouseBoxY) {
					startNode = null;
					repaint();
				}
			} 
			// If 'e' is pressed remove end node
			else if (currentKey == 'e') {
				if (endNode != null && mouseBoxX == endNode.getX() && endNode.getY() == mouseBoxY) {
					endNode = null;
					repaint();
				}
			} 
			// Otherwise, remove wall
			else {
				if (astar.searchBorder(mouseBoxX, mouseBoxY) != -1) {
					astar.removeBorder(astar.searchBorder(mouseBoxX, mouseBoxY));                 
				}
				if (bfs.searchBorder(mouseBoxX, mouseBoxY) != -1) {
					bfs.removeBorder(bfs.searchBorder(mouseBoxX, mouseBoxY));                 
				}
				if (dfs.searchBorder(mouseBoxX, mouseBoxY) != -1) {
					dfs.removeBorder(dfs.searchBorder(mouseBoxX, mouseBoxY));                 
				}
				if (dijkstra.searchBorder(mouseBoxX, mouseBoxY) != -1) {
					dijkstra.removeBorder(dijkstra.searchBorder(mouseBoxX, mouseBoxY));                 
				}
				if (greedy.searchBorder(mouseBoxX, mouseBoxY) != -1) {
					greedy.removeBorder(greedy.searchBorder(mouseBoxX, mouseBoxY));                 
				}
				repaint();
			}
		}
	}


	
	@Override
	public void mouseClicked(MouseEvent e) {
		MapCalculations(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		MapCalculations(e);
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		currentKey = key;
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		currentKey = (char) 0;
	}
	
	//for visualization 
	@Override
	public void actionPerformed(ActionEvent e) {
		if(algorithms.equals("Astar")) {
			if(astar.isRunning() && visualizationCheck) {
				astar.findPath(astar.getPar());
			}
			
			if(e.getActionCommand() != null) {
				if(e.getActionCommand().equals("run") && !astar.isRunning()) {
					ch.getB("run").setText("clear");
					start();
				}
				else if(e.getActionCommand().equals("clear") ) {
					ch.getB("run").setText("run");
					astar.reset();
				}
			}
			repaint();
		}else if(algorithms.equals("BFS")) {
			if(bfs.isRunning() && visualizationCheck) {
				bfs.findPath();
			}
			
			if(e.getActionCommand() != null) {
				if(e.getActionCommand().equals("run") && !bfs.isRunning()) {
					ch.getB("run").setText("clear");
					start();
				}
				else if(e.getActionCommand().equals("clear") ) {
					ch.getB("run").setText("run");
					bfs.reset();
				}
			}
			repaint();
			
		}else if(algorithms.equals("DFS")) {
			if(dfs.isRunning() && visualizationCheck) {
				dfs.findPath(dfs.getPar());
			}
			
			if(e.getActionCommand() != null) {
				if(e.getActionCommand().equals("run") && !dfs.isRunning()) {
					ch.getB("run").setText("clear");
					start();
				}
				else if(e.getActionCommand().equals("clear") ) {
					ch.getB("run").setText("run");
					dfs.reset();
				}
			}
			repaint();
			
		}else if(algorithms.equals("Dijkstra")) {
			
				if(dijkstra.isRunning() && visualizationCheck) {
					dijkstra.findPath(dijkstra.getPar());
				}
				
				if(e.getActionCommand() != null) {
					if(e.getActionCommand().equals("run") && !dijkstra.isRunning()) {
						ch.getB("run").setText("clear");
						start();
					}
					else if(e.getActionCommand().equals("clear") ) {
						ch.getB("run").setText("run");
						dijkstra.reset();
					}
				}
				repaint();
				
		}else if(algorithms.equals("Greedy")) {

				if(greedy.isRunning() && visualizationCheck) {
					greedy.findPath(greedy.getPar());
				}
				
				if(e.getActionCommand() != null) {
					if(e.getActionCommand().equals("run") && !greedy.isRunning()) {
						ch.getB("run").setText("clear");
						start();
					}
					else if(e.getActionCommand().equals("clear") ) {
						ch.getB("run").setText("run");
						greedy.reset();
					}
				}
				repaint();
			}
			
	}
	
	public void start() {
		if(algorithms.equals("Astar")) {
			if(startNode != null && endNode != null) {
				if(!visualizationCheck) {
					astar.start(startNode, endNode);
				}else {
					astar.setup(startNode, endNode);
					setSpeed();
				}
			}else {
				System.out.println("ERROR: Needs start and end points to run");
			}
		}else if(algorithms.equals("BFS")) {
			if(startNode != null && endNode != null) {
				if(!visualizationCheck) {
					bfs.start(startNode, endNode);
				}else {
					bfs.setup(startNode, endNode);
					setSpeed();
				}
			}else {
				System.out.println("ERROR: Needs start and end points to run");
			}
			
		}else if(algorithms.equals("DFS")) {
			if(startNode != null && endNode != null) {
				if(!visualizationCheck) {
					dfs.start(startNode, endNode);
				}else {
					dfs.setup(startNode, endNode);
					setSpeed();
				}
			}else {
				System.out.println("ERROR: Needs start and end points to run");
			}
			
		}else if(algorithms.equals("Dijkstra")) {
			if(startNode != null && endNode != null) {
				if(!visualizationCheck) {
					dijkstra.start(startNode, endNode);
				}else {
					dijkstra.setup(startNode, endNode);
					setSpeed();
				}
			}else {
				System.out.println("ERROR: Needs start and end points to run");
			}
			
		}else if(algorithms.equals("Greedy")) {
			if(startNode != null && endNode != null) {
				if(!visualizationCheck) {
					greedy.start(startNode, endNode);
				}else {
					greedy.setup(startNode, endNode);
					setSpeed();
				}
			}else {
				System.out.println("ERROR: Needs start and end points to run");
			}
			
		}	
	}
	
	// Calculates delay with two exponential functions
	public void setSpeed() {
		int delay = 0;
		int value = ch.getS("speed").getValue();
		double a1 = (5000.0000 / (Math.pow(25.0000/5000, 1/49)));
		double a2 = 625.0000;
		
		if(value == 0) {
			timer.stop();
		}
		else if(value >= 1 && value < 50) {
			if(!timer.isRunning()) {
				timer.start();
			}
			// Exponential function. value(1) == delay(5000). value (50) == delay(25)
			delay = (int)(a1 * (Math.pow(25/5000.0000, value / 49.0000)));
			timer.start();
		}
		else if(value >= 50 && value <= 100) {
			if(!timer.isRunning()) {
				timer.start();
			}
			// Exponential function. value (50) == delay(25). value(100) == delay(1).
			delay = (int)(a2 * (Math.pow(1/25.0000, value/50.0000)));
			timer.start();
		}
		timer.setDelay(delay);
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean visualization() {
		return visualizationCheck;
	}
}

