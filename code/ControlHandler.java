package myWork;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlHandler {
	private Frame frame;
	private JLabel algorithmsT, speedT, speedC, computedT, 
	lengthT, timeT, computedC, lengthC, timeC;
	private JCheckBox visualizationCheck;
	private JSlider speed;
	private JComboBox<String> algorithms;
	private JButton run;
	private ArrayList<JLabel> labels;
	private ArrayList<JCheckBox> checks;
	private ArrayList<JSlider> sliders;
	private ArrayList<JButton> buttons;
	private ArrayList<JComboBox<String>> comboBoxes;
	private String[] choices = { "Astar", "BFS","DFS", "Dijkstra","Greedy"};
	Dimension npD;
	
	public ControlHandler(final Frame frame) {
		this.frame = frame;
		labels = new ArrayList<JLabel>();
		checks = new ArrayList<JCheckBox>();
		sliders = new ArrayList<JSlider>();
		buttons = new ArrayList<JButton>();
		comboBoxes = new ArrayList<JComboBox<String>>();
		
		// Set up JLabels
		algorithmsT = new JLabel("Algorithms: ");
		algorithmsT.setName("algorithmsT");
		algorithmsT.setVisible(true);
		
		speedT = new JLabel("Speed: ");
		speedT.setName("speedT");

		speedT.setVisible(true);
		
		speedC = new JLabel("50");
		speedC.setName("speedC");

		speedC.setVisible(true);
		
		computedT = new JLabel("Computed");
		computedT.setName("computedT");

		computedT.setVisible(true);
		
		computedC = new JLabel("0");
		computedC.setName("computedC");
		computedC.setVisible(true);
		
		lengthT = new JLabel("Length");
		lengthT.setName("lengthT");
		lengthT.setVisible(true);
		
		lengthC = new JLabel("0");
		lengthC.setName("lengthC");
		lengthC.setVisible(true);
		
		timeT = new JLabel("Time");
		timeT.setName("timeT");
		timeT.setVisible(true);
		
		timeC = new JLabel("N/A");
		timeC.setName("timeC");
		timeC.setVisible(true);
		
		
		// Add JLabels to list
		labels.add(algorithmsT);
		labels.add(speedT);
		labels.add(speedC);
		labels.add(computedT);
		labels.add(computedC);
		labels.add(lengthT);
		labels.add(lengthC);
		labels.add(timeT);
		labels.add(timeC);
		
		// Set up JCheckBoxes
		visualizationCheck = new JCheckBox();
		visualizationCheck.setText("Visualization");
		visualizationCheck.setName("visualizationCheck");
		visualizationCheck.setSelected(true);
		visualizationCheck.setOpaque(false);
		visualizationCheck.setFocusable(false);
		visualizationCheck.setVisible(true);
		
		
		// Add JCheckboxes to list
		checks.add(visualizationCheck);
		
		// Set up JSliders
		speed = new JSlider();
		speed.setName("speed");
		speed.setOpaque(false);
		speed.setVisible(true);
		speed.setFocusable(false);
		speed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				speed.setValue(source.getValue());
				frame.setSpeed();
				frame.repaint();
			}
		});
        speed.addKeyListener(frame);
        
		// Add JSliders to list
		sliders.add(speed);
		
		//Set up JcomboBoxes
	    algorithms = new JComboBox<String>(choices);
	    algorithms.setName("algorithms");
	    algorithms.addActionListener(frame);
	    algorithms.addKeyListener(frame);
	    algorithms.setVisible(true);
	    comboBoxes.add(algorithms);
		
		// Set up JButtons
		run = new JButton();
		run.setText("run");
		run.setName("run");
		run.setFocusable(false);
		run.addActionListener(frame);
		run.setMargin(new Insets(0,0,0,0));
		run.setVisible(true);
		
		// Add JButtons to list
		buttons.add(run);
	}
	
	// Gets a specific JLabel by name
	public JLabel getL(String t) {
		for(int i = 0; i < labels.size(); i++) {
			if(labels.get(i).getName().equals(t)) {
				return labels.get(i);
			}
		}
		return null;
	}
	
	// Gets specific JCheckBox by name
	public JCheckBox getC(String t) {
		for(int i = 0; i < checks.size(); i++) {
			if(checks.get(i).getName().equals(t)) {
				return checks.get(i);
			}
		}
		return null;
	}
	
	// Gets specific JSlider by name
	public JSlider getS(String t) {
		for(int i = 0; i < sliders.size(); i++) {
			if(sliders.get(i).getName().equals(t)) {
				return sliders.get(i);
			}
		}
		return null;
	}
	
	// Gets specific JButton by name
	public JButton getB(String t) {
		for(int i = 0; i < buttons.size(); i++) {
			if(buttons.get(i).getName().equals(t)) {
				return buttons.get(i);
			}
		}
		return null;
	}
	
	// Gets specific JComboBox by name
		public JComboBox<String> getCo(String t) {
			for(int i = 0; i < comboBoxes.size(); i++) {
				if(comboBoxes.get(i).getName().equals(t)) {
					return comboBoxes.get(i);
				}
			}
			return null;
		}
	
	
	public void position() {
		// Set label bounds
		speedT.setBounds(40, frame.getHeight()-50, 60, 20);
		speedC.setBounds(90, frame.getHeight()-50, 60, 20);
		computedT.setBounds(320, frame.getHeight()-80, 80, 20);
		computedC.setBounds(320, frame.getHeight()-50, 80, 20);
		lengthT.setBounds(420, frame.getHeight()-80, 60, 20);
		lengthC.setBounds(420, frame.getHeight()-50, 60, 20);
		timeT.setBounds(480, frame.getHeight()-80, 60, 20);
		timeC.setBounds(480, frame.getHeight()-50, 60, 20);
		Dimension size = algorithmsT.getPreferredSize();
		algorithmsT.setBounds(40, frame.getHeight() - 88, size.width, size.height);
		
		// Set check box bounds
		visualizationCheck.setBounds(40 , frame.getHeight() - 70, 200, 20);
		
		// Set slider bounds
		speed.setBounds(110, frame.getHeight()-50, 68, 20);
		
		// Set button bounds
		run.setBounds(45, frame.getHeight() - 30, 150, 20);
		
		//Set comboBox bounds
		algorithms.setBounds(130, frame.getHeight()- 90, 100, 20);
	}
	
	// Adds all components to frame
	public void addAll() {
		frame.add(visualizationCheck);
		frame.add(run);
		frame.add(algorithmsT);
		frame.add(computedT);
		frame.add(computedC);
		frame.add(lengthT);
		frame.add(lengthC);
		frame.add(timeT);
		frame.add(timeC);
		frame.add(speed);
		frame.add(speedT);
		frame.add(speedC);
		frame.add(algorithms);
	}
	
}
