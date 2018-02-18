package NewAutoShell;

import edu.wpi.first.wpilibj.DriverStation;

public class GameData {
	
	boolean closeIsLeft;
	boolean scaleIsLeft;
	boolean farIsLeft;
	
	public GameData () {
		
		String message;
		
		while ((message = DriverStation.getInstance().getGameSpecificMessage()) == null 
				&& message.length() != 3) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		closeIsLeft = message.substring (0, 1).equals("L");
		scaleIsLeft = message.substring (1, 2).equals("L");
		farIsLeft = message.substring (2).equals("L");
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
