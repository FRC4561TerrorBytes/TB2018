/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// Dependent on Pathfinder for Java: https://github.com/JacisNonsense/Pathfinder

package org.usfirst.frc.team4561.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4561.trajectories.MotionProfileRunner;
import org.usfirst.frc.team4561.robot.automodes.*;
import org.usfirst.frc.team4561.robot.commands.ArcadeDrive;
import org.usfirst.frc.team4561.robot.commands.ArmDrive;
import org.usfirst.frc.team4561.robot.commands.CheckScaleSide;
import org.usfirst.frc.team4561.robot.commands.CheckSwitchSide;
import org.usfirst.frc.team4561.robot.commands.ElevatorDrive;
import org.usfirst.frc.team4561.robot.commands.IntakeDrive;
import org.usfirst.frc.team4561.robot.commands.ResetArm;
import org.usfirst.frc.team4561.robot.commands.ResetElevator;
import org.usfirst.frc.team4561.robot.commands.TankDrive;
import org.usfirst.frc.team4561.robot.commands.ToggleArmPID;
import org.usfirst.frc.team4561.robot.commands.ToggleDriveTrainPID;
import org.usfirst.frc.team4561.robot.commands.ToggleElevatorPID;
import org.usfirst.frc.team4561.robot.subsystems.*;
import org.usfirst.frc.team4561.trajectories.*;

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
	public static final Gyroscope gyro = new Gyroscope();
	public static CameraServer cam;
	//public static final Encoder testEncoder = new Encoder(0, 1);
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	//public static Elevator elevator = new Elevator();
	public static Transmission transmission = new Transmission();
	//public static Arm arm = new Arm(); // non-PID arm
	
	public static boolean switchFMSSideRight; // true if right, false if left
	public static boolean scaleFMSSideRight; // true if right, false if left
	private Command getFieldData;
	public static final Path midScaleRightCSV = new WallToRightScaleCSV();
	public static Path midSwitchLeft;
	public static final Path leftScaleLeft = new LeftScaleLeft();
	public static final Path leftScaleTurnAroundS3 = new LeftScaleTurnAroundS3();
	public static final Path scaleLeftSwitchLeftCube = new ScaleLeftSwitchLeftCube();
	public static final Path rightScaleRight = new RightScaleRight();
	public static final Path rightScaleTurnAroundS4 = new RightScaleTurnAroundS4();
	public static final Path scaleRightSwitchRightCube = new ScaleRightSwitchRightCube();
	public static MotionProfileRunner motionProfileRunner = new MotionProfileRunner(Robot.driveTrain.frontLeft, Robot.driveTrain.frontRight);
	public static MotionProfileOnboardRunner motionProfileOnboardRunner = new MotionProfileOnboardRunner(Robot.driveTrain.frontLeft, Robot.driveTrain.frontRight);
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	public static boolean elevatorHealthy = true;
	public static boolean armHealthy = true;
	public static boolean driveHealthy = true;
	
	public static boolean autoDisableElvPID = false; //If true, disables elevator PID if a problem is detected with the sensor
	public static boolean autoDisableArmPID = false; //If true, disables arm PID if a problem is detected with the sensor
	public static boolean autoDisableDrvPID = false; //If true, disables drivetrain PID if a problem is detected with the sensor
	
	public static boolean autoShiftTorque = false; //If true, automatically shifts to low gear when stalled
	public static boolean autoShiftSpeed = false; //If true, automatically shifts to high gear when at max speed in low gear
	
	@Override
	public void robotInit() {
		oi = new OI();
		
		UsbCamera cam = CameraServer.getInstance().startAutomaticCapture();
		//m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		//(new CheckSwitchSide()).start();
		
		SmartDashboard.putData("Auto mode", chooser);
		System.out.println("Things are not on fire");
		oi.toggleArmPID.whenActive(new ToggleArmPID());
		oi.toggleElevatorPID.whenActive(new ToggleElevatorPID());
		oi.toggleDriveTrainPID.whenActive(new ToggleDriveTrainPID());
		oi.stopElevatorRelative.whenActive(new ResetElevator());
		oi.startElevatorRelative.whenActive(new ResetElevator());
		oi.startArmRelative.whenActive(new ResetArm());
		oi.stopArmRelative.whenActive(new ResetArm());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		intake.stop();
		elevator.resetGoal();
		arm.reset();
		driveTrain.stop();
		motionProfileOnboardRunner.reset();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		intake.stop();
		elevator.resetGoal();
		arm.reset();
		driveTrain.stop();
		//arm.resetEncoder();
	}
	
	public void robotPeriodic(){
		motionProfileRunner.control();
		motionProfileOnboardRunner.control();
		if (RobotMap.ARM_DEBUG){
			SmartDashboard.putNumber("Arm/Encoder Position", Robot.arm.getEncoderPosition());
	    	SmartDashboard.putNumber("Arm/Encoder Velocity", Robot.arm.getEncoderVelocity());
	    	SmartDashboard.putNumber("Arm/Goal Position", Robot.arm.getGoal());
	    	SmartDashboard.putBoolean("Arm/Forward Limit Switch", Robot.arm.getFwdSwitch());
	    	SmartDashboard.putBoolean("Arm/Reverse Limit switch", Robot.arm.getRevSwitch());
	    	SmartDashboard.putNumber("Arm/Motor Voltage", Robot.arm.getVoltage());
		}
    	
		if (RobotMap.ELEVATOR_DEBUG){
	    	SmartDashboard.putNumber("Elevator/Position", Robot.elevator.getElevatorPos());
	    	SmartDashboard.putNumber("Elevator/Motor One Voltage", Robot.elevator.motorOneVoltage());
	    	SmartDashboard.putNumber("Elevator/Motor Two Voltage", Robot.elevator.motorTwoVoltage());
	    	SmartDashboard.putNumber("Elevator/Goal", Robot.elevator.getGoal());
	    	SmartDashboard.putBoolean("Elevator/Limit Switch", Robot.elevator.limitSwitch());
			SmartDashboard.putNumber("Elevator/Speed", Robot.elevator.getElevatorSpeed());
			SmartDashboard.putNumber("Elevator/Sensor Voltage", Robot.elevator.getPotVolt());
		}
    	
    	SmartDashboard.putNumber("Heartbeat <3", Math.random());

    	if (RobotMap.DRIVETRAIN_DEBUG){
	    	SmartDashboard.putNumber("DriveTrain/Left Speed", Robot.driveTrain.getLeftSpeed());
	    	SmartDashboard.putNumber("DriveTrain/Right Speed", Robot.driveTrain.getRightSpeed());
	    	SmartDashboard.putNumber("DriveTrain/Left Position", Robot.driveTrain.getLeftPos());
	    	SmartDashboard.putNumber("DriveTrain/Right Position", Robot.driveTrain.getRightPos());
	    	SmartDashboard.putNumber("DriveTrain/Average Error", Robot.driveTrain.avgErr());
	    	SmartDashboard.putNumber("DriveTrain/Average Speed", Robot.driveTrain.avgSpeed());
    	}
    	
    	if (RobotMap.TRANSMISSION_DEBUG) {
    		SmartDashboard.putString("Transmission/State", Robot.transmission.getTransMode());
    	}
    	//SmartDashboard.putNumber("Test Encoder", testEncoder.getDistance());
    	//SmartDashboard.putNumber("Test Encoder speed", testEncoder.getRate());
    	SmartDashboard.putNumber("Drive Current/Drive Train Left Front", Robot.driveTrain.getLeftCurrent());
    	SmartDashboard.putNumber("Drive Current/Drive Train Right Front", Robot.driveTrain.getRightCurrent());
    	SmartDashboard.putNumber("Drive Current/Drive Train Left Mid", Robot.driveTrain.getLeftMidCurrent());
    	SmartDashboard.putNumber("Drive Current/Drive Train Right Mid", Robot.driveTrain.getRightMidCurrent());
    	SmartDashboard.putNumber("Drive Current/Drive Train Left Rear", Robot.driveTrain.getLeftRearCurrent());
    	SmartDashboard.putNumber("Drive Current/Drive Train Right Rear", Robot.driveTrain.getRightRearCurrent());
    	SmartDashboard.putNumber("Drive Throttle/Drive Train Left Front Throttle", Robot.driveTrain.fLThrottle());
    	SmartDashboard.putNumber("Drive Throttle/Drive Train Left Mid Throttle", Robot.driveTrain.mLThrottle());
    	SmartDashboard.putNumber("Drive Throttle/Drive Train Left Rear Throttle", Robot.driveTrain.rLThrottle());
    	SmartDashboard.putNumber("Drive Throttle/Drive Train Right Front Throttle", Robot.driveTrain.fRThrottle());
    	SmartDashboard.putNumber("Drive Throttle/Drive Train Right Mid Throttle", Robot.driveTrain.mRThrottle());
    	SmartDashboard.putNumber("Drive Throttle/Drive Train Right Rear Throttle", Robot.driveTrain.rRThrottle());
    	SmartDashboard.putNumber("Motor Current/Arm", Robot.arm.getCurrent());
    	SmartDashboard.putNumber("Motor Current/Elevator Master", Robot.elevator.getCurrentOne());
    	SmartDashboard.putNumber("Motor Current/Elevator Slave", Robot.elevator.getCurrentTwo());
    	
    	SmartDashboard.putNumber("Left Error", Robot.driveTrain.getLeftError());
		SmartDashboard.putNumber("Right Error", Robot.driveTrain.getRightError());
		
    	//SmartDashboard.putNumber("Controller POV", oi.getControllerPOV());
    	if (Robot.arm.getRevSwitch()){
			Robot.arm.setEncoderPos(-1120);
		}
    	if (RobotMap.ARM_PID){
    		SmartDashboard.putString("DB/String 3", "Arm PID: ON");
    	}
    	else {
    		SmartDashboard.putString("DB/String 3", "!!Arm PID: OFF!!");
    	}
    	if (RobotMap.ELEVATOR_PID){
    		SmartDashboard.putString("DB/String 2", "Elevator PID: ON");
    	}
    	else {
    		SmartDashboard.putString("DB/String 2", "!!Elevator PID: OFF!!");
    	}
    	if (RobotMap.DRIVETRAIN_PID) {
    		SmartDashboard.putString("DB/String 1", "Drivetrain PID: ON");
    	}
    	else {
    		SmartDashboard.putString("DB/String 1", "!!Drivetrain PID: OFF!!");
    	}
    	
    	if (arm.getEncoderPosition()==0&&!arm.getFwdSwitch() || arm.getEncoderPosition() < -1120) {
    		//0 position on the arm is where the forward limit switch is, going beyond -1120 should never happen
    		armHealthy = false;
    		SmartDashboard.putString("DB/String 8", "!!CHECK ARM ENCODER!!");
    		if (autoDisableArmPID) {
    			RobotMap.ARM_PID = false;
    			arm.stop();
    		}
    	}
    	else {
    		armHealthy = true;
    		SmartDashboard.putString("DB/String 8", "Arm Healthy");
    	}
    	if (elevator.getElevatorPos() == 0) { //Bottom position of the elevator is 29 - we should never hit 0
    		elevatorHealthy = false;
    		SmartDashboard.putString("DB/String 7", "!!CHECK ELEVATOR SENSOR!!");
    		if (autoDisableElvPID) {
    			RobotMap.ELEVATOR_PID = false;
    			elevator.stop();
    		}
    	}
    	else {
    		elevatorHealthy = true;
    		SmartDashboard.putString("DB/String 7", "Elevator Healthy");
    	}
    	if (Math.abs(driveTrain.fLThrottle()) > 0.125 && driveTrain.getLeftSpeed() == 0) {
    		driveHealthy = false;
    		if (driveTrain.getLeftPos() == 0) { //If all values from the sensor are 0 while the motor is getting power, sensor is likely not connected
    			SmartDashboard.putString("DB/String 6", "!!CHECK DRIVETRAIN SENSORS!!");
    			SmartDashboard.putString("DB/String 9", "");
    			if (autoDisableDrvPID) {
    				RobotMap.DRIVETRAIN_PID = false;
    				driveTrain.stop();
    			}
    		}
    		else if (driveTrain.getLeftSpeed() != 0) { //If we are receiving positional data from the sensor, we are likely just stalling
        		SmartDashboard.putString("DB/String 6", "!!DRIVETRAIN STALLING!!");
        		if (autoShiftTorque && !transmission.isTorque()) {
        			transmission.torqueGear();
        		}
        		else {
            		SmartDashboard.putString("DB/String 9", "!!SHIFTING RECOMMENDED!!");
        		}
    		}
    		else if (Math.abs(driveTrain.fLThrottle()) == 1 && Math.abs(driveTrain.fRThrottle()) == 1 && transmission.isTorque()) {
        		if (autoShiftSpeed) {
        			transmission.speedGear();
        		}
        		else {
        			SmartDashboard.putString("DB/String 9", "!!SHIFTING RECOMMENDED!!");
        		}
        	}
    	}
    	else {
    		driveHealthy = true;
    		SmartDashboard.putString("DB/String 6", "Drivetrain Healthy");
    		SmartDashboard.putString("DB/String 9", "");
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
		//getFieldData = new CheckScaleSide();
		motionProfileRunner.control();
		int auto = (int) SmartDashboard.getNumber("DB/Slider 0", 0);
		switch (auto){
		case 0:
			autonomousCommand = null;
			break;
		case 1:
			autonomousCommand = new AutoDriveToLine();
			break;
		case 2:
			autonomousCommand = new AutoMidSwitchProfiling();
			break;
		case 3:
			autonomousCommand = new AutoRightScaleProfiling();
			break;
		case 4:
			autonomousCommand = new AutoLeftScaleProfiling();
			break;
		case 5:
			autonomousCommand = new AutoTwoSwitchCube();
			break;
		case 6:
			autonomousCommand = new AutoMidSwitchOnboardProfiling();
			break;
		}

		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			if (RobotMap.ELEVATOR_PID) elevator.setToGoal();
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		motionProfileRunner.control();
		motionProfileOnboardRunner.control();
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
		motionProfileOnboardRunner.reset();
		if (RobotMap.DRIVE_MODE == 1) (new ArcadeDrive()).start();
		else (new TankDrive()).start();
		(new ResetElevator()).start();
		(new ElevatorDrive()).start();
		(new ArmDrive()).start();
		(new IntakeDrive()).start();
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
