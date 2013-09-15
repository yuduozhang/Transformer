package paperDolls;

import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

/**
 * A building block for creating your own shapes that can be
 * transformed and that can respond to input. This class is
 * provided as an example; you will likely need to modify it
 * to meet the assignment requirements.
 * 
 * Michael Terry
 */
public abstract class Sprite {

	/**
	 * Tracks our current interaction mode after a mouse-down
	 */
	protected enum InteractionMode {
		IDLE,
		DRAGGING,
		SCALING,
		ROTATING
	}
	
	protected 	String				toolTipString 		= "";
	private     Vector<Sprite>      children            = new Vector<Sprite>();     // Holds all of our children
	private     Sprite              parent              = null;                     // Pointer to our parent
	private     AffineTransform     transform           = new AffineTransform();    // Our transformation matrix
	private static AffineTransform 	scaleLeftTransform;
	private static AffineTransform 	scaleRightTransform;
	private 	double 				rotationAngle;
	protected 	double				accumulatedAngle;
	private static 	double			scaleTotal;


	protected   Point2D             lastPoint           = null;                     // Last mouse point
	protected   InteractionMode     interactionMode     = InteractionMode.IDLE;     // Current interaction mode
	protected 	Point2D 			origin 				= new Point2D.Double();
	final protected Point2D 		ORIGIN 				= new Point2D.Double();		// final ORIGIN point, always at (0,0)
	protected 	Point2D			originScale				= new Point2D.Double();
	protected String 				spriteName 			= "";


	public Sprite() {
		; // no-op
		scaleLeftTransform	= new AffineTransform();
		scaleRightTransform	= new AffineTransform();
		scaleTotal			= 1;
		ORIGIN.setLocation(0, 0);
	}

	public Sprite(Sprite parent) {
		if (parent != null) {
			parent.addChild(this);
		}
	}

	public void addChild(Sprite s) {
		children.add(s);
		s.setParent(this);
	}
	public Sprite getParent() {
		return parent;
	}

	// return first child
	public Sprite getFirstChild() {
		return children.get(0);
	}

	private void setParent(Sprite s) {
		this.parent = s;
	}

	/**
	 * Test whether a point, in world coordinates, is within our sprite.
	 */
	public abstract boolean pointInside(Point2D p);

	/**
	 * Handles a mouse down event, assuming that the event has already
	 * been tested to ensure the mouse point is within our sprite.
	 */
	protected void handleMouseDownEvent(MouseEvent e) {
		//System.out.println(toString() + "x: " + e.getX() + "y: " + e.getY());
		lastPoint = e.getPoint();
		if (this.getParent() == null) {
			interactionMode = InteractionMode.DRAGGING;
		}
		else if (e.getButton() == MouseEvent.BUTTON3
				&&
				(spriteName.equals(SpriteName.leftUpperLeg)
						||
						spriteName.equals(SpriteName.leftLowerLeg)
						||
						spriteName.equals(SpriteName.rightUpperLeg)
						||
						spriteName.equals(SpriteName.rightLowerLeg)
				)){
			interactionMode = InteractionMode.SCALING;
		}
		else {
			interactionMode = InteractionMode.ROTATING;
		}
		// Handle rotation, scaling mode depending on input
	}

