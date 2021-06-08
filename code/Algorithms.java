package myWork;

import java.util.ArrayList;

public abstract class Algorithms {
	int size;
	Frame frame;
	Node startNode, endNode, par;
	long runTime;
	boolean running, complete, noPath;
	ArrayList<Node> borders, open, closed, path;
	
	public Algorithms (Frame frame, int size) {
		this.frame = frame;
		this.size = size;
		
		running = false;
		complete = false;	
		
		borders = new ArrayList<Node>();
		open = new ArrayList<Node>();
		closed = new ArrayList<Node>();
		path = new ArrayList<Node>();
		
	}
	
	public abstract void start(Node s, Node e);
	
	public abstract void setup(Node s, Node e);

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
	


	public void reverse(ArrayList<Node> list) {
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
	
	//these are used to check if a node has already been an open node, close node or border
	//return -1  means they are not in the list
	//otherwise return location
	public int searchClosed(int x, int y) {
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

	public int searchOpen(int x, int y) {
		int location = -1;
		
		for (int i =0; i < open.size(); i++) {
			if(open.get(i).getX() == x &&
			   open.get(i).getY() == y) {
				location = i;
			}
		}
		return location;
	}
	
	//clear all 
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
	
	//border cannot be duplicated
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


