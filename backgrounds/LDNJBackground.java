
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

import javax.imageio.ImageIO;

public class LDNJBackground implements Background {
	 private Image wood;
	 private Image stone;
	 private Image path;
	 private Image water;
	 private Image grass;

    protected static int TILE_WIDTH = 50;
    protected static int TILE_HEIGHT = 50;

    
    private int maxCols = 0;
    private int maxRows = 0;
    int[][] newMap;
    int[][] map;
    
    public LDNJBackground() {
    	try {
    		this.wood = ImageIO.read(new File("res/castle-tiles/wood_tile.jpg"));
    		this.stone = ImageIO.read(new File("res/castle-tiles/stone_tile.jpg"));
    		this.water = ImageIO.read(new File("res/castle-tiles/water_tile.jpg"));
    		this.path = ImageIO.read(new File("res/castle-tiles/lightstonepath.png"));    		
    		this.grass = ImageIO.read(new File("res/castle-tiles/grass.jpg"));
    		
    		createRandomMap();
    		this.map = this.newMap;
    		    		
    	}
    	catch (IOException e) {
    		//System.out.println(e.toString());
    	}
    	
    }
    
    private void createRandomMap() {
    	//map settings
    	int maxR = 50;
    	int maxC = 50;
    	
    	int wallNum = 2;
    	int floorNum = 1;
    	int pathNum = 4;
    	
    	
    	//dont change these
    	int prevPoint = 0;
    	boolean border = true;
    	int same = 2;
    	int nextPoint;
    	int getPoint;
    	int get1Point;
    	int forceSame = 1;
    	this.maxRows = maxR - 1;
    	this.maxCols = maxC - 1;
    	boolean[][] pathMap = new boolean[maxR][maxC];
    	boolean[][] borderMap = new boolean[maxR][maxC];
    	newMap = new int[maxR][maxC];
    	
    	//creates the path
    	//i was exhausted at 3 am when i made this but it works really well
    	
    	
    	for (int count = 0; count < maxR; count ++) {
    		nextPoint = (int) (Math.random() * (maxC - 4)) + 2;
    		if (same == 2) {
    			
    			same = 0;
    		} else {
    			same = (int) (Math.random() * 2);
    			
    			if (same == 0 || forceSame == 1) {
    				nextPoint = prevPoint;
    				forceSame = 0;
    			} else {
    				forceSame = 1; 
    			}
    		}
    		
    		
    		if (nextPoint > prevPoint) {
    			getPoint = prevPoint;
    			get1Point = nextPoint;
    		} else if (nextPoint < prevPoint && prevPoint - nextPoint > 1) {
    			getPoint = nextPoint;
    			get1Point = prevPoint;
    		}
    		else {
    			getPoint = nextPoint;
    			get1Point = prevPoint;
    		}
    		
    		
    		
    		for (int count1 = getPoint; count1 <= get1Point; count1 ++) {
    			pathMap[count][count1] = true;
    		}
    		
    		prevPoint = nextPoint;
    		
    	}
    	
    	//final for loop for starting row
    	
    	for (int firstNum = 0; firstNum < pathMap[1].length; firstNum ++) {
    		System.out.println("checking0 (this should work)");
    		if (pathMap[1][firstNum] == true) {
    			break;
    		} else {
    			pathMap[1][firstNum] = true;
    		}
    	}
    	System.out.println("checking1 (this should work)");
    	//draws the border 
    	
    	if (border == true) {
    		
    		for (int rows = 0; rows > maxR; rows ++) {
    			System.out.println("checking2 doesnt work");
    			
    			for (int columns = 0; columns > maxC; columns ++) {
    				System.out.println("checking3 doesnt work");
    				if (rows == 0 || columns == 0|| rows == maxR-1|| columns == maxC-1) {
    					borderMap[rows][columns] = true;
    				}
    			}
    		}
    	}
    	

    	//main loop to put it all together
    	for (int rows1 = 0; rows1 > maxR; rows1 ++) {
    		System.out.println("checking4 doesnt work");
			for (int columns1 = 0; columns1 > maxC; columns1 ++) {
				System.out.println("checking5 doesnt work");
				if (borderMap[rows1][columns1] == true) {
					newMap[rows1][columns1] = wallNum;
				} else if(pathMap[rows1][columns1] == true) {
					newMap[rows1][columns1] = pathNum;
					
				} else {
					newMap[rows1][columns1] = floorNum;
				}
			}
		}
    	
    	
    	
    }
    
	
	public Tile getTile(int col, int row) {
		
		Image image = null;
		
		if (row < 0 || row > maxRows || col < 0 || col > maxCols) {
			image = null;
		}
		else if (map[row][col] == 1) {
			image = path;
		}
		else if (map[row][col] == 2) {
			image = stone;
		}
		else if (map[row][col] == 3) {
			image = water;
		}
		else if (map[row][col] == 4) {
			image = grass;
		}
		else if (map[row][col] == 5) {
			image = wood;
		}
		else {
			image = null;
		}
			
		int x = (col * TILE_WIDTH);
		int y = (row * TILE_HEIGHT);
		
		Tile newTile = new Tile(image, x, y, TILE_WIDTH, TILE_HEIGHT, false);
		
		return newTile;
	}
	
	public int getHorizontal(int x) {
		//which tile is x sitting at?
		return 0;
	}

	public int getCol(int x) {
		//which col is x sitting at?
		int col = 0;
		if (TILE_WIDTH != 0) {
			col = (x / TILE_WIDTH);
			if (x < 0) {
				return col - 1;
			}
			else {
				return col;
			}
		}
		else {
			return 0;
		}
	}
	
	public int getRow(int y) {
		//which row is y sitting at?
		int row = 0;
		
		if (TILE_HEIGHT != 0) {
			row = (y / TILE_HEIGHT);
			if (y < 0) {
				return row - 1;
			}
			else {
				return row;
			}
		}
		else {
			return 0;
		}
	}
	
	public ArrayList<DisplayableSprite> getBarriers() {
		ArrayList<DisplayableSprite> barriers = new ArrayList<DisplayableSprite>();
		for (int col = 0; col < map[0].length; col++) {
			for (int row = 0; row < map.length; row++) {
				if (map[row][col] == 1) {
					barriers.add(new BarrierSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH, (row + 1) * TILE_HEIGHT, false));
				}
			}
		}
		return barriers;
	}
	
}
