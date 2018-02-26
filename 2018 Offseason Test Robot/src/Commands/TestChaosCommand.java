package Commands;

import NewAutoShell.ChaosCommand;

public class TestChaosCommand extends ChaosCommand {

	public static final String NAME = "Test";
	
	public TestChaosCommand(int argsLength) {
		super(argsLength);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
