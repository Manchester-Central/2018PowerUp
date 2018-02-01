package NewAutoShell;

import edu.wpi.first.wpilibj.command.Command;

public abstract class ChaosCommand extends Command {
	
	protected String[] args;
	private final int argsLength;
	
	public void setArgs (String[] args) {
		this.args = args;
		System.out.println(String.join(" ", args));
	}
	
	public int getArgsLength () {
		return argsLength;
	}
	
	public ChaosCommand(int argsLength) {
        this.argsLength = argsLength;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }
    
    
    

}
