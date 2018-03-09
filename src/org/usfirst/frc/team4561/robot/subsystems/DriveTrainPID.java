package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;
import org.usfirst.frc.team4561.robot.commands.ArcadeDrive;
import org.usfirst.frc.team4561.robot.commands.TankDrive;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
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
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

/**
 * @author Snehil
 */

public class DriveTrainPID extends Subsystem {
	
	double maxSpeed = 3500;		//Maximum RPM
	double maxSpeedHighGear = 9300;
	double maxSpeedLowGear = 3500;
	double speedF = 0.161;
	double torqueF = 0.417;
	double goalL;
	double goalR;
	//Control Modes
	private ControlMode follower = com.ctre.phoenix.motorcontrol.ControlMode.Follower;
	private ControlMode velocity = com.ctre.phoenix.motorcontrol.ControlMode.Velocity;
	
	//Declare all motors variables as TalonSRXs
	public TalonSRX frontRight = new TalonSRX(RobotMap.FRONT_RIGHT_MOTOR_PORT);
	public TalonSRX frontLeft = new TalonSRX(RobotMap.FRONT_LEFT_MOTOR_PORT);
		
	private TalonSRX midRight = new TalonSRX(RobotMap.MID_RIGHT_MOTOR_PORT);
	private TalonSRX midLeft = new TalonSRX(RobotMap.MID_LEFT_MOTOR_PORT);
		
	private TalonSRX rearRight = new TalonSRX(RobotMap.BACK_RIGHT_MOTOR_PORT);
	private TalonSRX rearLeft = new TalonSRX(RobotMap.BACK_LEFT_MOTOR_PORT);
	
	public static final boolean LEFT_SIDE_INVERTED = false;
	public static final boolean RIGHT_SIDE_INVERTED = true;
	
	double angleAccum = 0;
	double rateAccum = 0;
	double angleAvg = 0;
	double rateAvg = 0;
	
	private ADXRS450_Gyro gyro = new ADXRS450_Gyro();

	double leftSpeed;
	double rightSpeed;
	double leftSpeedOriginal;
	double rightSpeedOriginal;
	
	public static final double kInchesToTicks = 487.6;
	public static final double kFeetToTicks = kInchesToTicks*12;
	public static final double kInchesToTicksSpeed = kInchesToTicks/10;
	public static final double kFeetToTicksSpeed = kFeetToTicks/10;
		
	//Set middle and back motors as followers to front two motors, and set the PIDF values (currently placeholders)
	public DriveTrainPID() {
			
		midRight.set(follower, RobotMap.FRONT_RIGHT_MOTOR_PORT);
		midRight.configPeakOutputForward(1, 0);
		midRight.configPeakOutputReverse(-1, 0);
			
		rearRight.set(follower, RobotMap.FRONT_RIGHT_MOTOR_PORT);
			
		midLeft.set(follower, RobotMap.FRONT_LEFT_MOTOR_PORT);
		
		rearLeft.set(follower, RobotMap.FRONT_LEFT_MOTOR_PORT);
		
		frontLeft.setInverted(LEFT_SIDE_INVERTED);
		midLeft.setInverted(LEFT_SIDE_INVERTED);
		rearLeft.setInverted(LEFT_SIDE_INVERTED);
		
		frontRight.setInverted(RIGHT_SIDE_INVERTED);
		midRight.setInverted(RIGHT_SIDE_INVERTED);
		rearRight.setInverted(RIGHT_SIDE_INVERTED);
		
		frontLeft.setSensorPhase(false);
		frontRight.setSensorPhase(false);
		
		gyro.calibrate();
		
		double kP = 0.35;
		double kI = 0.01;
		double kD = 0;
		
		frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0);
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
			
		frontRight.config_kF(0, torqueF, 0);
		frontRight.config_kP(0, kP, 0);
		frontRight.config_kI(0, kI, 0);
		frontRight.config_kD(0, kD, 0);
		frontRight.config_IntegralZone(0, 50, 0);
		frontRight.configMotionCruiseVelocity(3500, 0);
		rightSpeed = 0.225;
		frontRight.configMotionAcceleration(3500, 0);
		frontRight.configNominalOutputForward(0, 0);
		frontRight.configNominalOutputReverse(0, 0);
		frontRight.configPeakOutputForward(1, 0);
		frontRight.configPeakOutputReverse(-1, 0);
		frontRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
		frontRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
			
