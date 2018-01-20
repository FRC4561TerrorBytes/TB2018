/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.RobotMap;
import org.usfirst.frc.team4561.robot.commands.ArcadeDrive;
import org.usfirst.frc.team4561.robot.commands.TankDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	private WPI_TalonSRX frontRight;
	private WPI_TalonSRX frontLeft;
	
	private WPI_TalonSRX midRight;
	private WPI_TalonSRX midLeft;
	
	private WPI_TalonSRX rearRight;
	private WPI_TalonSRX rearLeft;
	
	private ControlMode follower = com.ctre.phoenix.motorcontrol.ControlMode.Follower;
	
	private DifferentialDrive robotDrive; 
	
	public DriveTrain() {
		frontRight = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_MOTOR_PORT);
		frontLeft = new WPI_TalonSRX(RobotMap.FRONT_LEFT_MOTOR_PORT);
		
		midRight = new WPI_TalonSRX(RobotMap.MID_RIGHT_MOTOR_PORT);
		
		// Sets other motors as slaves to masters FrontLeft/Right, set doesn't set power, it sets a slave
		midRight.set(follower, RobotMap.FRONT_RIGHT_MOTOR_PORT);
		
		rearRight = new WPI_TalonSRX(RobotMap.BACK_RIGHT_MOTOR_PORT);
		rearRight.set(follower, RobotMap.FRONT_RIGHT_MOTOR_PORT);
		
		midLeft = new WPI_TalonSRX(RobotMap.MID_LEFT_MOTOR_PORT);
		midLeft.set(follower, RobotMap.FRONT_LEFT_MOTOR_PORT);
		
		rearLeft = new WPI_TalonSRX(RobotMap.BACK_LEFT_MOTOR_PORT);
		rearLeft.set(follower, RobotMap.FRONT_LEFT_MOTOR_PORT);
		
		// Puts motors into RobotDrive class
		robotDrive = new DifferentialDrive(frontLeft, frontRight);

	}
	
	public void tankDrive(double leftPower, double rightPower) {
		robotDrive.tankDrive(leftPower, rightPower);
		if (RobotMap.DRIVETRAIN_DEBUG) {
			System.out.println("[Subsystem] Driving in tank mode, left: " + leftPower + ", right: " + rightPower);
		}
	}
	
	public void arcadeDrive (double leftPower, double rightPower) {
		robotDrive.arcadeDrive(leftPower, rightPower);
		if (RobotMap.DRIVETRAIN_DEBUG) {
			System.out.println("[Subsystem] Driving in arcade mode, left: " + leftPower + ", right: " + rightPower);
		}
	}

	public void initDefaultCommand() {
		if (RobotMap.DRIVE_MODE == 1){
			setDefaultCommand(new ArcadeDrive());
		}
		else{
			setDefaultCommand(new TankDrive());
		}
	}
}