	/**
	 * Handle mouse drag event, with the assumption that we have already
	 * been "selected" as the sprite to interact with.
	 * This is a very simple method that only works because we
	 * assume that the coordinate system has not been modified
	 * by scales or rotations. You will need to modify this method
	 * appropriately so it can handle arbitrary transformations.
	 */
	protected void handleMouseDragEvent(MouseEvent e) {

		Point2D oldPoint = lastPoint;
		Point2D newPoint = e.getPoint();
		updateOrigin();
		switch (interactionMode) {
		case IDLE:
			; // no-op (shouldn't get here)
			break;
		case DRAGGING:
			double x_diff = newPoint.getX() - oldPoint.getX();
			double y_diff = newPoint.getY() - oldPoint.getY();
			transform.translate(x_diff, y_diff);
			//System.out.println(toString() + "x: " + transform.getTranslateX());
			//System.out.println(toString() + "y: " + transform.getTranslateY());
			break;
		case ROTATING:
			; // Provide rotation code here
			double startAngle 	 = getAngle(origin, oldPoint);
			double currentAngle = getAngle(origin, newPoint);
			rotationAngle = currentAngle - startAngle;
			
			if (Math.abs(rotationAngle) > 0.1){
				rotationAngle = 0;
			}
			//System.out.println("angle: " + (currentAngle - startAngle));
			//System.out.println("rotation angle: " + accumulatedAngle);
			
			if (spriteName.equals(SpriteName.head)){
				if (accumulatedAngle >= 0.7 && rotationAngle > 0){
					accumulatedAngle = 0.7;
					break;
				}
				else if(accumulatedAngle <= -0.7 && rotationAngle < 0) {
					accumulatedAngle = -0.7;
					break;
				}
				transform.rotate(rotationAngle, 27, 61);
			}
			else if (spriteName.equals(SpriteName.leftUpperArm)){
				transform.rotate(rotationAngle, 56, 46);
			}
			else if (spriteName.equals(SpriteName.leftLowerArm)){
				if (accumulatedAngle >= 0.6 && rotationAngle > 0){
					accumulatedAngle = 0.6;
					break;
				}
				else if(accumulatedAngle <= -2.6 && rotationAngle < 0){
					accumulatedAngle = -2.6;
					break;
				}
				transform.rotate(rotationAngle, 61, 37);
			}
			else if (spriteName.equals(SpriteName.leftHand)){
				if (accumulatedAngle >= 0 && rotationAngle > 0){
					accumulatedAngle = 0;
					break;
				}
				else if(accumulatedAngle <= -0.8 && rotationAngle < 0){
					accumulatedAngle = -0.8;
					break;
				}
				transform.rotate(rotationAngle, 8, 9);
			}
			else if (spriteName.equals(SpriteName.rightUpperArm)){
				transform.rotate(rotationAngle, 9, 46);
			}
			else if (spriteName.equals(SpriteName.rightLowerArm)){
				if (accumulatedAngle >= 2.6 && rotationAngle > 0){
					accumulatedAngle = 2.6;
					break;
				}
				else if(accumulatedAngle <= -0.6 && rotationAngle < 0){
					accumulatedAngle = -0.6;
					break;
				}
				transform.rotate(rotationAngle, 12, 45);
			}
			else if (spriteName.equals(SpriteName.rightHand)){
				if (accumulatedAngle >= 0.8 && rotationAngle > 0){
					accumulatedAngle = 0.8;
					break;
				}
				else if(accumulatedAngle <= 0 && rotationAngle < 0){
					accumulatedAngle = 0;
					break;
				}
				transform.rotate(rotationAngle, 30, 19);
			}
			else if (spriteName.equals(SpriteName.leftLowerLeg)){
				if (accumulatedAngle >= 1.5 && rotationAngle > 0){
					accumulatedAngle = 1.5;
					break;
				}
				else if(accumulatedAngle <= -1.5 && rotationAngle < 0){
					accumulatedAngle = -1.5;
					break;
				}
				transform.rotate(rotationAngle, 80, 21);
			}
			else if (spriteName.equals(SpriteName.leftFoot)){
				if (accumulatedAngle >= 0.4 && rotationAngle > 0){
					accumulatedAngle = 0.4;
					break;
				}
				else if(accumulatedAngle <= -0.4 && rotationAngle < 0){
					accumulatedAngle = -0.4;
					break;
				}
				transform.rotate(rotationAngle, 30, 0);
			}
			else if (spriteName.equals(SpriteName.rightLowerLeg)){
				if (accumulatedAngle >= 1.5 && rotationAngle > 0){
					accumulatedAngle = 1.5;
					break;
				}
				else if(accumulatedAngle <= -1.5 && rotationAngle < 0){
					accumulatedAngle = -1.5;
					break;
				}
				transform.rotate(rotationAngle, 29, 11);
			}
			else if (spriteName.equals(SpriteName.rightFoot)){
				if (accumulatedAngle >= 0.4 && rotationAngle > 0){
					accumulatedAngle = 0.4;
					break;
				}
				else if(accumulatedAngle <= -0.4 && rotationAngle < 0){
					accumulatedAngle = -0.4;
					break;
				}
				transform.rotate(rotationAngle, 36, 12);
			}
			else if (spriteName.equals(SpriteName.leftUpperLeg)){
				if (accumulatedAngle >= 1.5 && rotationAngle > 0){
					accumulatedAngle = 1.5;
					break;
				}
				else if(accumulatedAngle <= -1.57 && rotationAngle < 0){
					accumulatedAngle = -1.57;
					break;
				}
				transform.rotate(rotationAngle,31, 23);
			}
			else if (spriteName.equals(SpriteName.rightUpperLeg)){
				if (accumulatedAngle >= 1.58 && rotationAngle > 0){
					accumulatedAngle = 1.58;
					break;
				}
				else if(accumulatedAngle <= -1.5 && rotationAngle < 0){
					accumulatedAngle = -1.5;
					break;
				}
				transform.rotate(rotationAngle, 21, 25);
			}
			
			accumulatedAngle += rotationAngle;
			break;
		case SCALING:
			; // Provide scaling code here
			double oldDistance;
			double newDistance;
			double scale;
			if (spriteName.equals(SpriteName.leftLowerLeg) || spriteName.equals(SpriteName.rightLowerLeg)){
				oldDistance = getParent().originScale.distance(oldPoint);
				newDistance = getParent().originScale.distance(newPoint);
				scale = newDistance/oldDistance;
				
				if ((newPoint.getY() < oldPoint.getY() && scale > 1)
						||
					(newPoint.getY() > oldPoint.getY() && scale < 1)){
					scale = 1/scale;
				}
				
				if (spriteName.equals(SpriteName.leftLowerLeg)){
					scaleLeftTransform.scale(1, scale);
					scaleRightTransform.scale(1, scale);
				}
				else {
					scaleLeftTransform.scale(1, scale);
					scaleRightTransform.scale(1, scale);
				}
			}
			else{
				oldDistance = originScale.distance(oldPoint);
				newDistance = originScale.distance(newPoint);
				scale = newDistance/oldDistance;
				
				if ((newPoint.getY() < oldPoint.getY() && scale > 1)
						||
					(newPoint.getY() > oldPoint.getY() && scale < 1)){
					scale = 1/scale;
				}
				
				if (spriteName.equals(SpriteName.leftUpperLeg) ){
					scaleLeftTransform.scale(1, scale);
					scaleRightTransform.scale(1, scale);
				}
				else {
					scaleLeftTransform.scale(1, scale);
					scaleRightTransform.scale(1, scale);
				}
			}
			
			scaleTotal = scale*scaleTotal;
			//System.out.println("scale total is: " + scaleTotal);
			
			break;
		}
		// Save our last point, if it's needed next time around
		lastPoint = e.getPoint();
	}

