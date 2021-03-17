package myWork;

import java.util.ArrayList;



public class Dijkstra {
	int size;
	private Frame frame;
	private Node startNode, endNode, par;
	private long runTime;
	private boolean running, complete, noPath;
	private ArrayList<Node> borders, open, closed, path;
	
	public Dijkstra(Frame frame, int size) {
		this.frame = frame;
		this.size = size;
		
		running = false;
		complete = false;	
		
		borders = new ArrayList<Node>();
		open = new ArrayList<Node>();
		closed = new ArrayList<Node>();
		path = new ArrayList<Node>();
		
	}
	
	public void start(Node s, Node e) {
		running = true;
		startNode = s;
		endNode = e;
		
		addClosed(startNode);
		
		long startTime = System.currentTimeMillis();
		
		findPath(startNode);
		
		complete = true;
		long endTime = System.currentTimeMillis();
		runTime = endTime - startTime; 
		System.out.println("Complete" + runTime);
		
	}
	
	public void setup(Node s, Node e) {
		running = true;
		startNode = s;
		startNode.setG(0);
		par = startNode;
		endNode = e;

		addClosed(startNode);
	}

	public void findPath(Node parent) {
		Node openNode = null;
		
		//add four neighbors of the parent node to the openNode
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if((i == 0 && j == 0) || (i == 0 && j == 2) || 
						(i == 1 && j == 1) || (i == 2 && j == 0) ||
						(i == 2 && j == 2)) {
						continue;
					}
					int possibleX = (parent.getX() - size) + (size * i);
					int possibleY = (parent.getY() - size) + (size * j);
					//give values for all the open node
					//add all of them in the open arrayList
					calculateNodeValues(possibleX, possibleY, openNode, parent);
				
			}
		}
		
		//set a new parentNode with lowest fCost in open nodes
		parent = lowestFCost();
		
		//for noPath
		if (parent == null) {
			System.out.println("NO PATH");
			noPath = true;
			running = false;
			complete = false;
			frame.repaint();
			return;
		}
		
		//for have path
		if (Node.isEqual(parent, endNode)){
			endNode.setParent(parent.getParent());
			connectPath();
			running = false;
			complete = true;
			frame.repaint();
			return;
		}
		
		// Remove parent node from open list
		removeOpen(parent);
		// Add parent node to closed list
		addClosed(parent);
		
		if(!frame.visualization()) {
			findPath(parent);
		}else {
			par = parent;
		}
	}
	
	public void calculateNodeValues(int x, int y, Node openNode, Node parent) {
		//Don't calculate the Nodes which are outside of the border
		if(x < 0 || x >= frame.getWidth() || 
		   y < 0 || y >= frame.getHeight()) {
			return;
		}
		
		//Don't calculate the Node which have already been 
		//a open node, a border or closed node
		if(searchBorder(x, y) != -1 || 
		   searchClosed(x, y) != -1 || 
		   searchOpen(x, y) != -1) {
			return;
		}
		
		//create an open node with the available x and y
		openNode = new Node(x,y);
		openNode.setParent(parent);
		
		//calculating gCost
		int gCost = parent.getG() + size;
		openNode.setG(gCost);
		
		//calculating hCost
		int hCost = 0;
		
		//calculating fCost
		int fCost = gCost + hCost;
		openNode.setF(fCost);
		
		addOpen(openNode);
		
	}
	
	public void connectPath() {
		if(path.size() == 0) {
			//set up parent node 
			Node parentNode =  endNode.getParent();
			
			//keep adding the parent node of end node to the path
			while (!Node.isEqual(parentNode, startNode)) {
				path.add(parentNode);
				
				for (int i = 0; i < closed.size(); i++) {
					Node current = closed.get(i);
					//find the closed nodes which can be the path
					//keep finding backwards
					if(Node.isEqual(current, parentNode)) {
						parentNode = current.getParent();
						break;
					}
				}		
			}
			
			//reverse the path list
			reverse(path);
		}
	}
	


	private void reverse(ArrayList<Node> list) {
		int j = list.size() - 1;

		for (int i = 0; i < j; i++) {
			Node temp = list.get(i);
			list.remove(i);
			list.add(i, list.get(j - 1));
			list.remove(j);
			list.add(j, temp);
			j--;
		}
		
	}
	
	//return -1  means they are not in the list
	//otherwise tell you the location
	private int searchClosed(int x, int y) {
		int location = -1;
		
		for (int i =0; i < closed.size(); i++) {
			if(closed.get(i).getX() == x &&
			   closed.get(i).getY() == y) {
				location = i;
			}
		}
		return location;
	}
	
	
	public int searchBorder(int x, int y) {
		int location = -1;
		
		for (int i =0; i < borders.size(); i++) {
			if(borders.get(i).getX() == x &&
			   borders.get(i).getY() == y) {
				location = i;
			}
		}
		return location;
	}

	private int searchOpen(int x, int y) {
		int location = -1;
		
		for (int i =0; i < open.size(); i++) {
			if(open.get(i).getX() == x &&
			   open.get(i).getY() == y) {
				location = i;
			}
		}
		return location;
	}

	public Node lowestFCost() {
		int Switch = -1;
		Node temp;
		if (open.size() > 0) {
			while (Switch != 0) {
				Switch = 0;
				for (int i = 0; i < open.size() - 1; i++) {
					if (open.get(i).getF() > open.get(i + 1).getF()) {
						temp = open.get(i);
						open.remove(i);
						open.add(i + 1, temp);
						Switch = 1;
					}
				}	
			}
			return open.get(0);
		}
		return null;
	}
	
	public void reset() {
		while(open.size() > 0) {
			open.remove(0);
		}
		
		while(closed.size() > 0) {
			closed.remove(0);
		}
		
		while(path.size() > 0) {
			path.remove(0);
		}
		noPath = false;
		running = false;
		complete = false;
	}
	
	public void addBorder(Node node) {
		if (borders.size() == 0) {
			borders.add(node);
		} else if (!checkBorderDuplicate(node)) {
			borders.add(node);
		}
	}
	
	public void removeBorder(int location) {
		borders.remove(location);
	}
	
	public void addOpen(Node node) {
		if (open.size() == 0) {
			open.add(node);
		} else if (!checkOpenDuplicate(node)) {
			open.add(node);
		}
	}
	
	public void removeOpen(Node node) {
		for (int i = 0; i < open.size(); i++) {
			if (node.getX() == open.get(i).getX() && node.getY() == open.get(i).getY()) {
				open.remove(i);
			}
		}
	}

	public void addClosed(Node node) {
		if (closed.size() == 0) {
			closed.add(node);
		} else if (!checkClosedDuplicate(node)) {
			closed.add(node);
		}
	}
	
	public boolean checkBorderDuplicate(Node node) {
		for (int i = 0; i < borders.size(); i++) {
			if (node.getX() == borders.get(i).getX() && node.getY() == borders.get(i).getY()) {
				return true;
			}
		}
		return false;
	}

	public boolean checkOpenDuplicate(Node node) {
		for (int i = 0; i < open.size(); i++) {
			if (node.getX() == open.get(i).getX() && node.getY() == open.get(i).getY()) {
				return true;
			}
		}
		return false;
	}

	public boolean checkClosedDuplicate(Node node) {
		for (int i = 0; i < closed.size(); i++) {
			if (node.getX() == closed.get(i).getX() && node.getY() == closed.get(i).getY()) {
				return true;
			}
		}
		return false;
	}
	
	//getters
	public Frame getFrame() {
		return frame;
	}

	public Node getStartNode() {
		return startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public Node getPar() {
		return par;
	}

	public long getRunTime() {
		return runTime;
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isComplete() {
		return complete;
	}

	public boolean isNoPath() {
		return noPath;
	}

	public ArrayList<Node> getBorders() {
		return borders;
	}

	public ArrayList<Node> getOpen() {
		return open;
	}

	public ArrayList<Node> getClosed() {
		return closed;
	}

	public ArrayList<Node> getPath() {
		return path;
	}

}

