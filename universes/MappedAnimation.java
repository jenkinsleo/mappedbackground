
public class MappedAnimation implements Animation {

	private static int universeCount = 0;
	
	public static int getUniverseCount() {
		return universeCount;
	}

	public static void setUniverseCount(int count) {
		MappedAnimation.universeCount = count;
	}

	public Universe getNextUniverse() {

		universeCount++;
		
		if (universeCount == 1) {
			return new MappedUniverse();
		}
		else {
			return null;
		}

	}
	
}
