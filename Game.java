import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;

public class Game{

	private Game() {
		
		//Frame
		final JFrame frame = new JFrame("Duck Hunt");
		frame.setLocation(750, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		final JWindow instructions = new JWindow(frame);
		frame.add(instructions);
		*/
		
		//Object[] options = {"One Player"};
		//JOptionPane.showMessageDialog(frame, "DUCK HUNT");

		//Main Duck Range
		final DuckRange range = new DuckRange();
		frame.add(range, BorderLayout.CENTER);
		
		
		//Reset Button
		final JPanel panel = new JPanel();
		frame.add(panel, BorderLayout.PAGE_END);
		
		//One Duck Mode
		final JButton oneDuck = new JButton("One Duck");
		oneDuck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				range.setToOneDuck();
				range.newGame();
			}
		});
		panel.add(oneDuck);
		
		//Two Duck Mode
		final JButton twoDucks = new JButton("Two Ducks");
		twoDucks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				range.setToTwoDucks();
				range.newGame();
			}
		});
		panel.add(twoDucks);
		
		
		//Reset Button
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				range.newGame();
			}
		});
		panel.add(reset);
		
		//Pause Button
		final JButton pause = new JButton("Pause");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isPaused = range.pause();
				if(isPaused){
					pause.setText("Resume");
				}
				else{	
					pause.setText("Pause");	
				}
			}
		});
		panel.add(pause);

		//Instruction Window
		final JButton about = new JButton("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isPaused = range.pause();
				if (isPaused){
					pause.setText("Resume");
				}
				else{
					pause.setText("Pause");
				}
				JOptionPane.showMessageDialog(frame, "Duck Hunt\n" +
			            "\n" +
						"Shoot the ducks as they appear on the screen\n" + 
						"Watch out though if you run out of bullets or\n" +
						"Wait too long the duck will fly away!\n" +
						"Look out for power-ups to help you out\n" +
						"\n" +
						"2 ways to play:\n" +
						"\n" +
						"1) Single Duck\n" +
						"\n" +
						"2) Double Duck\n" +
						"\n" +
						"\n" +
						"By Vivek Sivakumar");
			}
		});
		panel.add(about);
		
		
		
		// Display the frame
		frame.pack();
        frame.setVisible(true);
        range.newGame();
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new Game();
			}
		});

	}

}
