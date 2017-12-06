public class SpaceObject {
	// Value indicating relationship to player. 0 = neutral, 1 = ally, 2 = enemy
	private int allyType;
	// Value indicating what type of object it is.  0 = Barrier, 1 = Ship, 2 = Bullet
	private int objectType = 3;
	private int size = 0;
	private int xPos = 0;
	private int yPos = 0;
	private final int BARRIER_SIZE = 32;
	private final int SHIP_SIZE = 32;
	private final int BULLET_SIZE = 4;
	private boolean sizeKnown = false;
	
	public void moveObject(){
		actualMoveObject();
	}
	
	private void actualMoveObject(){
		if(objectType == 0){
			xPos += BARRIER_SIZE;
			yPos += BARRIER_SIZE;
		}
		else if(objectType == 1){
			xPos += SHIP_SIZE;
			yPos += SHIP_SIZE;
		}
		else if (objectType == 2){
			xPos += BULLET_SIZE;
			yPos += BULLET_SIZE;
		}
		else{
			throw new IndexOutOfBoundsException("Value must be 0, 1, or 2!");
		}
	}
	
	public int returnSize() {
		if(objectType == 0){
			return BARRIER_SIZE;
		}
		else if(objectType == 1){
			return SHIP_SIZE;
		}
		else if (objectType == 2){
			return BULLET_SIZE;
		}
		else{
			throw new IndexOutOfBoundsException("Value must be 0, 1, or 2!");
		}
	}
	private void setPosition(){
		if(objectType == 0){
			
		}
		else if(objectType == 1){
			
		}
		else if (objectType == 2){
			
		}
		else{
			throw new IndexOutOfBoundsException("Value must be 0, 1, or 2!");
		}
	}
	private void initialSetup(int submittedAllyType, int submittedObjectType) {
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
		setPosition();
	}
	
	public SpaceObject(int submittedAllyType, int submittedObjectType) {
		initialSetup(submittedAllyType, submittedObjectType);
	}
}
