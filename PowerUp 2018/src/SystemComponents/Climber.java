package SystemComponents;

import org.usfirst.frc.team131.robot.PortConstants;

import edu.wpi.first.wpilibj.Victor;

public class Climber {

	Victor climber;
	
	private final double SPEED = 0.5;
	
	public Climber () {
		climber = new Victor  (PortConstants.CLIMBER);
	}
	
	public void retract () {
		
		climber.setSpeed(-SPEED);
		
	}
	
	public void extend () {
		
		climber.setSpeed (SPEED);
		
	}
	
}
