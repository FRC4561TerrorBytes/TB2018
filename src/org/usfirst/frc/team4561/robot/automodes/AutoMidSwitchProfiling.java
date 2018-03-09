package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;
import org.usfirst.frc.team4561.robot.commands.ArmReleasePosition;
import org.usfirst.frc.team4561.robot.commands.CheckSwitchSide;
import org.usfirst.frc.team4561.robot.commands.DriveMagic;
import org.usfirst.frc.team4561.robot.commands.IntakeRelease;
import org.usfirst.frc.team4561.robot.commands.RunTrajectory;
import org.usfirst.frc.team4561.robot.commands.SpeedGear;
import org.usfirst.frc.team4561.robot.commands.TankDriveTimed;
import org.usfirst.frc.team4561.robot.commands.TorqueGear;
import org.usfirst.frc.team4561.robot.commands.TurnMagic;
import org.usfirst.frc.team4561.robot.commands.WaitUntilPositionPercent;
import org.usfirst.frc.team4561.robot.commands.WaitUntilTrajectoryFinished;
import org.usfirst.frc.team4561.trajectories.MotionProfileRunner;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * This automode puts a block on the switch if the robot is on the left side of the field.
 * This is currently in progress.
 * @author Ben
 */
public class AutoMidSwitchProfiling extends CommandGroup {

	double delay = Robot.oi.getDashboardDelaySlider();
	
    public AutoMidSwitchProfiling() {
        
    	addSequential(new SpeedGear());
    	// wait preassigned time
    	addSequential(new WaitCommand(delay));
    
    	// get side of switch from FMS
    	//addSequential(new CheckSwitchSide());
    	// on the left
    	if (!(Robot.switchFMSSideRight)) {
    		addParallel(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidSwitchLeft));
    		addSequential(new WaitUntilPositionPercent(0.5, MotionProfileRunner.TrajectorySelect.MidSwitchLeft.getLeftArrayFirstPosition(), MotionProfileRunner.TrajectorySelect.MidSwitchLeft.getLeftArrayLastPosition()));
    		addSequential(new ArmReleasePosition());
    		addSequential(new WaitUntilTrajectoryFinished());
    		addSequential(new IntakeRelease()); // drop power cubeq
    	}
    	// on the right
    	else {
    		addParallel(new RunTrajectory(MotionProfileRunner.TrajectorySelect.MidSwitchRight));
    		addSequential(new WaitUntilPositionPercent(0.5, MotionProfileRunner.TrajectorySelect.MidSwitchRight.getLeftArrayFirstPosition(), MotionProfileRunner.TrajectorySelect.MidSwitchRight.getLeftArrayLastPosition()));
    		addSequential(new ArmReleasePosition());
    		addSequential(new WaitUntilTrajectoryFinished());
    		addSequential(new IntakeRelease()); // drop power cubeq
    	}
    }
}