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

    /**
     * Copy constructor for testing rotations
     * @param block
     */
    public TetrisBlock(final TetrisBlock block)
    {
    	int arr[][] = arr = block.getCoordinateArr();
    	coordinates = new int[4][2];
    	
    	for(int i = 0; i < 4; i++)
    	{
    		coordinates[i][0] = arr[i][0];
    		coordinates[i][1] = arr[i][1];
    	}
    	
    	this.blockType = block.getBlockType();
    }
    
    public String getBlockType() 
    {
        return blockType;
    }

    public static TetrisBlock createRandomBlock()
    {
        int x = (int) (Math.random() * 7) + 1;
        
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
    
    public void rotateLeft() 
    {
        if(blockType == "squareBlocK") 
        {
            return;
        }
        else
        {
        	/*
       		 * Transpose
       		 * Reverse each row
       		 */
        	int[][] arr = coordinates;
        	 
        	
        	arr = transpose(reverseRows(arr));
        	coordinates = arr;
        }
    }
    
    /*
     * The following mutator methods are to assist us in keeping our methods static. 
     * The logic is that in our game we will be calling rotatemethods with a certain block,
     * and we will not have access to mutate that block's variables for security reasons.
     */
    
    
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
    
    public int[][] getCoordinateArr()
    {
    	return coordinates;
    }
    
    public int getMinYCoord() 
    {
    	int min = coordinates[0][1];
      
    	for (int i = 1; i < 4; i++) 
    	{
    		min = Math.min(min, coordinates[i][1]);
    	}
      
    	return min;
    }
    
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
	
	private static TetrisBlock createLineBlock()
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
	
	private static TetrisBlock createMirrorLBlock()
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
	
	private static int[][] transpose(int[][] arr) 
	{
        if (arr.length > 0) 
        {
            for (int i = 0; i < 2; i++) //row length
            {
                for (int j = 0; j < 2; j++) //column length
                {
                	int temp = arr[i][j];
                    arr[i][j] = arr[j][i];
                    arr[j][i] = temp;
                }
            }
        }
        
        return arr;
    }
	
	private static int[][] reverseRows(int[][] arr)
	{
		  for(int row = 0; row < arr.length; row++)
		  {
		        for(int col = 0; col < arr[row].length / 2; col++) 
		        {
		            int temp = arr[row][col] * -1;
		            arr[row][col] = arr[row][arr[row].length - col - 1];
		            arr[row][arr[row].length - col - 1] = temp;
		        }
		  }
		
		  return arr;
	}
	
	public static void main(String[] args)
	{
		
	}
}