	protected void handleMouseUp(MouseEvent e) {
		interactionMode = InteractionMode.IDLE;
		// Do any other interaction handling necessary here
	}
	
	protected void handleMouseMoved(MouseEvent e, SpriteCanvas canvas) {
		canvas.setToolTipText(toolTipString);
	}

	/**
	 * Locates the sprite that was hit by the given event.
	 * You *may* need to modify this method, depending on
	 * how you modify other parts of the class.
	 * 
	 * @return The sprite that was hit, or null if no sprite was hit
	 */
	public Sprite getSpriteHit(MouseEvent e) {
		for (Sprite sprite : children) {
			Sprite s = sprite.getSpriteHit(e);
			if (s != null) {
				return s;
			}
		}
		if (this.pointInside(e.getPoint())) {
			return this;
		}
		return null;
	}

	/*
	 * Important note: How transforms are handled here are only an example. You will
	 * likely need to modify this code for it to work for your assignment.
	 */

	/**
	 * Returns the full transform to this object from the root
	 */
	public AffineTransform getFullTransform() {
		AffineTransform returnTransform = new AffineTransform();
		Sprite curSprite = this;
		while (curSprite != null) {
//			if (curSprite.spriteName.equals(SpriteName.leftUpperLeg) || 
//					curSprite.spriteName.equals(SpriteName.rightUpperLeg)){
//				returnTransform.preConcatenate(curSprite.getLocalScaleTransform());
//			}
//			else{
				returnTransform.preConcatenate(curSprite.getLocalTransform());
//			}
				if (curSprite.spriteName.equals(SpriteName.leftLowerLeg) || 
						curSprite.spriteName.equals(SpriteName.rightLowerLeg)){
					returnTransform.preConcatenate(AffineTransform.getTranslateInstance(0, (scaleTotal-1)*94));
				}
				else if (curSprite.spriteName.equals(SpriteName.leftFoot))
					returnTransform.preConcatenate(AffineTransform.getTranslateInstance(0, (scaleTotal-1)*178));
				else if (curSprite.spriteName.equals(SpriteName.rightFoot))
					returnTransform.preConcatenate(AffineTransform.getTranslateInstance(0, (scaleTotal-1)*169));
				
				
			//if (curSprite.spriteName.equals(SpriteName.leftLowerLeg))
			curSprite = curSprite.getParent();
		}
		/*if (spriteName.equals(SpriteName.leftFoot) || spriteName.equals(SpriteName.rightFoot)){
			AffineTransform inverseScaleTransform = null;
			if (spriteName.equals(SpriteName.leftFoot)){
				try {
					inverseScaleTransform = scaleLeftTransform.createInverse();
				} catch (NoninvertibleTransformException e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					inverseScaleTransform = scaleRightTransform.createInverse();
				} catch (NoninvertibleTransformException e) {
					e.printStackTrace();
				}
			}
			returnTransform.concatenate(inverseScaleTransform);
		}*/
		if (spriteName.equals(SpriteName.leftUpperLeg)
				|| spriteName.equals(SpriteName.leftLowerLeg)){
			returnTransform.concatenate(scaleLeftTransform);
		}
		else if (spriteName.equals(SpriteName.rightUpperLeg)
				|| spriteName.equals(SpriteName.rightLowerLeg)){
			returnTransform.concatenate(scaleRightTransform);
		}
		return returnTransform;
	}

