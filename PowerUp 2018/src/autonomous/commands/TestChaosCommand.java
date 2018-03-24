package autonomous.commands;

import autonomous.builder.ChaosCommand;

public class TestChaosCommand extends ChaosCommand {

	public static final String NAME = "Test";
	
	public TestChaosCommand(int argsLength) {
		super(argsLength, NAME);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
