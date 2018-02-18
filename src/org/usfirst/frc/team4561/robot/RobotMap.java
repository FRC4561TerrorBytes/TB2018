/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4561.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

//TODO: Remove placeholder values

public class RobotMap {
	
	//Joystick Ports
	public static final int LEFT_JOYSTICK_PORT = 0;
	public static final int RIGHT_JOYSTICK_PORT = 1;
	public static final int CONTROLLER_PORT = 2;
	
	//Buttons
	public static final int RELEASE_BUTTON = 4;
	public static final int INTAKE_BUTTON = 1;
	public static final int INTAKE_FULL_BUTTON = 6;
	
	public static final int INTAKE_POSITION_BUTTON = 2;
	public static final int RELEASE_POSITION_BUTTON = 2;
	
	public static final int CONTROLLER_INTAKE = 2;
	public static final int ARM_BOTTOM_POS = 1;
	public static final int ARM_MIDDLE_POS = 3;
	public static final int ARM_TOP_POS= 4;
	
	public static final int CONTROLLER_LEFT_INTAKE = 5;
	public static final int CONTROLLER_RIGHT_INTAKE = 6;
	
	public static final int ARM_UP_POV = 0;
	public static final int ARM_MIDDLE_POV1 = 90;
	public static final int ARM_MIDDLE_POV2 = 270;
	public static final int ARM_DOWN_POV = 180;
	
	public static final int TRANSMISSION_SPEED_BUTTON = 5;
	public static final int TRANSMISSION_TORQUE_BUTTON = 3;
	
	public static final double RIGHT_JOYSTICK_DEAD_ZONE = 0.25;
	public static final double LEFT_JOYSTICK_DEAD_ZONE = 0.25;
	public static final double RIGHT_JOYSTICK_REDUCTION = 0.25;
	public static final double LEFT_JOYSTICK_REDUCTION = 0.25;
	
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
	
	//Left Motor Ports
	public static final int FRONT_LEFT_MOTOR_PORT = 7;
	public static final int MID_LEFT_MOTOR_PORT = 11;
	public static final int BACK_LEFT_MOTOR_PORT = 12;
	
	//Right Motor Ports
	public static final int FRONT_RIGHT_MOTOR_PORT = 8;
	public static final int MID_RIGHT_MOTOR_PORT = 9;
	public static final int BACK_RIGHT_MOTOR_PORT = 10;
		
	
	//Drive Mode 
	public static final int DRIVE_MODE = 1; // 1 is arcade drive, 0 is tank drive
	
	// Elevator Ports (zeros for placeholders)
	public static final int ELEVATOR_MOTOR_1_PORT = 1;
	public static final int ELEVATOR_MOTOR_2_PORT = 2;	
	
	// Arm ports (zeros for placeholders)
	public static final int ARM_MOTOR_1_PORT = 6;

	//Intake ports
	public static final int INTAKE_LEFT_MOTOR_PORT = 4;
	public static final int INTAKE_RIGHT_MOTOR_PORT = 3;
	
	//Transmission Ports
	public static final int TRANSMISSION_SOLENOID_PORT = 0;
	public static final int TRANSMISSION_SOLENOID_PORT_TWO = 1;
	public static final int TRANSMISSION_SOLENOID_TWO_PORT = 2;
	public static final int TRANSMISSION_SOLENOID_TWO_PORT_TWO = 3;
	public static final int PCM = 13;

	//Toggle PID Buttons
	public static final int TOGGLE_ARM_BUTTON = 9;
	public static final int TOGGLE_ELEVATOR_BUTTON = 10;
	public static final int TOGGLE_DRIVETRAIN_BUTTON_ONE = 11;
	public static final int TOGGLE_DRIVETRAIN_BUTTON_TWO = 9;
	public static final int TOGGLE_DRIVETRAIN_BUTTON_THREE = 7;
	public static final int TOGGLE_PID_BUTTON_ONE = 8;
	public static final int TOGGLE_PID_BUTTON_TWO = 7;
	
	// Debug variables
	public static final boolean MASTER_DEBUG = true;
	public static final boolean DRIVETRAIN_DEBUG = false || MASTER_DEBUG;
	public static final boolean ELEVATOR_DEBUG = true || MASTER_DEBUG;
	public static final boolean TRANSMISSION_DEBUG = false || MASTER_DEBUG;
	public static final boolean ARM_DEBUG = false || MASTER_DEBUG;
	
	//PID variables
	public static boolean MASTER_PID = false;
	public static boolean DRIVETRAIN_PID = false || MASTER_PID;
	public static boolean ELEVATOR_PID = true || MASTER_PID;
	public static boolean ARM_PID = true || MASTER_PID;
	
	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
