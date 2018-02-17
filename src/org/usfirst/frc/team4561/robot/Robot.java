/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4561.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4561.robot.automodes.*;
import org.usfirst.frc.team4561.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	public static final DriveTrainPID driveTrain = new DriveTrainPID();
	public static OI oi;
	public static final ElevatorPID elevator = new ElevatorPID();
	public static final ArmPID arm = new ArmPID();
	public static final Intake intake = new Intake();

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	//public static Elevator elevator = new Elevator();
	public static Transmission transmission = new Transmission();
	//public static Arm arm = new Arm(); // non-PID arm
	
	public static boolean switchFMSSideRight; // true if right, false if left
	public static boolean scaleFMSSideRight; // true if right, false if left

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		//m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		chooser.addDefault("Go To Line", new AutoDriveToLine());
		chooser.addObject("Go To Line (From Center)", new AutoDriveToLineCenter());
		chooser.addObject("Score Scale (From Center)", new AutoScaleCenterPosition());
		chooser.addObject("Score Scale (From Left)", new AutoScaleLeftPosition());
		chooser.addObject("Score Scale (From Right)", new AutoScaleRightPosition());
		chooser.addObject("Score Switch (From Center)", new AutoSwitchCenterPosition());
		chooser.addObject("Score Switch (From Left)", new AutoSwitchLeftPosition());
		chooser.addObject("Score Switch (From Right)", new AutoSwitchRightPosition());
		SmartDashboard.putData("Auto mode", chooser);
		System.out.println("Things are not on fire");
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		//arm.resetEncoder();
	}
	
	public void robotPeriodic(){
		SmartDashboard.putNumber("ArmPID Encoder Position", Robot.arm.getEncoderPosition());
    	SmartDashboard.putNumber("ArmPID Encoder Velocity", Robot.arm.getEncoderVelocity());
    	SmartDashboard.putNumber("ArmPID Goal Position", Robot.arm.getGoal());
    	SmartDashboard.putNumber("Elevator Position", Robot.elevator.getElevatorPos());
    	SmartDashboard.putNumber("Elevator Motor One Voltage", Robot.elevator.motorOneVoltage());
    	SmartDashboard.putNumber("Elevator Motor Two Voltage", Robot.elevator.motorTwoVoltage());
    	SmartDashboard.putNumber("Elevator Goal", Robot.elevator.getGoal());
    	SmartDashboard.putBoolean("Elevator Limit Switch", Robot.elevator.limitSwitch());
    	SmartDashboard.putBoolean("SmartDashboard Works", true);
    	SmartDashboard.putString("Transmission State", Robot.transmission.getTransMode());
    	//SmartDashboard.putNumber("Controller POV", oi.getControllerPOV());
    	if (Robot.arm.getRevSwitch()){
			Robot.arm.setEncoderPos(1120);
		}
    	if (RobotMap.ARM_PID){
    		SmartDashboard.putString("DB/String 3", "Arm PID: ON");
    	}
    	else {
    		SmartDashboard.putString("DB/String 3", "!!Arm PID: OFF!!");
    	}
    	if (RobotMap.ELEVATOR_PID){
    		SmartDashboard.putString("DB/String 2", "Elv PID: ON");
    	}
    	else {
    		SmartDashboard.putString("DB/String 2", "!!Elv PID: OFF!!");
    	}
    	if (RobotMap.DRIVETRAIN_PID) {
    		SmartDashboard.putString("DB/String 1", "Drv PID: ON");
    	}
    	else {
    		SmartDashboard.putString("DB/String 1", "!!Drv PID: OFF!!");
    	}
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();


		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
