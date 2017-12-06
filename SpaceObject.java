public class SpaceObject {
	// Value indicating relationship to player. 0 = neutral, 1 = ally, 2 = enemy
	private int allyType;
	// Value indicating what type of object it is.  0 = Barrier, 1 = Ship, 2 = Bullet
	private int objectType = 3;
	public int size = 0;
	public int xPos = 0;
	public int yPos = 0;
	private final int BARRIER_SIZE = 32;
	private final int SHIP_SIZE = 32;
	private final int BULLET_SIZE = 4;
	
	public void setSize(int ObjectType) {
		if(objectType == 0){
			size = BARRIER_SIZE;
		}
		else if(objectType == 1){
			size = SHIP_SIZE;
		}
		else if (objectType == 2){
			size = BULLET_SIZE;
		}
		else{
			throw new IndexOutOfBoundsException("Value must be 0, 1, or 2!");
		}
	}
	public void setPosition(int x, int y){
		xPos = x;
		yPos = y;
	}
	
	private void initialSetup(int submittedAllyType, int submittedObjectType, int x, int y) {
		if(submittedAllyType >= 0 && submittedAllyType <= 2){
			allyType = submittedAllyType;
			objectType = submittedObjectType;
		}
		else{
			throw new IndexOutOfBoundsException("Value must be 0, 1, or 2!");
		}
		if(submittedObjectType >= 0 && submittedObjectType <= 2){
			objectType = submittedObjectType;
		}
		else{
			throw new IndexOutOfBoundsException("Value must be 0, 1, or 2!");
		}
		setPosition(x, y);
		setSize(submittedObjectType);
	}
	
	public SpaceObject(int submittedAllyType, int submittedObjectType, int x, int y) {
		initialSetup(submittedAllyType, submittedObjectType, x, y);
	}
}
