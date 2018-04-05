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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4561.robot.commands.*;
import org.usfirst.frc.team4561.robot.triggers.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private static Joystick leftStick = new Joystick (RobotMap.LEFT_JOYSTICK_PORT);
	private static Joystick rightStick = new Joystick (RobotMap.RIGHT_JOYSTICK_PORT);
	private static Joystick controller = new Joystick (RobotMap.CONTROLLER_PORT);
	
	//private static JoystickButton controllerIntakeLeft = new JoystickButton(controller, RobotMap.CONTROLLER_LEFT_INTAKE);
	//private static JoystickButton controllerIntakeRight = new JoystickButton(controller, RobotMap.CONTROLLER_RIGHT_INTAKE);
	
	
	private static JoystickButton speedButton = new JoystickButton(rightStick, RobotMap.TRANSMISSION_SPEED_BUTTON);
	private static JoystickButton torqueButton = new JoystickButton(rightStick, RobotMap.TRANSMISSION_TORQUE_BUTTON);
	
	private static JoystickButton bobClawButton = new JoystickButton(leftStick, RobotMap.BOB_CLAW_BUTTON);
	
	public static Trigger startElevatorRelative;
	public static Trigger toggleArmPID;
	public static Trigger toggleElevatorPID;
	public static Trigger toggleDriveTrainPID;
	public static Trigger stopElevatorRelative;
	public static Trigger stopArmRelative;
	public static Trigger startArmRelative;
	
	public OI () {
		
		
		speedButton.whenPressed(new SpeedGear());
		torqueButton.whenPressed(new TorqueGear());
		
		
		//controllerIntake.whileHeld(new IntakeIn());
		//controllerIntakeLeft.whileHeld(new IntakeLeft());
		//controllerIntakeRight.whileHeld(new IntakeRight());
		
		toggleArmPID = new ToggleArmPIDTrigger();
		toggleElevatorPID = new ToggleElevatorPIDTrigger();
		toggleDriveTrainPID = new ToggleDriveTrainPIDTrigger();
		stopElevatorRelative = new StopElevatorRelative();
		startElevatorRelative = new StartElevatorRelative();
		stopArmRelative = new StopArmRelative();
		startArmRelative = new StartArmRelative();
		
		
	}
	/**
	 * 
	 * @return Y position of the right joystick, with deadzone and reduction applied
	 */
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
	/**
	 * 
	 * @return X Position of right joystick, with deadzone and reduction applied
	 */
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
	/**
	 * 
	 * @return Y Position of left joystick, with deadzone and reduction applied
	 */
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
	/**
	 * 
	 * 
	 * @return X Position of the left joystick, with deadzone and reduction applied
	 */
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
	/**
	 * 
	 * @param axis
	 * @return The position of the requested axis of the controller, with deadzone applied
	 */
	public double getControllerAxis(int axis){
		double axes = controller.getRawAxis(axis);
		if (Math.abs(axes) < RobotMap.CONTROLLER_DEADZONE) axes = 0;
		return axes;
	}
	/**
	 * 
	 * @return X position of the left stick of the controller
	 */
	public double getControllerLeftX(){
		return controller.getRawAxis(0);
	}
	/**
	 * 
	 * @return Y Position of the left stick of the controller
	 */
	public double getControllerLeftY(){
		return controller.getRawAxis(1);
	}
	/**
	 * 
	 * @return Position of the left trigger of the controller
	 */
	public double getControllerLTrigger(){
		return controller.getRawAxis(2);
	}
	/**
	 * 
	 * @return Position of the right trigger of the controller
	 */
	public double getControllerRTrigger(){
		return controller.getRawAxis(3);
	}
	/**
	 * 
	 * @return X Position of the right stick of the controller
	 */
	public double getControllerRightX(){
		return controller.getRawAxis(4);
	}
	/**
	 * 
	 * @return Y position of the right stick of the controller
	 */
	public double getControllerRightY(){
		return controller.getRawAxis(5);
	}
	/**
	 * 
	 * @param button
	 * @return State of the requested button of the controller
	 */
	public boolean getControllerButton(int button){
		return controller.getRawButton(button);
	}
	/**
	 * 
	 * @param button
	 * @return State of the requested button of the left joystick
	 */
	public boolean getLeftButton(int button){
		return leftStick.getRawButton(button);
	}
	/**
	 * 
	 * @return State of the POV of the controller
	 */
	public int getControllerPOV(){
		return controller.getPOV();
	}
	/**
	 * 
	 * @param button
	 * @return State of the requested button of the right joystick
	 */
	public boolean getRightButton(int button){
		return rightStick.getRawButton(button);
	}
	/**
	 * 
	 * @return Value of the second slider on the default dashboard
	 */
	public double getDashboardDelaySlider() {
		return SmartDashboard.getNumber("DB/Slider 1", 0);
	}
	/**
	 * 
	 * @return Value of the fourth slider on the default dashboard
	 */
    public double getDashboardSlider3() {
        return SmartDashboard.getNumber("DB/Slider 3", 0);
    }


	/**
	 * Converts feet to encoder units.
	 * Uses {@value #WHEEL_DIAMETER}" for wheel diameter and {@value #UNITS_PER_REVOLUTION} for encoder units per revolution.
	 * @param feet
	 * @return encoder units
	 */
	public static double ft2Units(double feet) {
		feet *= 12; // inches
		feet /= RobotMap.WHEEL_DIAMETER * Math.PI; // revolutions
		feet *= RobotMap.UNITS_PER_REVOLUTION; // Units
		return feet;
	}

	/**
	 * Converts feet to encoder units.
	 * Uses {@value #WHEEL_DIAMETER}" for wheel diameter and {@value #UNITS_PER_REVOLUTION} for encoder units per revolution.
	 * @param feet
	 * @return encoder units
	 */
	public static double units2Ft(double units) {
		units /= RobotMap.UNITS_PER_REVOLUTION; // revolutions
		units *= RobotMap.WHEEL_DIAMETER * Math.PI; // inches
		units /= 12; // feet
		
		return units;
	}
	
	/**
	 * Converts feet per second to encoder units per 100 milliseconds.
	 * Uses {@value #WHEEL_DIAMETER}" for wheel diameter and {@value #UNITS_PER_REVOLUTION} for encoder units per revolution.
	 * @param fps feet per second
	 * @return encoder units per 100 milliseconds
	 */
	public static double fps2UnitsPerRev(double fps) {
		fps /= 10; // ft/100ms
		fps *= 12; // in/100ms
		fps /= RobotMap.WHEEL_DIAMETER * Math.PI; // revolutions/100ms
		fps *= RobotMap.UNITS_PER_REVOLUTION; // Units/100ms
		return fps;
	}
    
}
