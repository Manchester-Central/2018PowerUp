package SystemComponents;

import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CubeManipulator {
	
	DoubleSolenoid extender;
	DoubleSolenoid pincher;
	
	Victor motor1;
	Victor motor2;
	
	// broken beam sensor
	DigitalInput cubeDetector;
	
	private static final double SPEED = 1.0;
	private LinearLift lift;
	
	public CubeManipulator(LinearLift lift) {
		motor1 = new Victor(PortConstants.ROLLER_CLAW_LEFT);
		motor2 = new Victor(PortConstants.ROLLER_CLAW_RIGHT);
		
		this.lift = lift;
	
		extender = new DoubleSolenoid(PortConstants.CUBE_PUSHER_A, PortConstants.CUBE_PUSHER_B);
		pincher = new DoubleSolenoid(PortConstants.CUBE_PINCHER_A, PortConstants.CUBE_PINCHER_B);
	
		//cubeDetector = new DigitalInput(PortConstants.CUBE_DETECTOR_SENSOR);
		
		motor2.setInverted(true);
	}
	
	public void intake() {
		motor1.set(SPEED);
		motor2.set(SPEED);
	}
	
	public void output() {
		motor1.set(-SPEED);
		motor2.set(-SPEED);
	}
	public void stopSpeed () {
		motor1.set(0.0);
		motor2.set(0.0);
	}
	
	/**
	 * extends claw only if it is above the intake position
	 */
	public void extend () {
		if (lift.liftPosition() >= LinearLift.INTAKE_POSITION - LinearLift.DEADBAND) {
			extender.set(Value.kForward);
		}
	}
	
	/**
	 * retracts claw only if it is above the intake position
	 */
	public void retract () {
		if (lift.liftPosition() >= LinearLift.INTAKE_POSITION - LinearLift.DEADBAND) {
			extender.set(Value.kReverse);
		}
	}
	
	@Deprecated
	public void testPusher (Value TrumpSC) {
		
		extender.set(TrumpSC);
		
	}
	
	@Deprecated
	public void testPincher (Value TrumpSC) {
		
		pincher.set(TrumpSC);
		
	}
	
	public void pinch (Value value) {
		pincher.set(value);
	}
	
	public void setFlywheels (double value) {
		motor1.set(value);
		motor2.set(value);
	}
	
	public boolean cubeIn() {
		return cubeDetector.get();
		
	}
	
	public boolean isPinched() {
		return pincher.get() == Value.kForward;
		
	}
	
	public boolean isExtended () {
		return extender.get() == Value.kForward;
	}
	
	public void putInfo () {
		
		SmartDashboard.putBoolean("Claw is extended: ", isExtended());
		SmartDashboard.putBoolean("Intake is running: ", motor1.get() == SPEED);
		SmartDashboard.putBoolean("Output is running: ", motor1.get() == -SPEED);
		
	}
	
}
