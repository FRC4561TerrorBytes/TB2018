package org.usfirst.frc.team4561.trajectories;

import org.usfirst.frc.team4561.robot.RobotMap;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class Path {
	
	// Sample Counts: SAMPLES_HIGH (100 000), SAMPLES_LOW  (10 000), SAMPLES_FAST (1 000)
	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH,
			RobotMap.TIME_STEP, RobotMap.MAX_VELOCITY, RobotMap.MAX_ACCELERATION, RobotMap.MAX_JERK);
	Waypoint[] points;
    Trajectory trajectory;
    TankModifier modifier;
    Trajectory left;
    Trajectory right;

	public Trajectory getLeftTrajectory() {
		return left;
	}

	public Trajectory getRightTrajectory() {
		return right;
	}
}
