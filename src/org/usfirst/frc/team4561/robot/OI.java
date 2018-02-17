/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4561.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

import org.usfirst.frc.team4561.robot.commands.*;
import org.usfirst.frc.team4561.robot.triggers.ToggleArmPIDTrigger;
import org.usfirst.frc.team4561.robot.triggers.ToggleDriveTrainPIDTrigger;
import org.usfirst.frc.team4561.robot.triggers.ToggleElevatorPIDTrigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private static Joystick leftStick = new Joystick (RobotMap.LEFT_JOYSTICK_PORT);
	private static Joystick rightStick = new Joystick (RobotMap.RIGHT_JOYSTICK_PORT);
	private static Joystick controller = new Joystick (RobotMap.CONTROLLER_PORT);
	
	private static JoystickButton controllerIntake = new JoystickButton(controller, RobotMap.CONTROLLER_INTAKE);
	private static JoystickButton controllerIntakeLeft = new JoystickButton(controller, RobotMap.CONTROLLER_LEFT_INTAKE);
	private static JoystickButton controllerIntakeRight = new JoystickButton(controller, RobotMap.CONTROLLER_RIGHT_INTAKE);
	
	private static JoystickButton intakeButton = new JoystickButton (rightStick, RobotMap.INTAKE_BUTTON);
	private static JoystickButton releaseButton = new JoystickButton (leftStick, RobotMap.RELEASE_BUTTON);
	
	private static JoystickButton speedButton = new JoystickButton(leftStick, RobotMap.TRANSMISSION_SPEED_BUTTON);
	private static JoystickButton torqueButton = new JoystickButton(leftStick, RobotMap.TRANSMISSION_TORQUE_BUTTON);
	
	private static Trigger toggleArmPID = new ToggleArmPIDTrigger();
	private static Trigger toggleElevatorPID = new ToggleElevatorPIDTrigger();
	private static Trigger toggleDriveTrainPID = new ToggleDriveTrainPIDTrigger();
	
	public OI () {
		
		intakeButton.whileHeld(new IntakeIn());
		releaseButton.whileHeld(new IntakeRelease());
		
		speedButton.whenPressed(new SpeedGear());
		torqueButton.whenPressed(new TorqueGear());
		
		controllerIntake.whileHeld(new IntakeIn());
		controllerIntakeLeft.whileHeld(new IntakeLeft());
		controllerIntakeRight.whileHeld(new IntakeRight());
		
		toggleArmPID.whenActive(new ToggleArmPID());
		toggleElevatorPID.whenActive(new ToggleElevatorPID());
		toggleDriveTrainPID.whenActive(new ToggleDriveTrainPID());
	}
	public double getRightStickY() {
		
		double rightStickY = rightStick.getY(); 
		
		if (Math.abs(rightStick.getMagnitude()) < RobotMap.RIGHT_JOYSTICK_DEAD_ZONE) {
			rightStickY = 0;
		}
		
		// Reductions - joystick reduction reduces velocity from given joystick direction
		if (rightStickY > 0) {
			rightStickY = (rightStickY - RobotMap.RIGHT_JOYSTICK_REDUCTION) * (1/(1-RobotMap.LEFT_JOYSTICK_REDUCTION));
			if (rightStickY < 0) {
				rightStickY = 0;
			}
		} else if (rightStickY < 0) {
			rightStickY = (rightStickY + RobotMap.RIGHT_JOYSTICK_REDUCTION) * (1/(1-RobotMap.LEFT_JOYSTICK_REDUCTION));
			if (rightStickY > 0) 
			{
				rightStickY = 0;
			}
		}
		
		return rightStickY;
		
	}
	
	public double getRightStickX() {
		
		double rightStickX = rightStick.getX(); 
		
		// Dead zone management
				if (Math.abs(rightStick.getMagnitude()) < RobotMap.RIGHT_JOYSTICK_DEAD_ZONE) {
					rightStickX = 0;
				}
				
				// Reductions
				if (rightStickX > 0) {
					rightStickX = (rightStickX - RobotMap.RIGHT_JOYSTICK_REDUCTION) * (1/(1-RobotMap.LEFT_JOYSTICK_REDUCTION));
					if (rightStickX < 0) {
						rightStickX = 0;
					}
				} else if (rightStickX < 0) {
					rightStickX = (rightStickX + RobotMap.RIGHT_JOYSTICK_REDUCTION) * (1/(1-RobotMap.LEFT_JOYSTICK_REDUCTION));
					if (rightStickX > 0) {
						rightStickX = 0;
					}
				}
		
		return rightStickX;
		
	}
	
	public double getLeftStickY() {
		
		double leftStickY = leftStick.getY(); 
		
		// Dead zone management
				if (Math.abs(leftStick.getMagnitude()) < RobotMap.LEFT_JOYSTICK_DEAD_ZONE) {
					leftStickY = 0;
				}
				
				// Reductions
				if (leftStickY > 0) {
					leftStickY = (leftStickY - RobotMap.LEFT_JOYSTICK_REDUCTION) * (1/(1-RobotMap.LEFT_JOYSTICK_REDUCTION));
					if(leftStickY < 0) {
						leftStickY = 0;
					}
				} else if (leftStickY < 0) {
					leftStickY = (leftStickY + RobotMap.LEFT_JOYSTICK_REDUCTION) * (1/(1-RobotMap.LEFT_JOYSTICK_REDUCTION));
					if (leftStickY > 0) {
						leftStickY = 0;
					}
				}
		
		return leftStickY;
		
	}
	
	public double getLeftStickX() {
		
		double leftStickX = leftStick.getX(); 
		
		// Dead zone management
				if (Math.abs(leftStick.getMagnitude()) < RobotMap.LEFT_JOYSTICK_DEAD_ZONE) {
					leftStickX = 0;
				}
				
				// Reductions
				if (leftStickX > 0) {
					leftStickX = (leftStickX - RobotMap.LEFT_JOYSTICK_REDUCTION) * (1/(1-RobotMap.LEFT_JOYSTICK_REDUCTION));
					if(leftStickX < 0){
						leftStickX = 0;
					}
				} else if (leftStickX < 0) {
					leftStickX = (leftStickX + RobotMap.LEFT_JOYSTICK_REDUCTION) * (1/(1-RobotMap.LEFT_JOYSTICK_REDUCTION));
					if(leftStickX > 0) {
						leftStickX = 0;
					}
				}
		
		return leftStickX;
		
	}
	
	public double getControllerAxis(int axis){
		return controller.getRawAxis(axis);
	}
	
	public double getControllerLeftX(){
		return controller.getRawAxis(0);
	}
	
	public double getControllerLeftY(){
		return controller.getRawAxis(1);
	}
	
	public boolean getControllerLTrigger(){
		return (controller.getRawAxis(2)==1);
	}
	
	public boolean getControllerRTrigger(){
		return (controller.getRawAxis(3)==1);
	}
	
	public double getControllerRightX(){
		return controller.getRawAxis(4);
	}
	
	public double getControllerRightY(){
		return controller.getRawAxis(5);
	}
	
	public boolean getControllerButton(int button){
		return controller.getRawButton(button);
	}
	
	public boolean getLeftButton(int button){
		return leftStick.getRawButton(button);
	}
	
	public int getControllerPOV(){
		return controller.getPOV();
	}

	public boolean getRightButton(int button){
		return rightStick.getRawButton(button);
	}

}
