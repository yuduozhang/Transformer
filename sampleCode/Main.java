package sampleCode;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main {

	/**
	 * Test code.
	 */
	public static void main(String[] args) {		

		SpriteCanvas canvas = new SpriteCanvas();
		canvas.addSprite(Main.makeSprite());

		JFrame f = new JFrame();
		f.setJMenuBar(Main.makeMenuBar(canvas));
		f.getContentPane().add(canvas);
		f.getContentPane().setLayout(new GridLayout(1, 1));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 300);
		f.setVisible(true);
	}
	
	/* Make a sample sprite for testing purposes. */
	private static Sprite makeSprite() {
		Sprite firstArm = new RectangleSprite(80, 50);
		Sprite secondArm = new RectangleSprite(50, 40);
		Sprite thirdArm = new RectangleSprite(70, 30);
		Sprite fourthArm = new RectangleSprite(10, 10);
		Sprite fifthArm = new RectangleSprite(10, 10);

		firstArm.transform(AffineTransform.getTranslateInstance(0, 25));
		secondArm.transform(AffineTransform.getTranslateInstance(80, 5));
		thirdArm.transform(AffineTransform.getTranslateInstance(50, 5));
		fourthArm.transform(AffineTransform.getTranslateInstance(70, 30));
		fourthArm.transform(AffineTransform.getScaleInstance(4, 3));
		fourthArm.transform(AffineTransform
				.getRotateInstance(Math.PI / 180 * 30));
		fifthArm.transform(AffineTransform.getTranslateInstance(10, 10));

		firstArm.addChild(secondArm);
		secondArm.addChild(thirdArm);
		thirdArm.addChild(fourthArm);
		fourthArm.addChild(fifthArm);
		
		return firstArm;
	}

	/* Menu with recording and playback. */
	private static JMenuBar makeMenuBar(final SpriteCanvas canvas) {
		JMenuBar mbar = new JMenuBar();

		JMenu script = new JMenu("Scripting");
		final JMenuItem record = new JMenuItem("Start recording");
		final JMenuItem play = new JMenuItem("Start script");

		script.add(record);
		script.add(play);

		record.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (record.getText().equals("Start recording")) {
					record.setText("Stop recording");
					canvas.startRecording();
				} else if (record.getText().equals("Stop recording")) {
					record.setText("Start recording");
					canvas.stopRecording();
				} else {
					assert false;
				}
			}
		});

		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (play.getText().equals("Start script")) {
					play.setText("Stop script");
					record.setEnabled(false);
					canvas.startDemo();
				} else if (play.getText().equals("Stop script")) {
					play.setText("Start script");
					record.setEnabled(true);
					canvas.stopRecording();
				} else {
					assert false;
				}
			}
		});

		mbar.add(script);
		return mbar;
	}

}
