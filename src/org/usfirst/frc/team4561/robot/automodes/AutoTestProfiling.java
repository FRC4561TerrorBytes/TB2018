package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.commands.ArmReleasePosition;
import org.usfirst.frc.team4561.robot.commands.IntakeRelease;
import org.usfirst.frc.team4561.robot.commands.Nothing;
import org.usfirst.frc.team4561.robot.commands.RunTrajectory;
import org.usfirst.frc.team4561.robot.commands.SpeedGear;
import org.usfirst.frc.team4561.robot.commands.WaitUntilPositionPercent;
import org.usfirst.frc.team4561.robot.commands.WaitUntilTrajectoryFinished;
import org.usfirst.frc.team4561.trajectories.MotionProfileRunner;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoTestProfiling extends CommandGroup {
	public AutoTestProfiling(){
		addSequential(new SpeedGear());
		addSequential(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidSwitchLeft));
		addParallel(new WaitUntilPositionPercent(0.5, new Nothing()));
		addSequential(new WaitUntilTrajectoryFinished());
		addSequential(new Nothing());
	}
}
