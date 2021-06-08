package myWork;

import java.util.ArrayList;


public class BFS extends Algorithms{
	
	public BFS(Frame frame, int size) {
		super(frame, size);
		
	}
	
	public void start(Node s, Node e) {
		running = true;
		startNode = s;
		endNode = e;
		
		addOpen(startNode);
		
		long startTime = System.currentTimeMillis();
		
		findPath();
		
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
		addOpen(startNode);
	}

	public void findPath() {
		ArrayList<Node> sons = new ArrayList<Node>();
		
		if (open.size() == 0) {
			System.out.println("NO PATH");
			noPath = true;
			running = false;
			complete = false;
			frame.repaint();
			return;
		}
		
		
		
		for(Node parent : open) {
			
			//have path
			if (Node.isEqual(parent, endNode)){
				endNode.setParent(parent.getParent());
				connectPath();
				running = false;
				complete = true;
				frame.repaint();
				return;
			}
			
			addClosed(parent);
			//search the near four block and add them in open list
			//
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if((i == 0 && j == 0) || (i == 0 && j == 2) || 
							(i == 1 && j == 1) || (i == 2 && j == 0) ||
							(i == 2 && j == 2)) {
							continue;
					}
					
					int x = (parent.getX() - size) + (size * i);
					int y = (parent.getY() - size) + (size * j);
					
					if(x < 0 || x >= frame.getWidth() || 
					   y < 0 || y >= frame.getHeight()) {
						continue;
					}
							

					if(searchBorder(x, y) != -1 || 
					   searchClosed(x, y) != -1 ||
					   searchOpen(x, y) != -1) {
						continue;
					}
					
					Node openNode = new Node(x,y);
					openNode.setParent(parent);
					sons.add(openNode);
				}
			}
		}
		open.clear();
		for(Node son : sons) {
			addOpen(son);
		}
		
		if(!frame.visualization()) {
			findPath();
		}
		
	}	
}