	/**
	 * Returns our local transform
	 */
	public AffineTransform getLocalTransform() {
		return (AffineTransform)transform.clone();
	}

	public AffineTransform getLocalScaleTransform() {
		AffineTransform scaleTransform = (AffineTransform)transform.clone();
		if (spriteName.equals(SpriteName.leftUpperLeg)){
			scaleTransform.concatenate(scaleLeftTransform);
		}
		else if (spriteName.equals(SpriteName.rightUpperLeg)){
			scaleTransform.concatenate(scaleRightTransform);
		}
		return scaleTransform;
	}

	/**
	 * Performs an arbitrary transform on this sprite
	 */
	public void transform(AffineTransform t) {
		transform.concatenate(t);
	}

	/**
	 * Draws the sprite. This method will call drawSprite after
	 * the transform has been set up for this sprite.
	 */
	public void draw(Graphics2D g) {
		AffineTransform oldTransform = g.getTransform();

		// Set to our transform
		g.setTransform(this.getFullTransform());

		// Draw the sprite (delegated to sub-classes)
		
		// add scaling
		this.drawSprite(g);
		// remove scaling (restore to old transform)
		//g.scale(arg0, arg1);
		// Restore original transform
		g.setTransform(oldTransform);

		// Draw children
		for (Sprite sprite : children) {
			sprite.draw(g);
		}
	}

	public void updateOrigin() {
		AffineTransform fullTransform = this.getFullTransform();
		fullTransform.transform(ORIGIN, originScale);
		if (spriteName.equals(SpriteName.head)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(27, 61));
		}
		else if (spriteName.equals(SpriteName.leftUpperArm)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(56, 46));
		}
		else if (spriteName.equals(SpriteName.leftLowerArm)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(61, 37));
		}
		else if (spriteName.equals(SpriteName.leftHand)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(8, 9));
		}
		else if (spriteName.equals(SpriteName.rightUpperArm)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(9, 46));
		}
		else if (spriteName.equals(SpriteName.rightLowerArm)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(12, 45));
		}
		else if (spriteName.equals(SpriteName.rightHand)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(30, 19));
		}
		else if (spriteName.equals(SpriteName.leftLowerLeg)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(80, 21));
		}
		else if (spriteName.equals(SpriteName.leftFoot)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(30, 0));
		}
		else if (spriteName.equals(SpriteName.rightLowerLeg)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(29, 11));
		}
		else if (spriteName.equals(SpriteName.rightFoot)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(36, 12));
		}
		else if (spriteName.equals(SpriteName.leftUpperLeg)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(31, 23));
		}
		else if (spriteName.equals(SpriteName.rightUpperLeg)){
			fullTransform.concatenate(AffineTransform.getTranslateInstance(21, 25));
		}
		
		fullTransform.transform(ORIGIN, origin);
	}

	/**
	 * The method that actually does the sprite drawing. This method
	 * is called after the transform has been set up in the draw() method.
	 * Sub-classes should override this method to perform the drawing.
	 */
	protected abstract void drawSprite(Graphics2D g);

	// Code from Stack Overflow
	protected double getAngle(Point2D origin, Point2D other) {
		double dy = other.getY() - origin.getY();
		double dx = other.getX() - origin.getX();
		double angle;
		if (dx == 0) // special case
			angle = dy >= 0? Math.PI/2: - Math.PI/2;
			else{
				angle = Math.atan(dy/dx);
				if (dx < 0) // hemisphere correction
					angle += Math.PI;
			}
		// all between 0 and 2PI
		if (angle < 0) // between -PI/2 and 0
			angle += 2*Math.PI;
		return angle;
	}
}
