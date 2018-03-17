/**
 * Example logic for firing and managing motion profiles.
 * This example sends MPs, waits for them to finish
 * Although this code uses a CANTalon, nowhere in this module do we changeMode() or call set() to change the output.
 * This is done in Robot.java to demonstrate how to change control modes on the fly.
 * 
 * The only routines we call on Talon are....
 * 
 * changeMotionControlFramePeriod
 * 
 * getMotionProfileStatus		
 * clearMotionProfileHasUnderrun     to get status and potentially clear the error flag.
 * 
 * pushMotionProfileTrajectory
 * clearMotionProfileTrajectories
 * processMotionProfileBuffer,   to push/clear, and process the trajectory points.
 * 
 * getControlMode, to check if we are in Motion Profile Control mode.
 * 
 * Example of advanced features not demonstrated here...
 * [1] Calling pushMotionProfileTrajectory() continuously while the Talon executes the motion profile, thereby keeping it going indefinitely.
 * [2] Instead of setting the sensor position to zero at the start of each MP, the program could offset the MP's position based on current position. 
 */
package org.usfirst.frc.team4561.trajectories;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;
import org.usfirst.frc.team4561.robot.subsystems.DriveTrainPID;
import org.usfirst.frc.team4561.trajectories.MotionProfileRunner.PeriodicRunnable;

import com.ctre.phoenix.motion.*;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;

/**
 * Based on https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/MotionProfile/src/org/usfirst/frc/team217/robot/MotionProfileExample.java
 * @author Kaiz
 *
 */
public class MotionProfileOnboardRunner {
	
	
	public static final double WHEEL_DIAMETER = 3.5; //inches: 5 for Delta, 6 for Kongo, 3.5 for Janderson
	public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
	
	public static final int UNITS_PER_REVOLUTION = (int)((RobotMap.UNITS_PER_10_FEET / 10.0 * (WHEEL_CIRCUMFERENCE / 12.0)) * 0.95);// 3700; //encoder ticks: 7659 for Delta, 8192 for Kongo, 3700 for Janderson
	
	public static Path leftScaleLeft = new LeftScaleLeft();
	public static Path leftScaleRight = new LeftScaleRight();
	public static Path leftScaleTurnAroundS3 = new LeftScaleTurnAroundS3();
	public static Path midSwitchLeft = new MidSwitchLeft();
	public static Path midSwitchRight = new MidSwitchRight();
	public static Path rightScaleLeft = new RightScaleLeft();
	public static Path rightScaleRight = new RightScaleRight();
	public static Path rightScaleTurnAroundS4 = new RightScaleTurnAroundS4();
	public static Path scaleLeftSwitchLeftCube = new ScaleLeftSwitchLeftCube();
	public static Path scaleLeftTurnAround = new ScaleLeftTurnAround();
	public static Path scaleRightSwitchRightCube = new ScaleRightSwitchRightCube();
	public static Path scaleRightTurnAround = new ScaleRightTurnAround();
	public static Path midSwitchLeftReverse = new MidSwitchLeftReverse();
	public static Path midCubePile = new MidCubePile();
	public static Path midCubePileReverse = new MidCubePileReverse();
	public static Path midSwitchRightReverse = new MidSwitchRightReverse();
	
	/**
	 * All the different trajectories the robot can run.
	 */
	public enum TrajectorySelect {
		LeftScaleLeft(leftScaleLeft),
		LeftScaleRight(leftScaleRight),
		LeftScaleTurnAroundS3(leftScaleTurnAroundS3),
		MidSwitchLeft(midSwitchLeft),
		MidSwitchRight(midSwitchRight),
		RightScaleLeft(rightScaleLeft),
		RightScaleRight(rightScaleRight),
		RightScaleTurnAroundS4(rightScaleTurnAroundS4),
		ScaleLeftSwitchLeftCube(scaleLeftSwitchLeftCube),
		ScaleLeftTurnAround(scaleLeftTurnAround),
		ScaleRightSwitchRightCube(scaleRightSwitchRightCube),
		ScaleRightTurnAround(scaleRightTurnAround),
		MidSwitchLeftReverse(midSwitchLeftReverse),
		MidCubePile(midCubePile),
		MidCubePileReversed(midCubePileReverse),
		MidSwitchRightReverse(midSwitchRightReverse);
		
