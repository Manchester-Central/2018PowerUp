package NewAutoShell;

import edu.wpi.first.wpilibj.command.Command;

public abstract class ChaosCommand extends Command {
	
	protected String[] args;
	
	public void setArgs (String[] args) {
		this.args = args;
		System.out.println(String.join(" ", args));
	}
	
	public ChaosCommand() {
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }
    
    
    

}
