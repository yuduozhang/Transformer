package paperDolls;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A canvas that draws sprites and provides capabilities for recording events
 * and playing them back.
 * 
 * Michael Terry
 */
public class SpriteCanvas extends JPanel {

	
	/**
	 * Tracks our current interactive mode
	 */
	private enum InteractionMode {
		IDLE, RECORDING, PLAYBACK
	}

	private Vector<Sprite> sprites = new Vector<Sprite>(); // All sprites we're
															// managing
	private Sprite interactiveSprite = null; // Sprite with which user is
												// interacting
	private InteractionMode interactionMode = InteractionMode.IDLE; // Current
																	// interactive
																	// mode
	private Vector<Serializable> eventStream = null; // Event stream for
														// recording events

	public BufferedImage img;
	
	public SpriteCanvas(String path) {
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		initialize();
	}

	
	

	private void initialize() {
		// Install our event handlers
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				handleMousePress(e);
			}
			public void mouseReleased(MouseEvent e) {
				handleMouseReleased(e);
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				handleMouseDragged(e);
			}
			
			public void mouseMoved(MouseEvent e) { 
				handleMouseMoved(e); 
			}  
		});

	}


	/**
	 * Handle mouse press events from user or demo file
	 */
	private void handleMousePress(java.awt.event.MouseEvent e) {
		for (Sprite sprite : sprites) {
			interactiveSprite = sprite.getSpriteHit(e);
			if (interactiveSprite != null) {
				interactiveSprite.handleMouseDownEvent(e);
				break;
			}
		}
	}

	/**
	 * Handle mouse released events from user or demo file
	 */
	private void handleMouseReleased(MouseEvent e) {
		if (interactiveSprite != null) {
			interactiveSprite.handleMouseUp(e);
			repaint();
		}
		interactiveSprite = null;
	}

	/**
	 * Handle mouse dragged events from user or demo file
	 */
	private void handleMouseDragged(MouseEvent e) {
		if (interactiveSprite != null) {
			interactiveSprite.handleMouseDragEvent(e);
			repaint();
		}
	}
	
	protected void handleMouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		boolean checkFound = false;
		for (Sprite sprite : sprites) {
			Sprite interactiveSpriteTemp = sprite.getSpriteHit(e);
			if (interactiveSpriteTemp != null) {
				checkFound = true;
				interactiveSpriteTemp.handleMouseMoved(e, this);
				break;
			}
		}
		if (!checkFound){
			setToolTipText(SpriteName.mainTip);
		}
	}


	/**
	 * Add a top-level sprite to manage
	 */
	public void addSprite(Sprite s) {
		sprites.add(s);
	}

	/**
	 * Paint our canvas
	 */
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(img, 0, 0, null);
		g.setColor(Color.BLACK);

		for (Sprite sprite : sprites) {
			sprite.draw((Graphics2D) g);
		}
	}

	public void reset(Sprite s) {
		sprites.clear();
		addSprite(s);
		repaint();
	}

}
