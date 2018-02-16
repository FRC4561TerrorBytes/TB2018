package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;
import org.usfirst.frc.team4561.robot.commands.ArcadeDrive;
import org.usfirst.frc.team4561.robot.commands.TankDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	double angleAccum = 0;
	double rateAccum = 0;
	double angleAvg = 0;
	double rateAvg = 0;
	
	private ADXRS450_Gyro gyro = new ADXRS450_Gyro();

	double leftSpeed;
	double rightSpeed;
	double leftSpeedOriginal;
	double rightSpeedOriginal;
		
	//Set middle and back motors as followers to front two motors, and set the PIDF values (currently placeholders)
	public DriveTrainPID() {
			
		midRight.set(follower, RobotMap.FRONT_RIGHT_MOTOR_PORT);
			
		rearRight.set(follower, RobotMap.FRONT_RIGHT_MOTOR_PORT);
			
		midLeft.set(follower, RobotMap.FRONT_LEFT_MOTOR_PORT);
		
		rearLeft.set(follower, RobotMap.FRONT_LEFT_MOTOR_PORT);
		
		frontRight.setInverted(true);
		midRight.setInverted(true);
		rearRight.setInverted(true);
		gyro.calibrate();
		
		frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0);
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
			
		frontRight.config_kF(0, 0.225, 0);
		frontRight.config_kP(0, 5, 0);
		frontRight.config_kI(0, 0, 0);
		frontRight.config_kD(0, 1, 0);
		frontRight.config_IntegralZone(0, 50, 0);
		frontRight.configMotionCruiseVelocity(666, 0);
		rightSpeed = 0.225;
		frontRight.configMotionAcceleration(166, 0);
		frontRight.configNominalOutputForward(0, 0);
		frontRight.configNominalOutputReverse(0, 0);
		frontRight.configPeakOutputForward(1, 0);
		frontRight.configPeakOutputReverse(-1, 0);
		frontRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
		frontRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
			
		frontLeft.config_kF(0, 0.225, 0);
		frontLeft.config_kP(0, 5, 0);
		frontLeft.config_kI(0, 0, 0);
		frontLeft.config_kD(0, 1, 0);
		frontLeft.config_IntegralZone(0, 50, 0);
		frontLeft.configMotionCruiseVelocity(1333, 0);
		leftSpeed = 0.225;
		frontLeft.configMotionAcceleration(333, 0);
		frontLeft.configNominalOutputForward(0, 0);
		frontLeft.configNominalOutputReverse(0, 0);
		frontLeft.configPeakOutputForward(1, 0);
		frontLeft.configPeakOutputReverse(-1, 0);
		frontLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
		frontLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
		
		gyro.calibrate();
	}		
	
	//Set the right and left sides of the robots to speeds based on input speed and rotation
	public void arcadeDrive(double xSpeed, double zRotation) {
		
		double leftMotorOutput = 0;
		double rightMotorOutput = 0;

		double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);
		zRotation = -zRotation;
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
	
	public double avgSpeed(){
		return (-getLeftSpeed()+(getRightSpeed()*2))/2;
	}
	public void magicDrive (double leftRot, double rightRot){
		frontLeft.set(ControlMode.MotionMagic, leftRot);
		frontRight.set(ControlMode.MotionMagic, rightRot);
		resetGyro();
		int timeout = 0;
		double sideChooser = 0;
		double kP = 1;
		double kD = 20;	
		while (((getLeftPos()-leftRot) > 5 || (leftRot-getLeftPos()) > 5) && ((getRightPos()-rightRot) > 5 || (rightRot-getRightPos()) > 5)){
			double angle = getGyroAngle();
			double rate = getGyroRate();
			double correction = 1/100;
			if (getLeftPos() > leftRot && getRightPos() > rightRot){
				correction = -1/100;
			}
			angleAccum = angleAccum + (angle*avgSpeed()/(1333));
			rateAccum = rateAccum + (rate);
			angleAvg = angleAccum;
			rateAvg = rateAccum;
			sideChooser = (kP*angleAvg)+(kD*rateAvg);
			if (sideChooser > 0 && timeout == 0){
				rightSpeed = rightSpeed + (rightSpeedOriginal * correction * sideChooser);
				leftSpeed = leftSpeed - (leftSpeedOriginal * correction * sideChooser);
				frontRight.config_kF(0, rightSpeed, 0);
				frontLeft.config_kF(0, leftSpeed, 0);
				timeout = 1;
				//System.out.println("Correcting Right");
			}
			else if (sideChooser < 0 && timeout == 0){
				leftSpeed = leftSpeed - (leftSpeedOriginal * correction * sideChooser);
				rightSpeed = rightSpeed + (rightSpeedOriginal * correction * sideChooser);
				frontLeft.config_kF(0, leftSpeed, 0);
				frontRight.config_kF(0, rightSpeed, 0);
				timeout = 1;
				//System.out.println("Correcting Left");
			}
			
			if (timeout > 0){
				timeout--;
			}
			SmartDashboard.putNumber("Gyro Angle", angleAvg);
			SmartDashboard.putNumber("Gyro Rate", rateAvg);
			SmartDashboard.putNumber("Correction Value", sideChooser);
			SmartDashboard.putNumber("Left Speed", Robot.driveTrain.getLeftSpeed());
			SmartDashboard.putNumber("Right Speed", Robot.driveTrain.getRightSpeed());
			SmartDashboard.putNumber("Left Pos", Robot.driveTrain.getLeftPos());
			SmartDashboard.putNumber("Right Pos", Robot.driveTrain.getRightPos());
			SmartDashboard.putNumber("Left Error", Robot.driveTrain.getLeftError());
			SmartDashboard.putNumber("Right Error", Robot.driveTrain.getRightError());
			SmartDashboard.putNumber("Avg Speed", Robot.driveTrain.avgSpeed());
		}
		System.out.println("Arrived");
		frontLeft.config_kF(0, leftSpeedOriginal, 0);
		frontRight.config_kF(0, rightSpeedOriginal, 0);
		leftSpeed = leftSpeedOriginal;
		rightSpeed = rightSpeedOriginal;
		goToAngle(0);
	}
	public void goToAngle(double target){
		int timeout = 0;
		System.out.println("Turning to " + target);
		double kP = 0.0035;
		double kI = 0.000025;
		double kD = 0.035;
		double angle = getGyroAngle();
		double error = target-Math.abs(angle);
		double angleAccum = 0;
		while (Math.abs(error) > 0.25){
			angle = Math.abs(getGyroAngle());
			error = target-Math.abs(angle);
			if (Math.abs(error) < 30) angleAccum = angleAccum + Math.abs(error);
			if (error > 0){
				if (Math.abs(error) < 30){
					frontRight.set(ControlMode.PercentOutput, error*kP+(angleAccum*kI));
					frontLeft.set(ControlMode.PercentOutput, error*kP+(angleAccum*kI));
				}
				else{
					frontRight.set(ControlMode.PercentOutput, error*kP);
					frontLeft.set(ControlMode.PercentOutput, error*kP);
				}
			}
			else if (error < 0){
				if (Math.abs(error) < 30){
					frontRight.set(ControlMode.PercentOutput, -(Math.abs(error)*kP+(angleAccum*kI)));
					frontLeft.set(ControlMode.PercentOutput, -(Math.abs(error)*kP+(angleAccum*kI)));
				}
				else{
					frontRight.set(ControlMode.PercentOutput, -(Math.abs(error)*kP));
					frontLeft.set(ControlMode.PercentOutput, -(Math.abs(error)*kP));
				}
			}
			if (timeout > 0){
				timeout--;
			}
			SmartDashboard.putNumber("Gyro Angle", angle);
			SmartDashboard.putNumber("Gyro Rate", rateAvg);
			SmartDashboard.putNumber("Correction Value", error);
			SmartDashboard.putNumber("Left Speed", Robot.driveTrain.getLeftSpeed());
			SmartDashboard.putNumber("Right Speed", Robot.driveTrain.getRightSpeed());
			SmartDashboard.putNumber("Left Pos", Robot.driveTrain.getLeftPos());
			SmartDashboard.putNumber("Right Pos", Robot.driveTrain.getRightPos());
			SmartDashboard.putNumber("Left Error", Robot.driveTrain.getLeftError());
			SmartDashboard.putNumber("Right Error", Robot.driveTrain.getRightError());
			SmartDashboard.putNumber("Avg Speed", Robot.driveTrain.avgSpeed());
		}
		System.out.println("Turned to the angle");
		frontLeft.set(ControlMode.PercentOutput, 0);
		frontRight.set(ControlMode.PercentOutput, 0);
		angleAccum = 0;
		rateAccum = 0;
		angleAvg = 0;
		rateAvg = 0;
		resetEncoders();
	}
	public double getLeftSpeed(){
		return frontLeft.getSelectedSensorVelocity(0);
	}
	public double getRightSpeed(){
		return frontRight.getSelectedSensorVelocity(0);
	}
	public double getLeftPos(){
		return frontLeft.getSelectedSensorPosition(0);
	}
	public double getRightPos(){
		return frontRight.getSelectedSensorPosition(0);
	}
	public void resetEncoders(){
		frontLeft.setSelectedSensorPosition(0, 0, 0);
		frontRight.setSelectedSensorPosition(0, 0, 0);
	}
	public double getGyroAngle(){
		return gyro.getAngle();
	}
	public double getGyroRate(){
		return gyro.getRate();
	}
	public void resetGyro(){
		gyro.reset();
	}
	public double getLeftError(){
		return frontLeft.getClosedLoopError(0);
	}
	public double getRightError(){
		return frontRight.getClosedLoopError(0);
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
