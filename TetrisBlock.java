import java.awt.Color;

/**
 * Creates a Tetris block, using coordinates as the main guidance. 
 * Coordinates are imagined in a x-y or R2 plane. This allows for convenient rotation. 
 * Essentially each block is in its own R2 plane, and the R2 planes intersecting is how they will stack. 
 * 
 * @author Ryan
 */

public class TetrisBlock 
{
	private int[][] coordinates;
	private String blockType; 
	private Color color;
	
    /**
     * Parameterized constructor. Makes a Tetris block from input.
     * @param coordinates
     * @param type
     * @param paint
     */
    public TetrisBlock(int[][] coordinates, String type, Color paint)
    {
        this.coordinates = coordinates;
        blockType = type;
        color = paint;
    }
    
    /**
     * Default constructor. Creates a null block.
     */
    public TetrisBlock()
    {
        createNullBlock();
    }

    /**
     * Copy constructor for testing rotations
     * @param block
     */
    public TetrisBlock(final TetrisBlock block)
    {
    	int arr[][] = block.getCoordinateArr();
    	coordinates = new int[4][2];
    	
    	for(int i = 0; i < 4; i++)
    	{
    		coordinates[i][0] = arr[i][0];
    		coordinates[i][1] = arr[i][1];
    	}
    	
    	this.blockType = block.getBlockType();
    	this.color = block.getBlockColor();
    }
    
    /**
     * Getter for block type
     * @return
     */
    public String getBlockType() 
    {
        return blockType;
    }
    
    /**
     * Getter for block color
     * @return
     */
    public Color getBlockColor()
    {
    	return color;
    }

    /**
     * Make a random block
     * @return
     */
    public static TetrisBlock createRandomBlock()
    {
        int x = (int) Math.floor(Math.random() * 7) + 1;
        
        if(x == 1)
        {
        	return createZBlock();
        }
        
        if(x == 2)
        {
        	return createTBlock();
        }
        
        if(x == 3)
        {
        	return createLBlock();
        }
        
        if(x == 4)
        {
        	return createMirrorLBlock();
        }
        
        if(x == 5)
        {
        	return createLineBlock();
        }
        
        if(x == 6)
        {
        	return createSquareBlock();
        }
        
        if(x == 7)
        {
        	return createSBlock();
        }
        
        return null;
    }
    
    /**
     * Rotate the block 90 degrees CCW.
     */
    public void rotateLeft() 
    {
        if(blockType == "squareBlock") 
        {
            return;
        }
        else
        {	        
        	//transpose, then reverse each row.
        	transpose();
        	reverseRows();
        }
    }
    
    /*
     * Getters can be public, and will assist us in boundary checking
     */
    
    public int[][] getCoordinateArr()
    {
    	return coordinates;
    }
    
    public int getMinYCoord() 
    {
    	int minValue = coordinates[0][1];
    	
        for (int i = 1; i < coordinates.length; i++) 
        {
            if (coordinates[i][1] < minValue) 
            {
                minValue = coordinates[i][1];
            }
        }
      
    	return minValue;
    }
    
    
    /*
     * The following methods create instances of special blocks used in Tetris.
     * Only the null block is public since it can be used to initialize TetrisBlock coordinatesays
     */
    
    public static TetrisBlock createNullBlock()
	{
		int[][] coordinates = new int[][] 
										{ 
											{0, 0}, 
											{0, 0}, 
											{0, 0}, 
											{0, 0} 
										};
										
		String name = "nullBlock";
		Color color = Color.WHITE;
		
		return new TetrisBlock(coordinates, name, color);
	}
	
	private static TetrisBlock createZBlock()
	{
		int[][] coordinates = new int[][] 
										{ 
											{0, 1},
											{1, 1},
											{1, 0},
											{2, 0}
								};
		
		String name = "zBlock";
		Color color = Color.RED;
		
		return new TetrisBlock(coordinates, name, color);
	}
	
	private static TetrisBlock createSBlock()
	{
		int[][] coordinates = new int[][] 
										{ 
											{0, 1},
											{0, 2},
											{1, 0},
											{1, 1}
										};
		
		String name = "sBlock";
		Color color = Color.RED.darker();
		
		return new TetrisBlock(coordinates, name, color);
	}
	
	private static TetrisBlock createLineBlock()
	{
		int[][] coordinates = new int[][] 
										{
											{0, 0},
											{0, 1},
											{0, 2},
											{0, 3}
										};
		
		String name = "lineBlock";
		Color color = Color.MAGENTA.darker();
		
		return new TetrisBlock(coordinates, name, color);
	}
	
	private static TetrisBlock createTBlock()
	{
		int[][] coordinates = new int[][] 
										{ 
											{0, 0},
											{1, 0},
											{1, 1},
											{2, 0}
										};
		String name = "tBlock";
		Color color = Color.GREEN.darker();
		
		return new TetrisBlock(coordinates, name, color);
	}
	
	private static TetrisBlock createSquareBlock()
	{
		int[][] coordinates = new int[][] 
										{
											{0, 0}, 
											{1, 0}, 
											{0, 1}, 
											{1, 1} 
										};
										
		String name = "squareBlock";
		Color color = Color.BLACK;
		
		return new TetrisBlock(coordinates, name, color);
	}
	
	private static TetrisBlock createLBlock()
	{
		int[][] coordinates = new int[][] 
										{
											{0, 0},
											{0, 1},
											{0, 2},
											{1, 0}
										};
										
		String name = "lBlock";
		Color color = Color.DARK_GRAY;
		
		return new TetrisBlock(coordinates, name, color);
	}
	
	private static TetrisBlock createMirrorLBlock()
	{
		int[][] coordinates = new int[][] 
										{
											{0, 0},
											{1, 0},
											{1, 1},
											{1, 2}
										};
										
		String name = "mirrorLBlock";
		Color color = Color.GRAY;
		
		return new TetrisBlock(coordinates, name, color);
	}
	
	private void transpose() 
	{
		for (int i = 0; i < 2; i++) //row length
		{
			for (int j = 0; j < 2; j++) //column length
			{
				int temp = coordinates[i][j];
				coordinates[i][j] = coordinates[j][i];
                coordinates[j][i] = temp;
			}
		}
    }
	
	/*
	 * The following methods are used for matrix operations.
	 * These can probably be reused in another project at some point.
	 */
	
	private void reverseRows()
	{
		for(int row = 0; row < coordinates.length; row++)
		{
			for(int col = 0; col < coordinates[row].length / 2; col++) 
		    {
		        int temp = coordinates[row][col] * -1;
		        coordinates[row][col] = coordinates[row][coordinates[row].length - col - 1];
		        coordinates[row][coordinates[row].length - col - 1] = temp;
		    }
		}	
	}
	
	public static void main(String[] args)
	{
		
	}
}
