package SystemComponents;

import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CubeManipulator {
	
	DoubleSolenoid pusher1;
	DoubleSolenoid pusher2;
	
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
	
		pusher1 = new DoubleSolenoid(PortConstants.CUBE_PUSHER_LEFT_A, PortConstants.CUBE_PUSHER_LEFT_B);
		pusher2 = new DoubleSolenoid(PortConstants.CUBE_PUSHER_RIGHT_A, PortConstants.CUBE_PUSHER_RIGHT_B);
	
		cubeDetector = new DigitalInput(PortConstants.CUBE_DETECTOR_SENSOR);
		
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
			pusher1.set(Value.kForward);
			pusher2.set(Value.kForward);
		}
	}
	
	/**
	 * retracts claw only if it is above the intake position
	 */
	public void retract () {
		if (lift.liftPosition() >= LinearLift.INTAKE_POSITION - LinearLift.DEADBAND) {
			pusher1.set(Value.kReverse);
			pusher2.set(Value.kReverse);
		}
	}
	
	public boolean cubeIn() {
		return cubeDetector.get();
		
	}
	
	public boolean isExtended () {
		return pusher1.get() == Value.kForward;
	}
	
	public void putInfo () {
		
		SmartDashboard.putBoolean("Claw is extended: ", isExtended());
		SmartDashboard.putBoolean("Intake is running: ", motor1.get() == SPEED);
		SmartDashboard.putBoolean("Output is running: ", motor1.get() == -SPEED);
		
	}
	
}
