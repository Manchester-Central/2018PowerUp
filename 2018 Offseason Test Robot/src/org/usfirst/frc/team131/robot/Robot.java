package org.usfirst.frc.team131.robot;

import NewAutoShell.AutoBuilder;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	DriveBase drive;
	ControllerManager cm;
	Compressor compressor;
	TestVictors testVictors;
	AnalogPotentiometer testPot;
	
	//Manipulator claw; 
	//Manipulator causes weird glitch while deploying
	
	AutoBuilder autoBuilder;
		
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		testPot = new AnalogPotentiometer (0, 84.0, 0.0);
		drive = new DriveBase();
		cm = new ControllerManager();
		compressor = new Compressor();
		SmartDashboard.putBoolean("Print Values", false);
		
		drive.resetEncoders();
		
	//	claw = new Manipulator();
		testVictors = new TestVictors ();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		drive.resetEncoders();
		
		CommandGroup autoSequence = new CommandGroup();
		autoBuilder = new AutoBuilder (drive);
		autoBuilder.createCommandGroup(autoSequence);

		Scheduler.getInstance().add(autoSequence);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	
	@Override
	public void autonomousPeriodic() {
		
		Scheduler.getInstance().run();

//		System.out.println("Left Encoder Current: " + drive.getLeftTalonCurrent() + 
//				"\tRight Encoder Current :" + drive.getRightTalonCurrent());

		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
//		if (cm.operator.buttonPressed(Controller.DOWN_A_ABXY))
//		{
//			drive.gearShift();
//		}
		
//		if (cm.driver.buttonPressed(Controller.LEFT_TRIGGER) || cm.driver.buttonPressed(Controller.RIGHT_TRIGGER)) {
//			drive.gearShift(true);
//		
//		} else {
//		
//			drive.gearShift(false);
//	
//		}	
//			
//		drive.setSpeed(-cm.driver.getLeftY() * 0.7, -cm.driver.getRightY() * 0.7);
//		
//		System.out.println(cm.driver.getRightY());
		
		//testVictors.setSpeed(cm.operator.getLeftY());
		
		// Claw on raft setup
//		testVictors.six.set(cm.operator.getLeftY());
//		testVictors.seven.set(-cm.operator.getRightY());
		
//		if(cm.operator.buttonPressed(Controller.LEFT_BUMPER)) {
//			claw.extend();
//			}
//		else if(cm.operator.buttonPressed(Controller.LEFT_TRIGGER)) {
//			claw.retract();
//		}
//		
//		if(cm.operator.buttonPressed(Controller.RIGHT_BUMPER)) {
//			claw.release();
//			}
//		else if(cm.operator.buttonPressed(Controller.RIGHT_TRIGGER)) {
//			claw.pinch();
//		}
//		else {
//			claw.relax();
//		}
		
		}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

	}
	
	@Override
	public void disabledPeriodic() {	
		
		//System.out.println(testPot.get());
		
//		System.out.println("Left Encoder: " + DriveBase.ticksToInches(drive.getLeftTalonEncoderValue())
//		+ " right Encoder: " + DriveBase.ticksToInches(drive.getRightTalonEncoderValue()));
//		
		//System.out.println("Left Value :" + drive.getLeftTalonEncoderValue() + "Robot Right Value :" + drive.getRightTalonEncoderValue());
		
		
		
		//System.out.print(drive.get);
	}
	
	@Override
	public void robotPeriodic () {
		if (SmartDashboard.getBoolean("Print Values", false)) {
			
			System.out.println("Left Value :" + drive.getLeftTalonEncoderValue() + "Robot Right Value :" + drive.getRightTalonEncoderValue());
		}
	}
	
}


