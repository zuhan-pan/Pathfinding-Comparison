package myWork;




public class Dijkstra extends Algorithms {
	
	public Dijkstra(Frame frame, int size) {
		super(frame, size);
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
		openNode.setH(hCost);
		
		//calculating fCost
		int fCost = gCost + hCost;
		openNode.setF(fCost);
		
		addOpen(openNode);
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
}
	

