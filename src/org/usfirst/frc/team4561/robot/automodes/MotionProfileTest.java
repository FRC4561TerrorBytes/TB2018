package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.DriveProfile;
import org.usfirst.frc.team4561.robot.commands.WaitUntilPositionPercent;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Trajectory;

public class MotionProfileTest extends CommandGroup {
	public MotionProfileTest(){
		Trajectory pointsR = Robot.midSwitchLeft.getRightTrajectory();
		Trajectory pointsL = Robot.midSwitchLeft.getLeftTrajectory();
		double start = pointsL.get(0).position;
		double end = pointsL.get(pointsL.length()-1).position;
		addSequential(new DriveProfile(pointsR, pointsL));
		addSequential(new WaitUntilPositionPercent(0.5, start, end));
	}
}