		frontLeft.config_kF(0, torqueF, 0);
		frontLeft.config_kP(0, kP, 0);
		frontLeft.config_kI(0, kI, 0);
		frontLeft.config_kD(0, kD, 0);
		frontLeft.config_IntegralZone(0, 50, 0);
		frontLeft.configMotionCruiseVelocity(3500, 0);
		leftSpeed = 0.225;
		frontLeft.configMotionAcceleration(3500, 0);
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
		
		
		double exponent = SmartDashboard.getNumber("DB/Slider 2", 1);
		
		zRotation = Math.copySign(Math.pow(zRotation, exponent), zRotation);
		
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
			System.out.print(rightMotorOutput);
			System.out.println(" ");
			System.out.println(leftMotorOutput);
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
		return (getLeftSpeed()+(getRightSpeed()))/2;
	}
	public double ticksToInches(double inches){
		return inches*kInchesToTicks;
	}
	public double fLThrottle(){
		return frontLeft.getMotorOutputPercent();
	}
	public double mLThrottle(){
		return midLeft.getMotorOutputPercent();
	}
	public double rLThrottle(){
		return rearLeft.getMotorOutputPercent();
	}
	public double fRThrottle(){
		return frontRight.getMotorOutputPercent();
	}
	public double mRThrottle(){
		return midRight.getMotorOutputPercent();
	}
	public double rRThrottle(){
		return rearRight.getMotorOutputPercent();
	}
	public void magicDrive (double lInches, double rInches){
		//System.out.println("Running for real");
		double leftRot = -1*kInchesToTicks*lInches;
		double rightRot = -1*kInchesToTicks*rInches;
		resetEncoders();
		//System.out.println("Running actually for real");
		System.out.println(leftRot+" "+rightRot);
		frontLeft.set(ControlMode.MotionMagic, leftRot);
		frontRight.set(ControlMode.MotionMagic, rightRot);
		goalL = leftRot;
		goalR = rightRot;
		
		//resetGyro();
		
		
	}
	public void setUpMotionProfiling(Trajectory pointsR, Trajectory pointsL){
		frontRight.clearMotionProfileTrajectories();
		frontLeft.clearMotionProfileTrajectories();
		TrajectoryPoint pointR = new TrajectoryPoint();
		TrajectoryPoint pointL = new TrajectoryPoint();
		
		for (int i = 0; i < pointsR.length(); i++){
			pointR.position = pointsR.get(i).position*kFeetToTicks;
			pointR.velocity = pointsR.get(i).velocity*kFeetToTicksSpeed;
			pointR.headingDeg = Pathfinder.r2d(pointsR.get(i).heading);
			
			pointR.zeroPos = (i == 0);
			pointR.isLastPoint = ((i+1) == pointsR.length());
			frontRight.pushMotionProfileTrajectory(pointR);
		}
		
		for (int i = 0; i < pointsL.length(); i++){
			pointL.position = pointsL.get(i).position*kFeetToTicks;
			pointL.velocity = pointsL.get(i).velocity*kFeetToTicksSpeed;
			pointL.headingDeg = Pathfinder.r2d(pointsL.get(i).heading);
			
			pointL.zeroPos = (i == 0);
			pointL.isLastPoint = ((i+1) == pointsL.length());
			frontLeft.pushMotionProfileTrajectory(pointL);
		}
		
	}
	public void runMotionProfile(){
		if (!frontRight.isMotionProfileTopLevelBufferFull()){
			frontRight.processMotionProfileBuffer();
		}
		if (!frontLeft.isMotionProfileTopLevelBufferFull()){
			frontLeft.processMotionProfileBuffer();
		}
		
		frontRight.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
		frontLeft.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
	}
	public void holdMotionProfile(){
		frontRight.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
		frontLeft.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
	}
	public boolean isProfileFinished(){
		MotionProfileStatus statusR = new MotionProfileStatus();
		frontRight.getMotionProfileStatus(statusR);
		MotionProfileStatus statusL = new MotionProfileStatus();
		frontLeft.getMotionProfileStatus(statusL);
		return (statusR.activePointValid&&statusR.isLast)&&(statusL.activePointValid&&statusL.isLast);
	}
	public void setToPosition(){
		frontLeft.set(ControlMode.Position, 0);
		frontRight.set(ControlMode.Position, 0);
	}
	public String getMode(){
		return frontLeft.getControlMode().toString();
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
		//frontLeft.set(ControlMode.PercentOutput, 0);
		//frontRight.set(ControlMode.PercentOutput, 0);
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
	
    public void setLeftMotorPower(double power) {
        if (power > 1) {
            if (RobotMap.DRIVETRAIN_DEBUG) {
                System.out.println("[Subsystem] Power to left side of drivetrain was set too high: " + power + ", changing to full forward.");
            }
            power = 1;
        } else if (power < -1) {
            if (RobotMap.DRIVETRAIN_DEBUG) {
                System.out.println("[Subsystem] Power to left side of drivetrain was set too low: " + power + ", changing to full reverse.");
            }
            power = -1;
        }
        frontLeft.set(ControlMode.PercentOutput, power);
    }
    
    public void setRightMotorPower(double power) {
        if (power > 1) {
            if (RobotMap.DRIVETRAIN_DEBUG) {
                System.out.println("[Subsystem] Power to right side of drivetrain was set too high: " + power + ", changing to full forward.");
            }
            power = 1;
        } else if (power < -1) {
            if (RobotMap.DRIVETRAIN_DEBUG) {
                System.out.println("[Subsystem] Power to right side of drivetrain was set too low: " + power + ", changing to full reverse.");
            }
            power = -1;
        }
        frontRight.set(ControlMode.PercentOutput, power);
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
	public double getLeftCurrent(){
		return frontLeft.getOutputCurrent();
	}
	public double getRightCurrent(){
		return frontRight.getOutputCurrent();
	}
	public double getAvgError(){
		return avgErr();
	}
	public double getLeftMidCurrent(){
		return midLeft.getOutputCurrent();
	}
	public double getRightMidCurrent(){
		return midRight.getOutputCurrent();
	}
	public double getLeftRearCurrent(){
		return rearLeft.getOutputCurrent();
	}
	public int avgErr(){
		return (int) (((Math.abs(getLeftPos()-goalL))+(Math.abs(getRightPos()-goalR)))/2);
	}
	public boolean nearGoal(){
		boolean yes = Math.abs(avgErr())<250 && Math.abs(avgSpeed()) < 20;
		if (yes) System.out.println("Yes");
		return yes;
	}
	public double getRightRearCurrent(){
		return rearRight.getOutputCurrent();
	}
	public void switchToTorque(){
		maxSpeed = maxSpeedLowGear;
		frontLeft.config_kF(0, torqueF, 0);
		frontRight.config_kF(0, torqueF, 0);
	}
	public void switchToSpeed(){
		maxSpeed = maxSpeedHighGear;
		frontLeft.config_kF(0, speedF, 0);
		frontRight.config_kF(0, speedF, 0);
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
			//setDefaultCommand(new ArcadeDrive());
		}
		else{
			setDefaultCommand(new TankDrive());
		}
	}	
	public void invertLeftSide(boolean invert) {
		frontLeft.setInverted(invert);
		midLeft.setInverted(invert);
		rearLeft.setInverted(invert);
	}
	public void invertRightSide(boolean invert) {
		frontRight.setInverted(invert);
		midRight.setInverted(invert);
		rearRight.setInverted(invert);
	}
	
	public void setUpForMotionProfiling() {
		frontLeft.configMotionProfileTrajectoryPeriod(0, 0);
		frontLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0); // Status 10 is also for motion profiling
		frontLeft.setSelectedSensorPosition(0, 0, 0);
		frontLeft.setInverted(LEFT_SIDE_INVERTED);
		midLeft.setInverted(LEFT_SIDE_INVERTED);
		rearLeft.setInverted(LEFT_SIDE_INVERTED);
		
		frontRight.configMotionProfileTrajectoryPeriod(0, 0);
		frontRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0); // Status 10 is also for motion profiling
		frontRight.setSelectedSensorPosition(0, 0, 0);
		frontRight.setInverted(RIGHT_SIDE_INVERTED);
		midRight.setInverted(RIGHT_SIDE_INVERTED);
		rearRight.setInverted(RIGHT_SIDE_INVERTED);
	}
}
