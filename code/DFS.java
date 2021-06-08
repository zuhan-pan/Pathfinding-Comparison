package myWork;

import java.util.ArrayList;


public class DFS extends Algorithms {
	
	public DFS(Frame frame, int size) {
		super(frame,size);
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
		Node newParent = null;
		//add four neighbors of the parent node to the openNode
		ArrayList<Node> ligitList =  new ArrayList<Node>();
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int x = (parent.getX() - size) + (size * i);
				int y = (parent.getY() - size) + (size * j);
				
				if((i == 0 && j == 0) || (i == 0 && j == 2) || 
						(i == 1 && j == 1) || (i == 2 && j == 0) ||
						(i == 2 && j == 2)) {
						continue;
				}
				
				if(x < 0 || x > frame.getWidth() || 
				   y < 0 || y > frame.getHeight()) {
					continue;
				}
				
				if(searchBorder(x, y) != -1 || 
				        searchClosed(x, y) != -1 || 
				        searchOpen(x, y) != -1) {
					    continue;
				}	
				Node openNode = new Node(x,y);
				openNode.setParent(parent);
				ligitList.add(openNode);
			}
		}
		
		
		//up
		for(Node ligit : ligitList){
			if(ligit.getX() == parent.getX() && ligit.getY() == (parent.getY() - size)) {
				if(newParent == null) {
					newParent = ligit;
				}
				addOpen(ligit);
			}
		}
		
		//right
		for(Node ligit : ligitList){
			if(ligit.getX() == (parent.getX() + size) && ligit.getY() == parent.getY()) {
				if(newParent == null) {
					newParent = ligit;
				}
				addOpen(ligit);
			}
		}
		
		//down
		for(Node ligit : ligitList){
			if(ligit.getX() == parent.getX() && ligit.getY() == (parent.getY() + size)) {
				if(newParent == null) {
					newParent = ligit;
				}
				addOpen(ligit);
			}
		}
		
		//left
		for(Node ligit : ligitList){
			if(ligit.getX() == (parent.getX() - size) && ligit.getY() == parent.getY()) {
				if(newParent == null) {
					newParent = ligit;
				}
				addOpen(ligit);
			}
		}
		
		if(newParent == null) {
			if(open.size() > 0) {
				newParent = open.get(open.size() - 1);
			}else {
				System.out.println("NO PATH");
				noPath = true;
				running = false;
				complete = false;
				frame.repaint();
				return;
			}
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
		ligitList.clear();
		
		if(!frame.visualization()) {
			findPath(newParent);
		}else {
			par = newParent;
		}
	}
}