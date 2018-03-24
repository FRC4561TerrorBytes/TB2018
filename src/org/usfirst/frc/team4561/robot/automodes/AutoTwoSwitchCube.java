package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.*;
import org.usfirst.frc.team4561.trajectories.MotionProfileRunner;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoTwoSwitchCube extends CommandGroup {
double delay = Robot.oi.getDashboardDelaySlider();
	
    @SuppressWarnings("deprecation")
	public AutoTwoSwitchCube() {
        
    	addSequential(new SpeedGear());
		addSequential(new ArmVertical());

    	// wait preassigned time
    	addSequential(new WaitCommand(delay));
    
    	// get side of switch from FMS
    	//addSequential(new CheckSwitchSide());
    	// on the left
    	if (!(Robot.switchFMSSideRight)) {
    		addSequential(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidSwitchLeft));
    		addParallel(new WaitUntilPositionPercent(0.5, MotionProfileRunner.TrajectorySelect.MidSwitchLeft.getLeftArrayFirstPosition(), MotionProfileRunner.TrajectorySelect.MidSwitchLeft.getLeftArrayLastPosition(), new ArmReleasePosition()));
    		addSequential(new WaitUntilTrajectoryFinished());
    		addSequential(new IntakeRelease()); // drop power cubeq
    		addSequential(new WaitCommand(0.5));
    		addSequential(new IntakeStop());
    		addParallel(new ArmVertical());
    		addSequential(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidSwitchLeftReverse));
    		addSequential(new ArmIntakePosition());
    		addParallel(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidCubePile));
    		addSequential(new WaitUntilPositionPercent(0.5, MotionProfileRunner.TrajectorySelect.MidCubePile.getLeftArrayFirstPosition(), MotionProfileRunner.TrajectorySelect.MidCubePile.getLeftArrayLastPosition(), new Nothing()));
    		addSequential(new IntakeIn());
    		addSequential(new WaitUntilTrajectoryFinished());
    		addSequential(new IntakeStop());
    		addParallel(new ArmVertical());
    		addSequential(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidCubePileReversed));
    		addParallel(new ArmReleasePosition());
    		addSequential(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidSwitchLeft));
//    		addSequential(new IntakeRelease());
    	}
    	// on the right
    	else {
    		addSequential(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidSwitchRight));
    		addParallel(new WaitUntilPositionPercent(0.5, MotionProfileRunner.TrajectorySelect.MidSwitchRight.getLeftArrayFirstPosition(), MotionProfileRunner.TrajectorySelect.MidSwitchRight.getLeftArrayLastPosition(), new ArmReleasePosition()));
    		addSequential(new WaitUntilTrajectoryFinished());
    		addSequential(new IntakeRelease()); // drop power cubeq
    		addSequential(new WaitCommand(0.5));
    		addSequential(new IntakeStop());
    		addParallel(new ArmVertical());
    		addSequential(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidSwitchRightReverse));
    		addSequential(new ArmIntakePosition());
    		addParallel(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidCubePile));
    		addSequential(new WaitUntilPositionPercent(0.5, MotionProfileRunner.TrajectorySelect.MidCubePile.getLeftArrayFirstPosition(), MotionProfileRunner.TrajectorySelect.MidCubePile.getLeftArrayLastPosition(), new Nothing()));
    		addSequential(new IntakeIn());
    		addSequential(new WaitUntilTrajectoryFinished());
    		addSequential(new IntakeStop());
    		addParallel(new ArmVertical());
    		addSequential(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidCubePileReversed));
    		addParallel(new ArmReleasePosition());
    		addSequential(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidSwitchRight));
//    		addSequential(new IntakeRelease());
    	}
    }
}
