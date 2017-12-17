/**
 * Makes the spaceship for use in SpaceGame object
 * @author J. Capuzzo (lead) and R. King (partner)
 *
 */
public class SpaceObject {
	// Value indicating relationship to player. 0 = neutral, 1 = ally, 2 = enemy
	private int allyType;
	// Value indicating what type of object it is.  0 = Barrier, 1 = Ship, 2 = Bullet
	private int objectType = 3;
	public int size = 0;
	public int xPos = 0;
	public int yPos = 0;
	private final int BARRIER_SIZE = 64;
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
	public void setPosition(int xPosition, int yPosition){
		xPos = xPosition;
		yPos = yPosition;
	}
	
	private void initialSetup(int xPosition, int yPosition) {
		if(allyType >= 0 && allyType <= 2){
		}
		else{
			throw new IndexOutOfBoundsException("Value must be 0, 1, or 2!");
		}
		if(objectType >= 0 && objectType <= 2){
		}
		else{
			throw new IndexOutOfBoundsException("Value must be 0, 1, or 2!");
		}
		setPosition(xPosition, yPosition);
		setSize(objectType);
	}
	
	public SpaceObject(int submittedAllyType, int submittedObjectType, int xPosition, int yPosition) {
		allyType = submittedAllyType;
		objectType = submittedObjectType;
		initialSetup(xPosition, yPosition);
	}
}
