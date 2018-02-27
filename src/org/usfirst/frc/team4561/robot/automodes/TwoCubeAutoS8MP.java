package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.commands.*;
import org.usfirst.frc.team4561.trajectories.*;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.*;

/**
 * Two Cube Auto sequence 8 using Motion Profiling.
 * @author Ben
 */
public class TwoCubeAutoS8MP extends CommandGroup {

    public TwoCubeAutoS8MP() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	Trajectory pointsL = new LeftScaleRight().getLeftTrajectory();
    	Trajectory pointsR = new LeftScaleRight().getRightTrajectory();
    	double start = pointsL.get(0).position;
    	double end = pointsR.get(pointsL.length()-1).position;
    	addSequential(new DriveProfile(pointsR, pointsL)); // run LeftScaleRight
    	addSequential(new WaitUntilPositionPercent(0.5, start, end)); // run LeftScaleRight
    	addSequential(new ElevatorScalePosition()); // elevator to scale
    	addSequential(new ArmReleasePosition()); // arm to release
    	addSequential(new IntakeOutFull()); // pop cube out
    	pointsL = new ScaleRightTurnAround().getLeftTrajectory();
    	pointsR = new ScaleRightTurnAround().getRightTrajectory();
    	start = pointsL.get(0).position;
    	end = pointsR.get(pointsL.length()-1).position;
    	addSequential(new DriveProfile(pointsR, pointsL)); // run ScaleRightTurnAround
    	addSequential(new WaitUntilPositionPercent(0.5, start, end)); // run ScaleRightTurnAround
    	addSequential(new ElevatorGroundPosition()); // elevator to ground
    	addSequential(new ArmIntakePosition()); // arm to intake
    	addSequential(new IntakeIn()); // activate intake
    	pointsL = new ScaleRightSwitchRightCube().getLeftTrajectory();
    	pointsR = new ScaleRightSwitchRightCube().getRightTrajectory();
    	start = pointsL.get(0).position;
    	end = pointsR.get(pointsL.length()-1).position;
    	addSequential(new DriveProfile(pointsR, pointsL)); // run ScaleRightSwitchRightCube
    	addSequential(new WaitUntilPositionPercent(0.5, start, end)); // run ScaleRightSwitchLeftCube
    	addSequential(new ElevatorSwitchPosition()); // elevator to switch
    	addSequential(new ArmReleasePosition()); // arm to release
    	addSequential(new IntakeOutFull()); // pop cube out
    }
}
