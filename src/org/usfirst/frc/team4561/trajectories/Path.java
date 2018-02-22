package org.usfirst.frc.team4561.trajectories;

import jaci.pathfinder.Trajectory;

public abstract class Path {
	public abstract Trajectory getLeftTrajectory();
	public abstract Trajectory getRightTrajectory();
}