		Path trajectory;
		TrajectorySelect(Path trajectory) {
			this.trajectory = trajectory;
		}
		/**
		 * @return The trajectory for left side of the drivetrain.
		 */
		private Trajectory getLeftTrajectory() {
			return trajectory.getLeftTrajectory();
		}
		/**
		 * @return The trajectory for right side of the drivetrain.
		 */
		private Trajectory getRightTrajectory() {
			return trajectory.getRightTrajectory();
		}
		/**
		 * @return An array of waypoints for the left side of the trajectory, each waypoint containing position, velocity, and time step. 
		 */
		private double[][] getLeftArray() {
			return trajectory.getLeftArray();
		}
		/**
		 * @return An array of waypoints for the right side of the trajectory, each waypoint containing position, velocity, and time step. 
		 */
		private double[][] getRightArray() {
			return trajectory.getRightArray();
		}
		/**
		 * @return The number of waypoints in the trajectory. This will be the length of the left and right arrays.
		 */
		private int getCount() {
			return trajectory.getCount();
		}
		/**
		 * @return Whether or not the robot goes backwards for this trajectory.
		 */
		private boolean isReversed() {
			return trajectory.isReversed();
		}
		/**
		 * @return The first position in the left trajectory.
		 */
		public double getLeftArrayFirstPosition() {
			return trajectory.getLeftArray()[0][0];
		}
		/**
		 * @return The last position in the left trajectory.
		 */
		public double getLeftArrayLastPosition() {
			return trajectory.getLeftArray()[trajectory.getCount()-1][0];
		}
	}
	
	/**
	 * The trajectory we are running right now.
	 */
	private TrajectorySelect currentTrajectory;
	
	/**
	 * Sets the trajectory that the robot should run next.
	 * @param traj
	 */
	public void setCurrentTrajectory(TrajectorySelect traj) {
		currentTrajectory = traj;
	}
	/**
	 * Gets the trajectory that the robot is ready to run next.
	 * @param traj
	 */
	public TrajectorySelect getCurrentTrajectory() {
		return currentTrajectory;
	}

	/** Additional cache for holding the active trajectory points */
	double leftPos = 0, leftVel = 0, leftHeading = 0, rightPos = 0, rightVel = 0, rightHeading = 0;

