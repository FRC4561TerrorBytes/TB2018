package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.RobotMap;
import org.usfirst.frc.team4561.robot.commands.ArcadeDrive;
import org.usfirst.frc.team4561.robot.commands.TankDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;

/**
 * @author Snehil
 */

public class DriveTrainPID extends Subsystem {
	
	double maxSpeed;		//Maximum RPM
	
	//Control Modes
	private ControlMode follower = com.ctre.phoenix.motorcontrol.ControlMode.Follower;
	private ControlMode velocity = com.ctre.phoenix.motorcontrol.ControlMode.Velocity;
	
	//Declare all motors variables as TalonSRXs
	private TalonSRX frontRight = new TalonSRX(RobotMap.FRONT_RIGHT_MOTOR_PORT);
	private TalonSRX frontLeft = new TalonSRX(RobotMap.FRONT_LEFT_MOTOR_PORT);
		
	private TalonSRX midRight = new TalonSRX(RobotMap.MID_RIGHT_MOTOR_PORT);
	private TalonSRX midLeft = new TalonSRX(RobotMap.MID_LEFT_MOTOR_PORT);
		
	private TalonSRX rearRight = new TalonSRX(RobotMap.BACK_RIGHT_MOTOR_PORT);
	private TalonSRX rearLeft = new TalonSRX(RobotMap.BACK_LEFT_MOTOR_PORT);
		
	//Set middle and back motors as followers to front two motors, and set the PIDF values (currently placeholders)
	public DriveTrainPID() {
			
		midRight.set(follower, RobotMap.FRONT_RIGHT_MOTOR_PORT);
			
		rearRight.set(follower, RobotMap.FRONT_RIGHT_MOTOR_PORT);
			
		midLeft.set(follower, RobotMap.FRONT_LEFT_MOTOR_PORT);
		
		rearLeft.set(follower, RobotMap.FRONT_LEFT_MOTOR_PORT);
			
		frontRight.config_kF(0, 0, 0);
		frontRight.config_kP(0, 0, 0);
		frontRight.config_kI(0, 0, 0);
		frontRight.config_kD(0, 0, 0);
			
		frontLeft.config_kF(0, 0, 0);
		frontLeft.config_kP(0, 0, 0);
		frontLeft.config_kI(0, 0, 0);
		frontLeft.config_kD(0, 0, 0);
	}		
	
	//Set the right and left sides of the robots to speeds based on input speed and rotation
	public void arcadeDrive(double xSpeed, double zRotation) {
		
		double leftMotorOutput = 0;
		double rightMotorOutput = 0;

		double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

		if (xSpeed >= 0.0) {
			// First quadrant, else second quadrant
			if (zRotation >= 0.0) {
				leftMotorOutput = maxInput;
				rightMotorOutput = xSpeed - zRotation;
			} else {
				leftMotorOutput = xSpeed + zRotation;
				rightMotorOutput = maxInput;
			}
		} else {
			// Third quadrant, else fourth quadrant
			if (zRotation >= 0.0) {
				leftMotorOutput = xSpeed + zRotation;
				rightMotorOutput = maxInput;
			} else {
				leftMotorOutput = maxInput;
				rightMotorOutput = xSpeed - zRotation;
			}
		}
		
		if (RobotMap.DRIVETRAIN_PID){
			frontRight.set(velocity, maxSpeed * rightMotorOutput);
			frontLeft.set(velocity, maxSpeed * leftMotorOutput);
		}
		else{
			frontRight.set(ControlMode.PercentOutput, rightMotorOutput);
			frontLeft.set(ControlMode.PercentOutput, leftMotorOutput);
		}
	}
		
	//Set the right and left sides of the robot to speeds based on input speeds in both motor sides.
	public void tankDrive(double leftSpeed, double rightSpeed) { 
			
		leftSpeed = limit(leftSpeed);
		rightSpeed = limit(rightSpeed);
		
		if (RobotMap.DRIVETRAIN_PID){
			frontRight.set(velocity, maxSpeed * rightSpeed);
			frontLeft.set(velocity, maxSpeed * leftSpeed);
		}
		else {
			frontRight.set(ControlMode.PercentOutput, rightSpeed);
			frontLeft.set(ControlMode.PercentOutput, leftSpeed);
		}
	}
		
	//Set value to number between -1 and 1
	protected double limit(double value) {
		if (value > 1.0) {
			return 1.0;
		}
		if (value < -1.0) {
			return -1.0;
		}
		return value;
   }

	@Override
	protected void initDefaultCommand() {
		if (RobotMap.DRIVE_MODE == 1){
			setDefaultCommand(new ArcadeDrive());
		}
		else{
			setDefaultCommand(new TankDrive());
		}
	}	

}
