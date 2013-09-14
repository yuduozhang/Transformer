package paperDolls;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * A simple demo of how to create a rectangular sprite.
 * 
 * Michael Terry
 */
public class RectangleSprite extends Sprite {

	private Rectangle2D rect = null;
	private BufferedImage img = null;
	/**
	 * Creates a rectangle based at the origin with the specified
	 * width and height
	 */
	public RectangleSprite(String path, String name, String toolTip) {
		super();
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.spriteName = name;
		this.toolTipString = toolTip;
		this.initialize();
		
	}
	/**
	 * Creates a rectangle based at the origin with the specified
	 * width, height, and parent
	 */
	public RectangleSprite(int width, int height, Sprite parentSprite, 
			String path, String name, String toolTip) {
		super(parentSprite);
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.spriteName = name;
		this.toolTipString = toolTip;
		this.initialize();
	}

	private void initialize() {
		int width  = img.getWidth();
		int height = img.getHeight();
		rect = new Rectangle2D.Double(0, 0, width, height);
		origin.setLocation(0, 0);
		originScale.setLocation(0, 0);
		if (spriteName.equals(SpriteName.head)
				|| 
				spriteName.equals(SpriteName.rightHand)
				||
				spriteName.equals(SpriteName.leftHand)){
			accumulatedAngle = 0;
		}
		else if (spriteName.equals(SpriteName.leftLowerArm)){
			accumulatedAngle = -0.6;
		}
		else if (spriteName.equals(SpriteName.rightLowerArm)){
			accumulatedAngle = 0.6;
		}	
	}


	/**
	 * Test if our rectangle contains the point specified.
	 */
	public boolean pointInside(Point2D p) {
		AffineTransform fullTransform = this.getFullTransform();
		AffineTransform inverseTransform = null;
		try {
			inverseTransform = fullTransform.createInverse();
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		Point2D newPoint = (Point2D)p.clone();
		inverseTransform.transform(newPoint, newPoint);
		return rect.contains(newPoint);
	}

	protected void drawSprite(Graphics2D g) {
		g.setColor(Color.BLACK);
		//g.draw(rect);
		g.drawImage(img, null, 0, 0);
		//        g.draw(this.getFullTransform().createTransformedShape(rect));
	}

	public String toString() {
		return spriteName;
	}
}