	/**
	 * Create a notifier to ensure that our method for setting motor speeds is being called
	 * at a constant, reliable rate.
	 */
	class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {
	    	control();
	    }
	}
	Notifier notifier = new Notifier(new PeriodicRunnable());
	
	/**
	 * Reference to the talons we plan on manipulating. We will not changeMode()
	 * or call set(), just get motion profile status and make decisions based on
	 * motion profile.
	 */
	private TalonSRX leftTalon;
	private TalonSRX rightTalon;
	
	/**
	 * Encoder followers to calculate the voltages to set our talons to.
	 */
	EncoderFollower leftFollower = new EncoderFollower();
	EncoderFollower rightFollower = new EncoderFollower();
	
	double MAX_UNITS_PER_100MS = 4011; // 4011 for Janderson, 6450 for Kongo;
	double MAX_UNITS_PER_SECOND = MAX_UNITS_PER_100MS * 10;
	double MAX_REVOLUTIONS_PER_SECOND = MAX_UNITS_PER_SECOND / UNITS_PER_REVOLUTION;
	double MAX_INCHES_PER_SECOND = MAX_REVOLUTIONS_PER_SECOND * WHEEL_DIAMETER * Math.PI;
	double MAX_FEET_PER_SECOND = MAX_INCHES_PER_SECOND / 12;
	
	private final double kP = 0.25; // Proportional gain: 0.25 for Kongo
	private final double kI = 0; // Integral gain (UNIMPLEMENTED in EncoderFollower)
	private final double kD = 0; // Derivative gain
	private final double kV = 1 / MAX_FEET_PER_SECOND; // TODO: Find drivetrain max units/100ms in speed gear (6450 for Kongo)
	private final double kA = 0; // Should be able to be left at 0 if max accel is configured correctly in trajectory config
	private final double kG = 0.075; // Gyro gain: 0.075 for Kongo
	
	/**
	 * If start() gets called, this flag is set and in the control() we will
	 * service it.
	 */
	private boolean start = false;
	
	/**
	 * @param leftTalon reference to left master TalonSRX object.
	 * @param rightTalon reference to the right master TalonSRX object.
	 * @param leftArray array containing the left motion profile
	 * @param rightArray array containing the right motion profile
	 */
	public MotionProfileOnboardRunner(TalonSRX leftTalon, TalonSRX rightTalon) {
		this.leftTalon = leftTalon;
		this.rightTalon = rightTalon;
		leftFollower.configureEncoder(Robot.driveTrain.getLeftPos(), UNITS_PER_REVOLUTION, WHEEL_DIAMETER / 12);
		rightFollower.configureEncoder(Robot.driveTrain.getRightPos(), UNITS_PER_REVOLUTION, WHEEL_DIAMETER / 12);
		leftFollower.configurePIDVA(kP, kI, kD, kV, kA);
		rightFollower.configurePIDVA(kP, kI, kD, kV, kA);
		notifier.startPeriodic(RobotMap.TIME_STEP); // TODO: Try turning up the time step and see how things change
	}
	
	/**
	 * Called to clear Motion profile buffer and reset state info during
	 * disabled and when Talon is not in MP control mode.
	 */
	public void reset() {
		leftFollower.configureEncoder(Robot.driveTrain.getLeftPos(), UNITS_PER_REVOLUTION, WHEEL_DIAMETER / 12);
		rightFollower.configureEncoder(Robot.driveTrain.getRightPos(), UNITS_PER_REVOLUTION, WHEEL_DIAMETER / 12);
		Robot.driveTrain.invertLeftSide(DriveTrainPID.LEFT_SIDE_INVERTED);
		Robot.driveTrain.invertRightSide(DriveTrainPID.RIGHT_SIDE_INVERTED);
		start = false;
	}

	/**
	 * Must be called every loop.
	 */
	public void control() {
		// TODO: Does not respect reversed trajectories.
		if (start) {
			double leftOutputRaw = leftFollower.calculate(Robot.driveTrain.getLeftPos());
			double rightOutputRaw = rightFollower.calculate(Robot.driveTrain.getRightPos());
			
			double gyro_heading = Robot.gyro.getYaw(); // Get our angle in degrees from -180..180
			double desired_heading = Pathfinder.r2d(leftFollower.getHeading());

			double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
			double turn = kG * (-1.0/80.0) * angleDifference;
			SmartDashboard.putNumber("turn", turn);
			double leftOutput = leftOutputRaw + turn;
			double rightOutput = rightOutputRaw - turn;
			
			leftTalon.set(ControlMode.PercentOutput, -leftOutput);
			rightTalon.set(ControlMode.PercentOutput, -rightOutput);
			
			/* Get the motion profile status every loop */
			//leftHeading = leftFollower.getHeading();
			//leftPos = leftFollower.getSegment().position;
			//leftVel = leftFollower.getSegment().velocity;
			
			//rightHeading = rightFollower.getHeading();
			//rightPos = rightFollower.getSegment().position;
			//rightVel = rightFollower.getSegment().velocity;
			
			if (leftFollower.isFinished() && rightFollower.isFinished()) {
				System.out.println("Trajectory complete.");
				Robot.driveTrain.stop();
				start = false;
			}
		}
		// TODO: Instrumentation implementation for onboard
	}

	/**
	 * Call to start running the current trajectory.
	 */
	public void startMotionProfile() {
		leftFollower.setTrajectory(getCurrentTrajectory().getLeftTrajectory());
		rightFollower.setTrajectory(getCurrentTrajectory().getRightTrajectory());
		leftFollower.configureEncoder(Robot.driveTrain.getLeftPos(), UNITS_PER_REVOLUTION, WHEEL_DIAMETER / 12);
		rightFollower.configureEncoder(Robot.driveTrain.getRightPos(), UNITS_PER_REVOLUTION, WHEEL_DIAMETER / 12);
		start = true;
	}
	
	public boolean isFinished() {
		return leftFollower.isFinished() && rightFollower.isFinished();
	}
	
	/**
	 * Converts feet to encoder units.
	 * Uses {@value #WHEEL_DIAMETER}" for wheel diameter and {@value #UNITS_PER_REVOLUTION} for encoder units per revolution.
	 * @param feet
	 * @return encoder units
	 */
	public static double ft2Units(double feet) {
		feet *= 12; // inches
		feet /= WHEEL_DIAMETER * Math.PI; // revolutions
		feet *= UNITS_PER_REVOLUTION; // Units
		return feet;
	}

	/**
	 * Converts feet to encoder units.
	 * Uses {@value #WHEEL_DIAMETER}" for wheel diameter and {@value #UNITS_PER_REVOLUTION} for encoder units per revolution.
	 * @param feet
	 * @return encoder units
	 */
	public static double units2Ft(double units) {
		units /= UNITS_PER_REVOLUTION; // revolutions
		units *= WHEEL_DIAMETER * Math.PI; // inches
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
		fps /= WHEEL_DIAMETER * Math.PI; // revolutions/100ms
		fps *= UNITS_PER_REVOLUTION; // Units/100ms
		return fps;
	}
}
