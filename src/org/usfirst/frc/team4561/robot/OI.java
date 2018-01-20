/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4561.robot;

import org.usfirst.frc.team4561.robot.commands.ArmIntakePosition;
import org.usfirst.frc.team4561.robot.commands.ArmReleasePosition;

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
	private static JoystickButton IntakePosition = new JoystickButton(leftStick, 5);
	private static JoystickButton ReleasePosition = new JoystickButton(leftStick, 6);
	private static JoystickButton intake = new JoystickButton (rightStick, RobotMap.IntakeButton);
	private static JoystickButton release = new JoystickButton (rightStick, RobotMap.ReleaseButton);
	
	public OI () {
		
		IntakePosition.whenPressed(new ArmIntakePosition());
		ReleasePosition.whenPressed(new ArmReleasePosition());
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
	
	public void matchMode () {
		intake.whileHeld(new Intake());
		release.whileHeld(new IntakeRelease());
	}
}
