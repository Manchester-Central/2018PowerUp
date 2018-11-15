package system.components;

import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CubeManipulator {
	
	DoubleSolenoid extender;
	
	DoubleSolenoid pincher1;
	DoubleSolenoid pincher2;
	
	Victor motor1;
	Victor motor2;
	
	// broken beam sensor
	DigitalInput cubeDetector;
	
	private static final double FAST_SPEED = 1.0;
	private static final double SLOW_SPEED = 0.3;
	
	public static final double MIN_INTAKE_SPEED = 0.25;
	
	private long time;
	private boolean cubeInByCurrent;
	private final long waitTime = 1000;
	
	private PowerDistributionPanel pdp;
	
	public CubeManipulator(PowerDistributionPanel pdp) {
		
		this.pdp = pdp;
		
		motor1 = new Victor(PortConstants.ROLLER_CLAW_LEFT);
		motor2 = new Victor(PortConstants.ROLLER_CLAW_RIGHT);
		
		extender = new DoubleSolenoid(PortConstants.CUBE_PUSHER_A, PortConstants.CUBE_PUSHER_B);
		
		pincher1 = new DoubleSolenoid (PortConstants.CUBE_PINCHER_A, PortConstants.CUBE_PINCHER_B);
		pincher2 = new DoubleSolenoid (PortConstants.CUBE_UNPINCHER_A, PortConstants.CUBE_UNPINCHER_B);
	
		cubeDetector = new DigitalInput(PortConstants.CUBE_DETECTOR_SENSOR);
		
		cubeInByCurrent = false;
		time = System.currentTimeMillis();
		
		motor2.setInverted(true);
	}
	/*
	public void h() {
		motor1.get();
		motor1.set(speed);
	}
	
	public void fullSpeed() {
		motor1.set(1.0);
		motor2.set(1.0);
	}
		
	public void fullBack() {
		motor1.set(-1.0);
		motor2.set(-1.0);
	}
	
	public void variableSpeed(double speed) {
		
//		if (speed > 1.0 || speed < -1.0) {
//			System.out.println("The wack");
//		} else {
//			motor1.set(speed);
//			motor2.set(speed);
//		}
		
		if ( speed > 1.0 ) {
			speed = 1.0;
			
		} else if (speed < -1.0) {
			speed = -1.0;
			
		}
			motor1.set(speed);		
			motor2.set(speed);
	
	}
		
	
	public void printSpeed () {
		
		System.out.println("Motor 1 speed: " + motor1.get());
		
		System.out.println("Motor 2 speed: " + motor2.get());
	
	}
	
	
	public void incrementSpeed () {

		double speed1 = motor1.get();
		double speed2 = motor2.get();
		
		for (int i = 0; i >= 10; i++) {
			speed1 = (speed1 + 0.1);
			speed2 = (speed2 + 0.1);
		}
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void setFlywheels (double value) {
		motor1.set(value);
		motor2.set(value);
	}
	
	public void intake() {
		motor1.set(-FAST_SPEED * 0.8);
		motor2.set(-FAST_SPEED * 0.8);
//		if (cubeInSensor()) {
//			pinch();
//		}
	}
	
	public void slowOutput() {
		motor1.set(SLOW_SPEED);
		motor2.set(SLOW_SPEED);
		
		relax();
			
	}
	
	public void fastOutput() {
		motor1.set(FAST_SPEED);
		motor2.set(FAST_SPEED);
		
		relax();
			
	}
	
	public double getFlywheelSpeed () {
		
		return motor1.get();
		
	}
	
	public void stopSpeed () {
		motor1.set(0.0);
		motor2.set(0.0);
	}
	
	/**
	 * extends claw only if it is above the intake position
	 */
	public void extend () {
		extender.set(Value.kForward);
	}
	
	/**
	 * retracts claw only if it is above the intake position
	 */
	public void retract () {
		extender.set(Value.kReverse);
	}
	
	public void pinch () {
		pincher1.set(Value.kForward);
		pincher2.set(Value.kReverse);
	}
	
	public void release () {
		pincher1.set(Value.kReverse);
		pincher2.set(Value.kForward);
	}
	
	public void relax () {
		pincher1.set(Value.kReverse);
		pincher2.set(Value.kReverse);
	}
	

	/**
	 * The raw get is flipped, so we return the inverse
	 * @return - Whether a cube is in the claw
	 */
	public boolean cubeInSensor() {
		
		return !cubeDetector.get();
		
	}
	
	public boolean cubeInCurrent () {
		
		return cubeInByCurrent;
		
	}
	
	public boolean isPinched() {
		return pincher1.get() == Value.kForward && pincher2.get() == Value.kReverse;
		
	}
	
	public boolean isReleased () {
		return pincher1.get() == Value.kReverse && pincher2.get() == Value.kForward;
	}
	
	public boolean isExtended () {
		return extender.get().equals(Value.kForward);
	}
	
	public boolean isRetracted () {
		return extender.get().equals(Value.kReverse);
	}
	
	public void checkPower () {
		
		if ((pdp.getCurrent(4)
				+ pdp.getCurrent(11)) / 2 < 20) {
			
			time = System.currentTimeMillis();
			
		}
		
		if (System.currentTimeMillis() - time >= waitTime) {
			
			cubeInByCurrent = true;
			
		}
		
	}
	
	
	public void putInfo () {
		
		SmartDashboard.putBoolean("Claw is extended: ", isExtended());
		SmartDashboard.putNumber("Flywheel Speed: ", motor1.get());
		
		SmartDashboard.putBoolean("Cube is in: ", cubeInSensor());
		
		
	}

	public boolean isRelaxed() {
		return pincher1.get() == Value.kReverse && pincher2.get() == Value.kReverse;
	}
	
}
