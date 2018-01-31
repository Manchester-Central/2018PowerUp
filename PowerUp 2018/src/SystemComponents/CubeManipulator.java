package SystemComponents;

import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Victor;

public class CubeManipulator {
	
	DoubleSolenoid pusher1;
	DoubleSolenoid pusher2;
	
	Victor motor1;
	Victor motor2;
	
	DigitalInput cubeDetector;
	
	private final double SPEED = 1.0;
	
	public CubeManipulator() {
		motor1 = new Victor(PortConstants.ROLLER_CLAW_LEFT);
		motor2 = new Victor(PortConstants.ROLLER_CLAW_RIGHT);
	
		pusher1 = new DoubleSolenoid(PortConstants.CUBE_PUSHER_LEFT_A, PortConstants.CUBE_PUSHER_LEFT_B);
		pusher2 = new DoubleSolenoid(PortConstants.CUBE_PUSHER_RIGHT_A, PortConstants.CUBE_PUSHER_RIGHT_B);
	
		cubeDetector = new DigitalInput(PortConstants.CUBE_DETECTOR_SENSOR);
	}

	public void intake() {
		motor1.set(SPEED);
		motor2.set(-SPEED);
	}
	
	public void output() {
		motor1.set(-SPEED);
		motor2.set(SPEED);
	}
	
	public void extend () {
		pusher1.set(Value.kForward);
		pusher2.set(Value.kForward);
	}
	
	public void retract () {
		pusher1.set(Value.kReverse);
		pusher2.set(Value.kReverse);
	}
	
	public boolean cubeIn() {
		return cubeDetector.get();
	}
	
}
