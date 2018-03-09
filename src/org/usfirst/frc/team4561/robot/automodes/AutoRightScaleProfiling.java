package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.ArmReleasePosition;
import org.usfirst.frc.team4561.robot.commands.ArmVertical;
import org.usfirst.frc.team4561.robot.commands.DriveMagic;
import org.usfirst.frc.team4561.robot.commands.ElevatorGroundPosition;
import org.usfirst.frc.team4561.robot.commands.ElevatorScalePosition;
import org.usfirst.frc.team4561.robot.commands.IntakeRelease;
import org.usfirst.frc.team4561.robot.commands.IntakeStop;
import org.usfirst.frc.team4561.robot.commands.RunTrajectory;
import org.usfirst.frc.team4561.robot.commands.SpeedGear;
import org.usfirst.frc.team4561.robot.commands.WaitUntilPositionPercent;
import org.usfirst.frc.team4561.robot.commands.WaitUntilTrajectoryFinished;
import org.usfirst.frc.team4561.trajectories.MotionProfileRunner;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoRightScaleProfiling extends CommandGroup {
double delay = Robot.oi.getDashboardDelaySlider();
	
    public AutoRightScaleProfiling() {
        
    	addSequential(new SpeedGear());
		addSequential(new ArmVertical());

    	// wait preassigned time
    	addSequential(new WaitCommand(delay));
    
    	// get side of switch from FMS
    	//addSequential(new CheckSwitchSide());
    	// on the left
    	if (!(Robot.switchFMSSideRight)) {
    		addParallel(new RunTrajectory(MotionProfileRunner.TrajectorySelect.RightScaleLeft));
    		addSequential(new WaitUntilPositionPercent(0.5, MotionProfileRunner.TrajectorySelect.RightScaleLeft.getLeftArrayFirstPosition(), MotionProfileRunner.TrajectorySelect.RightScaleLeft.getLeftArrayLastPosition()));
    		addSequential(new ElevatorScalePosition());
    		addSequential(new ArmReleasePosition());
    		addSequential(new WaitUntilTrajectoryFinished());
    		addSequential(new IntakeRelease()); // drop power cubeq
    		addSequential(new WaitCommand(0.5));
    		addSequential(new IntakeStop());
    		addSequential(new DriveMagic(-10, -10));
    		addSequential(new ArmVertical());
    		addSequential(new ElevatorGroundPosition());
    	}
    	// on the right
    	else {
    		addParallel(new RunTrajectory(MotionProfileRunner.TrajectorySelect.RightScaleRight));
    		addSequential(new WaitUntilPositionPercent(0.5, MotionProfileRunner.TrajectorySelect.RightScaleRight.getLeftArrayFirstPosition(), MotionProfileRunner.TrajectorySelect.RightScaleRight.getLeftArrayLastPosition()));
    		addSequential(new ElevatorScalePosition());
    		addSequential(new ArmReleasePosition());
    		addSequential(new WaitUntilTrajectoryFinished());
    		addSequential(new IntakeRelease()); // drop power cubeq
    		addSequential(new WaitCommand(0.5));
    		addSequential(new IntakeStop());
    		addSequential(new DriveMagic(-10, -10));
    		addSequential(new ArmVertical());
    		addSequential(new ElevatorGroundPosition());
    	}
    }
}
