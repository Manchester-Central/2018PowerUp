/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 CHAOS. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by CHAOS. The code   */
/* must be accompanied by the CHAOS BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team131.robot;

import autonomous.builder.AutoBuilder;
import autonomous.commands.ClearPrefs;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import system.components.CubeManipulator;
import system.components.DriveBase;
import system.components.LinearLift;

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
	
	double lastRightYInput;
	
	
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
		
		lastRightYInput = cm.operator.getRightY();
		
		cubeManipulator = new CubeManipulator(pdp);
	}

	/**
	 * builds auto builder
	 */
	@Override
	public void autonomousInit() {
		
		CommandGroup autoSequence = new CommandGroup();
		autoBuilder = new AutoBuilder (drive, lift, cubeManipulator);
		autoBuilder.createCommandGroup(autoSequence);
		drive.shiftLow(true);
		Scheduler.getInstance().add(autoSequence);

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		lift.moveToPosition();
	}

	/**
	 * This function is called periodically during driver/operator control.
	 */
	@Override
	public void teleopPeriodic() {
	
		driveControls();
		
		liftControls ();
			
		cubeControls ();
			
		
	
		
		SmartDashboard.putNumber("Right Y speed: ", -cm.operator.getRightY());
	
		//cm.updateControllerState(); Might be implemented after review
		
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
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
		
		if (cm.driver.getDPad() == Controller.DPadDirection.UP) {
			drive.setSpeed(0.65, 0.65);
		} else if (cm.driver.getDPad() == Controller.DPadDirection.DOWN) {
			drive.setSpeed(-0.65, -0.65);
		} else {
			//drive.setSpeed(-cm.driver.getLeftY(), -cm.driver.getRightY());
			
			if (!cm.driver.buttonPressed(Controller.RIGHT_TRIGGER)) {
				
				double proportionalDriveRestriction = (LinearLift.HIGH_POSITION_INCHES - Math.abs(lift.liftPosition()) * .7)
						/ LinearLift.HIGH_POSITION_INCHES;
				
				if (proportionalDriveRestriction < 0)
					proportionalDriveRestriction = 0.0;
				
				drive.setSpeed (proportionalDriveRestriction  * -cm.driver.getLeftY() * 0.9,
						proportionalDriveRestriction * -cm.driver.getRightY() * 0.9);
			} else
				drive.setSpeed(-cm.driver.getLeftY() * 0.9, -cm.driver.getRightY() * 0.9);
		}
		
		
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
			
		if (lastRightYInput == cm.operator.getRightY() && Math.abs(cm.operator.getRightY()) < 0.01) {
			
			if (cm.operator.buttonPressed(Controller.DOWN_A)) {
				
				lift.setTargetPosition(LinearLift.FLOOR_POSITION_INCHES);
				
			} else if (cm.operator.buttonPressed(Controller.UP_Y)) {
				
				lift.setTargetPosition(LinearLift.HIGH_POSITION_INCHES);
				
			} else if (cm.operator.buttonPressed(Controller.LEFT_X)) {
				
				lift.setTargetPosition(LinearLift.SWITCH_POSITION_INCHES);
				
			} else if (cm.operator.buttonPressed(Controller.RIGHT_B)) {
				
				lift.setTargetPosition(LinearLift.VAULT_POSITION_INCHES);
				
			} else {
				
				lift.setTargetPosition(lift.getChaosPot());
				
			}
			
			lift.moveToPosition();
			
		} else {
			
			lift.setSpeed(-cm.operator.getRightY() * 0.4);
			
			
			
		}
		
		lastRightYInput = cm.operator.getRightY();
			
	}
	
	/**
	 * Cube manipulator controls (manual) (operator)
	 * 
	 * Dpad up extends manipulator
	 * Dpad down retracts manipulator
	 * 
	 * right trigger intakes flywheels (full speed)
	 * right bumper outputs flywheels (full speed)
	 * left bumper outputs flywheels (30% power)
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
		
		
		// if you are outputting, the grabber relaxes
		if (Math.abs(cubeManipulator.getFlywheelSpeed()) > 0.26) {
			
			cubeManipulator.relax();
			
		// if you are intaking, and the push sensor goes green, the grabber pinches
		} else if (cubeManipulator.cubeInSensor()){
			
			cubeManipulator.pinch();
			
		// else, use manual controls
		} else {
			
			if (cm.operator.getDPad() == Controller.DPadDirection.LEFT) {
				
				cubeManipulator.relax();
				
			} else if (cm.operator.getDPad() == Controller.DPadDirection.RIGHT ) {
				
				cubeManipulator.pinch();
				
			} else if (cm.operator.buttonPressed(Controller.LEFT_TRIGGER)) {
				
				cubeManipulator.release();
				
			} 
		}
		
		SmartDashboard.putBoolean("isPinched: ", cubeManipulator.isPinched());	
				
		if (cm.operator.buttonPressed(Controller.LEFT_BUMPER)) {
			
			cubeManipulator.slowOutput();
			
		} else if (cm.operator.buttonPressed(Controller.RIGHT_TRIGGER)) {
			
			cubeManipulator.intake();
			
		} else if (cm.operator.buttonPressed(Controller.RIGHT_BUMPER)) { 
			
			cubeManipulator.fastOutput();
	
		} else {
			
			if (Math.abs(cm.operator.getLeftY()) > cubeManipulator.MIN_INTAKE_SPEED) {
				cubeManipulator.setFlywheels(-cm.operator.getLeftY());
			} else {
				cubeManipulator.setFlywheels(-cubeManipulator.MIN_INTAKE_SPEED);
			}
			
			
		}
		
	}
	
	
	
	private void dashboardInfo () {
		
		drive.putInfo();
		lift.putInfo();
//		cubeManipulator.putInfo();
		SmartDashboard.updateValues();
		
	}
	
	/**
	 * called each periodic state
	 */
	@Override
	public void robotPeriodic() {
		dashboardInfo();
		SmartDashboard.putBoolean("Cube In", cubeManipulator.cubeInSensor());
		SmartDashboard.updateValues();
	} 
	
	/**
	 * Schedule scheduler runs for table commands
	 */
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	/**
	 * Automatic Intake Cubes (Operator) left bumper button
	 * 
	 * Releases the pincher, moves down to intake position, 
	 * intakes & pinches until the cube is in, then lift to a higher position and retracts
	 */
	@Deprecated
	private void autonomaticCubeIntake () {
		
			
		// if cube is not in, intake
		if (!cubeManipulator.cubeInSensor()) {
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
				lift.moveToPosition();
			}
			
			
			
		} else {
			
			cubeManipulator.pinch();
			lift.setToIntakePosition();
			cubeManipulator.stopSpeed();
			
			// only retracts when lift is in position
			if (lift.liftIsStopped()) {
				cubeManipulator.retract();
			} else {
				lift.moveToPosition();
			}
			
			
		}
			
		
	}
}
