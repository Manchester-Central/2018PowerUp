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
import edu.wpi.first.wpilibj.PowerDistributionPanel;
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
	//PowerDistributionPanel pdp;
	
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
		//pdp = new PowerDistributionPanel();
		cubeManipulator = new CubeManipulator(lift/*, pdp*/);
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
	 * Controls for the drive (Driver)
	 * 
	 * left and right y-axis for corresponding drive side
	 * 
	 * Holding left or right bumper shifts into high position
	 * 
	 */
	private void driveControls () {
		
		drive.setSpeed(cm.driver.getLeftY(), cm.driver.getRightY());
		
		boolean doGearShift = cm.driver.buttonPressed(Controller.LEFT_BUMPER) 
				|| cm.driver.buttonPressed(Controller.RIGHT_BUMPER);
		
		drive.shiftLow(!doGearShift);
		
	}
	
	

	/**
	 * Lift controls (manual) (Operator)
	 * 
	 * D-Pad moves to preset position (have to hold to move) (see lift for more info)
	 * 
	 * left y-axis manually moves lift
	 * 
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
	 * Cube manipulator controls (manual) (operator)
	 * 
	 * left bumper extends manipulator
	 * left trigger retracts manipulator
	 * 
	 * A pinches
	 * B releases
	 * 
	 * Right bumper outputs flywheels
	 * Right trigger intakes flywheels
	 * 
	 */
	private void cubeControls () {
		
		if (cm.operator.buttonPressed(Controller.LEFT_BUMPER)) {
			
			cubeManipulator.extend();
			
		} else if (cm.operator.buttonPressed(Controller.LEFT_TRIGGER)) {
			
			cubeManipulator.retract();
			
		}
		
		if (cm.operator.buttonPressed(Controller.DOWN_A)) {
			
			cubeManipulator.pinch();
			
		} else if (cm.operator.buttonPressed(Controller.RIGHT_B)) {
			
			cubeManipulator.release();
			
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
	 * Automatic Intake Cubes (Operator)
	 * 
	 * 
	 */
	private void autonomaticCubeIntake () {
		
			
		// if cube is in, move to retract position
		// TODO to be changed to sensor if it gets working
		if (cubeManipulator.cubeInCurrent()) {
			
			lift.setToIntakePosition();
			cubeManipulator.stopSpeed();
			
			// only retracts when lift is in position
			if (lift.liftIsStopped()) {
				cubeManipulator.retract();
			} else {
				lift.MoveToPosition();
			}
			
		} else {
			cubeManipulator.release();
			// if the roller claw has successfully extended, set to floor
			if (cubeManipulator.isExtended() ) {
				lift.setToFloorPosition();
			} else {
				// else, extend
				cubeManipulator.extend();
				
				// if it didn't extend, go to intake to extend, else, go to floor
				if (!cubeManipulator.isExtended()) {
					lift.setToIntakePosition();
				} else {
					lift.setToFloorPosition();
				}
			}
			
			// intakes cube if in position
			if (lift.liftIsStopped()) {
				cubeManipulator.pinch();
				cubeManipulator.intake();
			} else {
				lift.MoveToPosition();
			}
		}
			
		
	}
	
	/**
	 * 
	 * controls with no automation and direct controller control, all operator
	 * 
	 * A extends the manipulator
	 * B retracts the manipulator
	 * 
	 * left bumper spins the flywheels outward
	 * left trigger spins the flywheels inwards
	 * 
	 * X makes the manipulator pinch
	 * Y makes the manipulator release
	 * 
	 * left y-axis manually moves the lift
	 * 
	 * right y-axis manually sets the flywheels
	 * 
	 */
	public void directTestControls () {
		
		if (cm.operator.buttonPressed(Controller.DOWN_A)) {
			cubeManipulator.extend();
		} else if (cm.operator.buttonPressed(Controller.RIGHT_B)) {
			cubeManipulator.retract();
		}
		
		if(cm.operator.buttonPressed(Controller.LEFT_BUMPER)) {
			cubeManipulator.output();
		} else if (cm.operator.buttonPressed(Controller.LEFT_TRIGGER)){
			cubeManipulator.intake();   
		} else {
			cubeManipulator.stopSpeed();
		}
		
		if (cm.operator.buttonPressed(Controller.LEFT_X)) {
			cubeManipulator.pinch();
		} else if (cm.operator.buttonPressed(Controller.UP_Y)) {
			cubeManipulator.release();
		}
		
		cubeManipulator.setFlywheels(cm.operator.getRightY());
			
		lift.setSpeed(cm.operator.getLeftY());
		
	}
	
	private void dashboardInfo () {
		
		drive.putInfo();
		lift.putInfo();
		cubeManipulator.putInfo();
		SmartDashboard.updateValues();
		
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
		
		// testDirectControls
		
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
	}
	
	/**
	 * called each periodic state
	 */
	@Override
	public void robotPeriodic() {
		dashboardInfo();
		//cubeManipulator.checkPower();
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
