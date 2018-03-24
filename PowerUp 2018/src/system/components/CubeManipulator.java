package system.components;

import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CubeManipulator {
	
	DoubleSolenoid extender;
	//DoubleSolenoid pincher;
	DoubleSolenoid pincher1;
	DoubleSolenoid pincher2;
	
	Victor motor1;
	Victor motor2;
	
	// broken beam sensor
	DigitalInput cubeDetector;
	
	private static final double SPEED = 1.0;
	private LinearLift lift;
	
	private long time;
	private boolean cubeInByCurrent;
	private final long waitTime = 1000;
	
	private PowerDistributionPanel pdp;
	
	public CubeManipulator(LinearLift lift, PowerDistributionPanel pdp) {
		motor1 = new Victor(PortConstants.ROLLER_CLAW_LEFT);
		motor2 = new Victor(PortConstants.ROLLER_CLAW_RIGHT);
		
		cubeInByCurrent = false;
		
		this.pdp = pdp;
		
		time = System.currentTimeMillis();
		
		this.lift = lift;
	
		extender = new DoubleSolenoid(PortConstants.CUBE_PUSHER_A, PortConstants.CUBE_PUSHER_B);
		
		//pincher = new DoubleSolenoid(PortConstants.CUBE_PINCHER_A, PortConstants.CUBE_PINCHER_B);
		pincher1 = new DoubleSolenoid (PortConstants.CUBE_PINCHER_A, PortConstants.CUBE_PINCHER_B);
		pincher2 = new DoubleSolenoid (PortConstants.CUBE_UNPINCHER_A, PortConstants.CUBE_UNPINCHER_B);
	
		cubeDetector = new DigitalInput(PortConstants.CUBE_DETECTOR_SENSOR);
		
		motor2.setInverted(true);
	}
	
	public void setFlywheels (double value) {
		motor1.set(value);
		motor2.set(value);
	}
	
	public void intake() {
		motor1.set(-SPEED);
		motor2.set(-SPEED);
	}
	
	public void output() {
		motor1.set(SPEED);
		motor2.set(SPEED);
	}
	public void stopSpeed () {
		motor1.set(0.0);
		motor2.set(0.0);
	}
	
	/**
	 * extends claw only if it is above the intake position
	 */
	public void extend () {
		//if (lift.liftPosition() >= LinearLift.EXTEND_POSITION_INCHES - LinearLift.DEADBAND_INCHES) {
			extender.set(Value.kForward);
		//}
	}
	
	/**
	 * retracts claw only if it is above the intake position
	 */
	public void retract () {
		//if (lift.liftPosition() >= LinearLift.EXTEND_POSITION_INCHES - LinearLift.DEADBAND_INCHES) {
			extender.set(Value.kReverse);
		//}
	}
	
	public void pinch () {
		//pincher.set(Value.kForward);
		pincher1.set(Value.kForward);
		pincher2.set(Value.kReverse);
	}
	
	public void release () {
		//pincher.set(Value.kReverse);
		pincher1.set(Value.kReverse);
		pincher2.set(Value.kForward);
	}
	
	public void relax () {
		pincher1.set(Value.kReverse);
		pincher2.set(Value.kReverse);
	}
	

	public boolean cubeInSensor() {
		
		return cubeDetector.get();
		
	}
	
	public boolean cubeInCurrent () {
		
		return cubeInByCurrent;
		
	}
	
	public boolean isPinched() {
		return pincher1.get() == Value.kForward && pincher2.get() == Value.kReverse;
		//return pincher.get().equals(Value.kForward);
		
	}
	
	public boolean isExtended () {
		return extender.get().equals(Value.kForward);
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
//		SmartDashboard.putBoolean("Intake is running: ", motor1.get() == SPEED);
//		SmartDashboard.putBoolean("Output is running: ", motor1.get() == -SPEED);
		SmartDashboard.putNumber("Flywheel Speed: ", motor1.get());
		
		SmartDashboard.putBoolean("Cube is in: ", cubeInCurrent());
		
		
	}

	public boolean isRelaxed() {
		return pincher1.get() == Value.kReverse && pincher2.get() == Value.kReverse;
	}
	
}
