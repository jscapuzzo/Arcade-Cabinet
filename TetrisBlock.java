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

    /**
     * Parameterized constructor. Makes a Tetris block from input.
     * @param coordinates
     * @param type
     */
    public TetrisBlock(int[][] coordinates, String type)
    {
        this.coordinates = coordinates;
        blockType = type;
    }
    
    /**
     * Default constructor. Creates a null block.
     */
    public TetrisBlock()
    {
        createNullBlock();
    }

    public String getBlockType() 
    {
        return blockType;
    }

    public static TetrisBlock createRandomBlock()
    {
        int x = (int) Math.random() * 7 + 1;
        
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
    
    public static TetrisBlock rotateLeft(TetrisBlock t) 
    {
        if(t.getBlockType() == "squareBlocK") 
        {
            return t;
        }
        else
        {
        	int i = 0;
        	
        	while(i < 4) //each array index has to be changed
        	{
                t.setXCoordinates(i, i);
                t.setYCoordinates(i, i);
                i++;
            }
        	
        	return t;
        }
    }

    public static TetrisBlock rotateRight(TetrisBlock t) 
    {
        if(t.getBlockType() == "squareBlock") 
        {
            return t;
        }
        else
        {
        	int i = 0;
        	
        	while(i < 4) //each array index has to be changed
        	{
                t.setXCoordinates(i, i);
                t.setYCoordinates(i, i);
                i++;
            }
        	
        	return t;
        }
    }
    /*
     * The following mutator methods are to assist us in keeping our methods static. 
     * The logic is that in our game we will be calling rotatemethods with a certain block,
     * and we will not have access to mutate that block's variables for security reasons.
     */
    
    private void setXCoordinates(int row, int var)
    {
    	coordinates[row][0] = var; 
    }
    
    private void setYCoordinates(int row, int var)
    {
    	coordinates[row][1] = var;
    }
    
    /*
     * Getters can be public, and will assist us in boundary checking
     */
    
    public int getXCoordinates(int row)
    {
    	return coordinates[row][0];
    }
    
    public int getYCoordinates(int row)
    {
    	return coordinates[row][1];
    }
    
    private static TetrisBlock createNullBlock()
	{
		int[][] coordinates = new int[][] 
										{ 
											{0, 0}, 
											{0, 0}, 
											{0, 0}, 
											{0, 0} 
										};
										
		String name = "nullBlock";
		return new TetrisBlock(coordinates, name);
	}
	
	private static TetrisBlock createZBlock()
	{
		int[][] coordinates = new int[][] 
										{ 
											{0, -1}, 
											{0, 0}, 
											{-1, 0}, 
											{-1, 1} 
										};
		
		String name = "zBlock";
		return new TetrisBlock(coordinates, name);
	}
	
	private static TetrisBlock createSBlock()
	{
		int[][] coordinates = new int[][] 
										{ 
											{0, -1}, 
											{0, 0}, 
											{1, 0}, 
											{1, 1} 
										};
		
		String name = "sBlock";
		return new TetrisBlock(coordinates, name);
	}
	
	public static TetrisBlock createLineBlock()
	{
		int[][] coordinates = new int[][] 
										{
											{0, -1}, 
											{0, 0}, 
											{0, 1}, 
											{0, 2} 
										};
		
		String name = "lineBlock";
		return new TetrisBlock(coordinates, name);
	}
	
	public static TetrisBlock createTBlock()
	{
		int[][] coordinates = new int[][] 
										{ 
											{-1, 0}, 
											{0, 0}, 
											{1, 0}, 
											{0, 1} 
										};
		String name = "tBlock";
		return new TetrisBlock(coordinates, name);
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
		return new TetrisBlock(coordinates, name);
	}
	
	private static TetrisBlock createLBlock()
	{
		int[][] coordinates = new int[][] 
										{
											{-1, -1}, 
											{0, -1}, 
											{0, 0}, 
											{0, 1} 
										};
										
		String name = "lBlock";
		return new TetrisBlock(coordinates, name);
	}
	
	public static TetrisBlock createMirrorLBlock()
	{
		int[][] coordinates = new int[][] 
										{
											{1, -1}, 
											{0, -1}, 
											{0, 0}, 
											{0, 1} 
										};
										
		String name = "mirrorLBlock";
		return new TetrisBlock(coordinates, name);
	}
}
