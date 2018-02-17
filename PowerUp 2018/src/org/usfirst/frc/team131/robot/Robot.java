/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 CHAOS. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by CHAOS. The code   */
/* must be accompanied by the CHAOS BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team131.robot;

import Commands.ClearPrefs;
import NewAutoShell.AutoBuilder;
import SystemComponents.CubeManipulator;
import SystemComponents.DriveBase;
import SystemComponents.LinearLift;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	
	AutoBuilder autoBuilder;
	
	DriveBase drive;
	CubeManipulator cubeManipulator;
	LinearLift lift;
	ControllerManager cm;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		SmartDashboard.putData("Clear Prefernces: ", new ClearPrefs());
		SmartDashboard.updateValues();
		drive = new DriveBase ();
		cm = new ControllerManager();
		lift = new LinearLift();
		cubeManipulator = new CubeManipulator(lift);
	}

	/**
	 * builds auto builder
	 */
	@Override
	public void autonomousInit() {
		CommandGroup autoSequence = new CommandGroup();
		autoBuilder = new AutoBuilder (drive, lift, cubeManipulator);
		autoBuilder.createCommandGroup(autoSequence);

		Scheduler.getInstance().add(autoSequence);

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * Does the controls for the drive
	 */
	private void driveControls () {
		
		drive.setSpeed(cm.driver.getLeftY(), cm.driver.getLeftY());
		
		boolean doGearShift = cm.driver.buttonPressed(Controller.LEFT_BUMPER) 
				|| cm.driver.buttonPressed(Controller.RIGHT_BUMPER);
		
		drive.gearShift(doGearShift);
		
	}
	
	/**
	 * Does the lift controls (if auto pickup isn't in progress)
	 */
	private void liftControls () {
			
		if (cm.operator.getDPad() != Controller.DPadDirection.NONE) {
			
			lift.setToPosition(cm.operator.getDPad());
			lift.MoveToPosition();
			
		} else {
			
			lift.setSpeed(cm.operator.getLeftY());
			
		}
			
	}
	
	/**
	 * Does the cube manipulator controls (if auto pickup isn't in progress)
	 */
	private void cubeControls () {
		
		if (cm.operator.buttonPressed(Controller.LEFT_BUMPER)) {
			
			cubeManipulator.extend();
			
		} else if (cm.operator.buttonPressed(Controller.LEFT_TRIGGER)) {
			
			cubeManipulator.retract();
			
		}
		
		if (cm.operator.buttonPressed(Controller.RIGHT_BUMPER)) {
			
			cubeManipulator.output();
			
		} else if (cm.operator.buttonPressed(Controller.RIGHT_TRIGGER)) {
			
			cubeManipulator.intake();
			
		} else {
			
			cubeManipulator.stopSpeed();
			
		}
		
	}
	
	/**
	 * intakes and picks up cube if A is pressed
	 */
	private void autonomaticCubeIntake () {
		
			
		if (cubeManipulator.cubeIn()) {
			
			lift.setToIntakePosition();
			cubeManipulator.stopSpeed();
			
			if (lift.liftIsStopped()) {
				cubeManipulator.retract();
			} else {
				lift.MoveToPosition();
			}
			
		} else {
			
			if (cubeManipulator.isExtended() ) {
				lift.setToFloorPosition();
			} else {
				cubeManipulator.extend();
				if (!cubeManipulator.isExtended()) {
					lift.setToIntakePosition();
				}
			}
			
			if (lift.liftIsStopped()) {
				cubeManipulator.intake();
			} else {
				lift.MoveToPosition();
			}
		}
			
		
	}
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		driveControls();
		
		if (cm.operator.buttonPressed(Controller.RIGHT_B)) {
			
			autonomaticCubeIntake();
			
		} else {
		
			liftControls ();
			
			cubeControls ();
			
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
	}
	
	/**
	 * take a guess my guy
	 */
	@Override
	public void robotPeriodic() {

	}
	
	/**
	 * Schedule scheduler runs for table commands
	 */
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.updateValues();
	}
}
