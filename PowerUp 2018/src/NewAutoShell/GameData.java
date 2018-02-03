package NewAutoShell;

import edu.wpi.first.wpilibj.DriverStation;

public class GameData {
	
	boolean closeIsLeft;
	boolean scaleIsLeft;
	boolean farIsLeft;
	
	public GameData () {
		closeIsLeft = DriverStation.getInstance().getGameSpecificMessage().substring (0, 1).equals("L");
		scaleIsLeft = DriverStation.getInstance().getGameSpecificMessage().substring (0, 1).equals("L");
		farIsLeft = DriverStation.getInstance().getGameSpecificMessage().substring (0, 1).equals("L");
	}
	
	public boolean closeSwitchIsLeft () {
		return closeIsLeft;
	}
	
	public boolean scaleIsLeft () {
		return scaleIsLeft;
	}
	
	public boolean farSwitchIsLeft () {
		return farIsLeft;
	}
	
}
