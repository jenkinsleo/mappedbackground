import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class LDNJSprite implements DisplayableSprite, MovableSprite, CollidingSprite {

	Image image = null;
	private double centerX = 0;
	private double centerY = 0;
	private double width = 46;
	private double height = 46;
	
	private long score = 0;
	
	
	private boolean exitStatus = false;
	private String proximityMessage;
	
	private double VELOCITY = 200;
	
	public LDNJSprite(double x, double y) {
		super();

		if (image == null) {
			try {
				image = ImageIO.read(new File("res/ldnj/avatar.png"));
				
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}
			
			
		}
		
		this.setCenterX(x);
		this.setCenterY(y);
		
	
	}
	
	public LDNJSprite() {
		this(0,0);
	}
	
	@Override
	public long getScore() {
		return this.score;
	}

	@Override
	public String getProximityMessage() {
		return this.proximityMessage;
	}

	@Override
	public boolean getIsAtExit() {
		return this.exitStatus;
	}

	@Override
	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	@Override
	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	@Override
	public void stop() {
		
	}

	@Override
	public Image getImage() {
		return this.image;
	}

	@Override
	public boolean getVisible() {
		return true;
	}

	@Override
	public double getMinX() {
		return this.centerX - (this.width / 2);
	}

	@Override
	public double getMaxX() {
		return this.centerX + (this.width / 2);
	}

	@Override
	public double getMinY() {
		return this.centerY - (this.height / 2);
	}

	@Override
	public double getMaxY() {
		return this.centerY + (this.height / 2);
	}

	@Override
	public double getHeight() {
		return this.height;
	}

	@Override
	public double getWidth() {
		return this.width;
	}

	@Override
	public double getCenterX() {
		return this.centerX;
	}

	@Override
	public double getCenterY() {
		return this.centerY;
	}

	@Override
	public boolean getDispose() {
		return false;
	}

	@Override
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {		
		double velocityX = 0;
		double velocityY = 0;
		
		//LEFT	
		if (keyboard.keyDown(37)) {
			velocityX = -VELOCITY;
		}
		//UP
		if (keyboard.keyDown(38)) {
			velocityY = -VELOCITY;			
		}
		// RIGHT
		if (keyboard.keyDown(39)) {
			velocityX += VELOCITY;
		}
		// DOWN
		if (keyboard.keyDown(40)) {
			velocityY += VELOCITY;			
		}
		double deltaX = actual_delta_time * 0.001 * velocityX;
		if (checkCollisionWithBarrier(universe, deltaX, 0) == false) {
			this.centerX += deltaX;
			}
		
		double deltaY = actual_delta_time * 0.001 * velocityY;
		if (checkCollisionWithBarrier(universe, 0, deltaY) == false) {
			this.centerY += deltaY;

		}
		
	}

	private boolean checkCollisionWithBarrier(Universe sprites, double deltaX, double deltaY) {

		boolean colliding = false;

		for (DisplayableSprite sprite : sprites.getSprites()) {
			if (sprite instanceof BarrierSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
						sprite.getMinX(),sprite.getMinY(), 
						sprite.getMaxX(), sprite.getMaxY())) {
					colliding = true;
					break;					
				}
			}
		}		
		return colliding;		
	}
	//these need to be here
	@Override
	public void moveX(double pixelsPerSecond) {
		
	}

	@Override
	public void moveY(double pixelsPerSecond) {
		
	}
	

	}
