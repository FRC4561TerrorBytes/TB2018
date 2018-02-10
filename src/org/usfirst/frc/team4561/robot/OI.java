/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4561.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4561.robot.commands.*;

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
	private static JoystickButton torqueButton = new JoystickButton(rightStick, RobotMap.TRANSMISSION_TORQUE_BUTTON);
	
	public OI () {
		
		intakeButton.whileHeld(new IntakeIn());
		releaseButton.whileHeld(new IntakeRelease());
		
		speedButton.whenPressed(new SpeedGear());
		torqueButton.whenPressed(new TorqueGear());
		
		controllerIntake.whileHeld(new IntakeIn());
		controllerIntakeLeft.whileHeld(new IntakeLeft());
		controllerIntakeRight.whileHeld(new IntakeRight());
	}
	public double getRightStickY() {
		
		double rightStickY = rightStick.getY(); 
		
		return rightStickY;
		
	}
	
	public double getRightStickX() {
		
		double rightStickX = rightStick.getX(); 
		
		return rightStickX;
		
	}
	
	public double getLeftStickY() {
		
		double leftStickY = leftStick.getY(); 
		
		return leftStickY;
		
	}
	
	public double getLeftStickX() {
		
		double leftStickX = leftStick.getX(); 
		
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
