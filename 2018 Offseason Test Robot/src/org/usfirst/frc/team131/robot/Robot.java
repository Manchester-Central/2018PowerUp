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
		
		drive.resetEncoders();
		
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
//		CommandGroup autoSequence = new CommandGroup();
//		autoBuilder = new AutoBuilder (drive);
//		autoBuilder.createCommandGroup(autoSequence);
//		drive.turnToAngleRight(90D);
		drive.tankCorrectedDrive(DriveBase.inchesToTicks(12D), -DriveBase.inchesToTicks(12D));
//		Scheduler.getInstance().add(autoSequence);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	
	@Override
	public void autonomousPeriodic() {
		
		Scheduler.getInstance().run();
		
		drive.tankCorrectedDrive(DriveBase.inchesToTicks(12D), -DriveBase.inchesToTicks(12D));
		
		
		//drive.encoderData();
//		System.out.println("Left Encoder Current: " + drive.getLeftTalonCurrent() + 
//				"\tRight Encoder Current :" + drive.getRightTalonCurrent());
	
		//drive.driveToInches(60);
		
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
		
		if (cm.driver.buttonPressed(Controller.LEFT_TRIGGER) || cm.driver.buttonPressed(Controller.RIGHT_TRIGGER)) {
			drive.gearShift(true);
		
		} else {
		
			drive.gearShift(false);
	
		}	
			
		drive.setSpeed(-cm.driver.getLeftY() * 0.7, -cm.driver.getRightY() * 0.7);
		
		System.out.println(cm.driver.getRightY());
		
		//testVictors.setSpeed(cm.operator.getLeftY());
		
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
		
		
		
		//System.out.print(drive.get);
	}
	
}


