package paperDolls;

import java.awt.Canvas;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class Main{
	
	private static SpriteCanvas canvas;
	
	
	public static void main(String[] args) {		

		canvas = new SpriteCanvas("res/transformersBackground.jpg");
		canvas.addSprite(Main.makeSprite());
		
		JFrame f = new JFrame();
		f.setJMenuBar(Main.makeMenuBar(canvas));
		f.getContentPane().add(canvas);
		f.getContentPane().setLayout(new GridLayout(1, 1));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				 int keyCode = e.getKeyCode();
				 if (keyCode == KeyEvent.VK_R && e.isControlDown()){
					 canvas.reset(Main.makeSprite());
				 }
			}
		});
		int width = canvas.img.getWidth();
		int height = canvas.img.getHeight();
		
		//System.out.println(width + " " + height +  " " + locateX);
		f.setSize(width, height);
		//f.setLocation(1, 0);
		f.setResizable(false);
		f.setVisible(true);
	}
	
	static Sprite makeSprite() {
		Sprite body = new RectangleSprite("res/body.png", SpriteName.body, 
				SpriteName.bodyTip);
		Sprite head = new RectangleSprite("res/head.png", SpriteName.head, 
				SpriteName.headTip);
		Sprite leftFoot = new RectangleSprite("res/leftFoot.png", 
				SpriteName.leftFoot, SpriteName.footTip);
		Sprite leftHand = new RectangleSprite("res/leftHand.png", 
				SpriteName.leftHand, SpriteName.handTip);
		Sprite leftLowerArm = new RectangleSprite("res/leftLowerArm.png", 
				SpriteName.leftLowerArm, SpriteName.lowerArmTip);
		Sprite leftLowerLeg = new RectangleSprite("res/leftLowerLeg.png", 
				SpriteName.leftLowerLeg, SpriteName.lowerLegTip);
		Sprite leftUpperArm = new RectangleSprite("res/leftUpperArm.png",
				SpriteName.leftUpperArm, SpriteName.upperArmTip);
		Sprite leftUpperLeg = new RectangleSprite("res/leftUpperLeg.png", 
				SpriteName.leftUpperLeg, SpriteName.upperLegTip);
		Sprite rightFoot = new RectangleSprite("res/rightFoot.png", 
				SpriteName.rightFoot, SpriteName.footTip);
		Sprite rightHand = new RectangleSprite("res/rightHand.png", 
				SpriteName.rightHand, SpriteName.handTip);
		Sprite rightLowerArm = new RectangleSprite("res/rightLowerArm.png", 
				SpriteName.rightLowerArm, SpriteName.lowerArmTip);
		Sprite rightLowerLeg = new RectangleSprite("res/rightLowerLeg.png", 
				SpriteName.rightLowerLeg, SpriteName.lowerLegTip);
		Sprite rightUpperArm = new RectangleSprite("res/rightUpperArm.png",
				SpriteName.rightUpperArm, SpriteName.upperArmTip);
		Sprite rightUpperLeg = new RectangleSprite("res/rightUpperLeg.png", 
				SpriteName.rightUpperLeg, SpriteName.upperLegTip);

		body.transform(AffineTransform.getTranslateInstance(590, 155));
		
		head.transform(AffineTransform.getTranslateInstance(35,-5));
		
		leftUpperArm.transform(AffineTransform.getTranslateInstance(-60, 3));
		leftLowerArm.transform(AffineTransform.getTranslateInstance(-48, 69));
		leftHand.transform(AffineTransform.getTranslateInstance(43, 115));
		
		rightUpperArm.transform(AffineTransform.getTranslateInstance(131, -4));
		rightLowerArm.transform(AffineTransform.getTranslateInstance(37, 65));
		rightHand.transform(AffineTransform.getTranslateInstance(-16, 114));
		
		leftUpperLeg.transform(AffineTransform.getTranslateInstance(-15, 142));
		leftLowerLeg.transform(AffineTransform.getTranslateInstance(-45, 94));
		leftFoot.transform(AffineTransform.getTranslateInstance(-5, 178));
		
		rightUpperLeg.transform(AffineTransform.getTranslateInstance(93, 140));
		rightLowerLeg.transform(AffineTransform.getTranslateInstance(-6, 94));
		rightFoot.transform(AffineTransform.getTranslateInstance(31, 169));
		

		body.addChild(head);
		body.addChild(rightUpperArm);
		body.addChild(leftUpperArm);
		body.addChild(rightUpperLeg);
		body.addChild(leftUpperLeg);
		
		rightUpperArm.addChild(rightLowerArm);
		rightLowerArm.addChild(rightHand);
		
		leftUpperArm.addChild(leftLowerArm);
		leftLowerArm.addChild(leftHand);
		
		rightUpperLeg.addChild(rightLowerLeg);
		rightLowerLeg.addChild(rightFoot);
		
		leftUpperLeg.addChild(leftLowerLeg);
		leftLowerLeg.addChild(leftFoot);
		
		return body;
	}

	/* Menu with resetting and quit. */
	private static JMenuBar makeMenuBar(final SpriteCanvas canvas) {
		JMenuBar mbar = new JMenuBar();

		JMenu file = new JMenu("File");
		
		final JMenuItem reset = new JMenuItem("Reset (Ctrl-R)");
		final JMenuItem quit = new JMenuItem("Quit");

		file.add(reset);
		file.addSeparator();
		file.add(quit);

		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.reset(Main.makeSprite());
			}
		});

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		mbar.add(file);
		
		return mbar;
	}

}
