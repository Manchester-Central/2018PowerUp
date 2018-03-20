public class Main {
	
	public static final double DEADBAND_INCHES = 2.0; // the acceptable error 
	
	private static final double MAX_UP_SPEED = 0.9;
	private static final double MIN_UP_SPEED = 0.0;
	
	private static final double MAX_DOWN_SPEED = 0.3;
	private static final double MIN_DOWN_SPEED = 0.0;
	
	// denominator of the proportional set
	private static final double PROPORTIONAL_DISTANCE = 12;
	
	private final static long changeRate = 0;
	private final static double maxAcceleration = 0.5;

	static double currentSet= 0D;
	
	static long lastTimeUpdate = 0;
	
	public static void main(String[] args) {
		
		double position = 0D;
		double targetPosition = 140D;
		
		while (Math.abs(targetPosition - position) > DEADBAND_INCHES) {
			
			position += getProportionalSet (targetPosition, position);
			System.out.printf(", pos=%.3f\n", position);
			
		}
		
		
		
		
	}
	
	public static double getProportionalSet (double targetPosition, double currentPosition) {
		
		// if at the correct position, return 0 to void NaN errors
		if (targetPosition == currentPosition) {
			currentSet = 0;
			return 0;
		}
		
		// the slope of x value distance away from target distance
		double proportionalSet = (targetPosition- currentPosition) / PROPORTIONAL_DISTANCE;
		
		double maxSpeed;
		double minSpeed;
		double signModifier;
		
		// keeps proportions within 1 and -1
		if (proportionalSet > 1.0) {
			proportionalSet = 1.0;
		} else if (proportionalSet < -1.0) {
			proportionalSet = -1.0;
		}
		
		if (proportionalSet > 0.0) {
			maxSpeed = MAX_UP_SPEED;
			minSpeed = MIN_UP_SPEED;
			signModifier = 1.0;
		} else {
			maxSpeed = MAX_DOWN_SPEED;
			minSpeed = MIN_DOWN_SPEED;
			signModifier = -1.0;
		}
		
		// sets the value of proportional set
		proportionalSet = maxSpeed * proportionalSet;
		
		//makes sure value is within min speed
		if (Math.abs(proportionalSet) < minSpeed) 
			proportionalSet = signModifier * minSpeed;

		
		// if ΔV > max acceleration
		if (proportionalSet - currentSet > maxAcceleration) {
			
			if (lastTimeUpdate + changeRate < System.currentTimeMillis()) {
				
				proportionalSet = currentSet + maxAcceleration;
				
				lastTimeUpdate = System.currentTimeMillis();
			
			} else {
				
				proportionalSet = currentSet;
				
			}
			
		}
			
		System.out.printf("p-set: %.3f\t,position: %.3f\n", proportionalSet);
		
		currentSet = proportionalSet;
		
		return proportionalSet;
		
	}

}
