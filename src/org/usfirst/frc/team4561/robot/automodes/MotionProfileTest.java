package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.DriveProfile;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Trajectory;

public class MotionProfileTest extends CommandGroup {
	public MotionProfileTest(){
		Trajectory pointsR = Robot.midSwitchLeft.getRightTrajectory();
		Trajectory pointsL = Robot.midSwitchLeft.getLeftTrajectory();
		int count = 3;
		addSequential(new DriveProfile(pointsR, pointsL));
	}
}
