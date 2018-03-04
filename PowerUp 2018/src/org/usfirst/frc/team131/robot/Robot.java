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
	
	PowerDistributionPanel pdp;
	
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
		
		pdp = new PowerDistributionPanel();
		
		// TODO Check CAN ID to use
		cubeManipulator = new CubeManipulator(lift, pdp);
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
	 * left and right y-axis for corresponding drive side (currently at 80% power)
	 * 
	 * Holding left or right bumper shifts into high position
	 * 
	 */
	private void driveControls () {
		
		drive.setSpeed(-cm.driver.getLeftY() * 0.9, -cm.driver.getRightY() * 0.9);
		
		boolean doGearShift = cm.driver.buttonPressed(Controller.LEFT_BUMPER) 
				|| cm.driver.buttonPressed(Controller.RIGHT_BUMPER);
		
		drive.shiftLow(!doGearShift);
		
	}
	
	

	/**
	 * Lift controls (manual) (Operator)
	 * 
	 * ABXY moves to preset position (have to hold to move)
	 * A - floor position, B - vault position, X - switch position, Y - top position
	 * 
	 * right y-axis manually moves lift
	 * 
	 */
	private void liftControls () {
			
		if (cm.operator.buttonPressed(Controller.DOWN_A)) {
			
			lift.setTargetPosition(LinearLift.FLOOR_POSITION_INCHES);
			lift.MoveToPosition();
			
		} else if (cm.operator.buttonPressed(Controller.UP_Y)) {
			
			lift.setTargetPosition(LinearLift.HIGH_POSITION_INCHES);
			lift.MoveToPosition();
			
		} else if (cm.operator.buttonPressed(Controller.LEFT_X)) {
			
			lift.setTargetPosition(LinearLift.SWITCH_POSITION_INCHES);
			lift.MoveToPosition();
			
		} else if (cm.operator.buttonPressed(Controller.RIGHT_B)) {
			
			lift.setTargetPosition(LinearLift.VAULT_POSITION_INCHES);
			lift.MoveToPosition();
			
		} else if (cm.operator.buttonPressed(Controller.LEFT_BUMPER)) {
			
			lift.setTargetPosition(LinearLift.PORTAL_POSITION_INCHES);
			lift.MoveToPosition();
			
		} else {
			//if (lift.liftPosition() < 109 || cm.operator.getRightY() > 0) {
				lift.setSpeed(-cm.operator.getRightY() * 0.8);
			//}
			
		}
			
	}
	
	/**
	 * Cube manipulator controls (manual) (operator)
	 * 
	 * Dpad up extends manipulator
	 * Dpad down retracts manipulator
	 * 
	 * right trigger intakes flywheels (full speed)
	 * right bumper outputs flywheels (full speed)
	 * 
	 * Dpad right pinches
	 * Dpad left relaxes
	 * left trigger releases
	 * 
	 * left y-axis manual set of flywheel
	 * 
	 */
	private void cubeControls () {
		
		if (cm.operator.getDPad() == Controller.DPadDirection.UP) {
			
			cubeManipulator.extend();
			
		} else if (cm.operator.getDPad() == Controller.DPadDirection.DOWN) {
			
			cubeManipulator.retract();
			
		}
		
		if (cm.operator.getDPad() == Controller.DPadDirection.LEFT) {
			
			cubeManipulator.relax();
			
		} else if (cm.operator.getDPad() == Controller.DPadDirection.RIGHT) {
			
			cubeManipulator.pinch();
			
		} else if (cm.operator.buttonPressed(cm.operator.LEFT_TRIGGER)) {
			
			cubeManipulator.release();
			
		}
		
		if (cm.operator.buttonPressed(Controller.RIGHT_BUMPER)) {
			
			cubeManipulator.output();
			
		} else if (cm.operator.buttonPressed(Controller.RIGHT_TRIGGER)) {
			
			cubeManipulator.intake();
			
		} else {
			
			cubeManipulator.setFlywheels(-cm.operator.getLeftY());
			
		}
		
	}
	
	/**
	 * Automatic Intake Cubes (Operator) left bumper button
	 * 
	 * Releases the pincher, moves down to intake position, 
	 * intakes & pinches until the cube is in, then lift to a higher position and retracts
	 */
	private void autonomaticCubeIntake () {
		
			
		// if cube is in, move to retract position
		// TODO to be changed to sensor if it gets working
		if (cubeManipulator.cubeInCurrent()) {
			
			cubeManipulator.pinch();
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
				cubeManipulator.relax();
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
	 * This function is called periodically during driver/operator control.
	 */
	@Override
	public void teleopPeriodic() {

		driveControls();
		
//		if (cm.operator.buttonPressed(Controller.LEFT_BUMPER)) {
//			
//			//autonomaticCubeIntake();
//			
//		} else {
		
			liftControls ();
			
			cubeControls ();
			
//		}
		

		SmartDashboard.putNumber("Right Y speed: ", -cm.operator.getRightY());
		
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
		cubeManipulator.checkPower();
		SmartDashboard.updateValues();
		//drive.velocityData();
	} 
	
	/**
	 * Schedule scheduler runs for table commands
	 */
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
}
