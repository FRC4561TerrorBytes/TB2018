package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.commands.*;
import org.usfirst.frc.team4561.trajectories.*;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.*;

/**
 * Two Cube Auto sequence 7 using Motion Profiling.
 * @author Ben
 */
public class TwoCubeAutoS7MP extends CommandGroup {

    public TwoCubeAutoS7MP() {
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
    	Trajectory pointsL = new RightScaleLeft().getLeftTrajectory();
    	Trajectory pointsR = new RightScaleLeft().getRightTrajectory();
    	double start = pointsL.get(0).position;
    	double end = pointsR.get(pointsL.length()-1).position;
    	addSequential(new DriveProfile(pointsR, pointsL)); // run RightScaleLeft
    	addSequential(new WaitUntilPositionPercent(0.5, start, end)); // run RightScaleLeft
    	addSequential(new ElevatorScalePosition()); // elevator to scale
    	addSequential(new ArmReleasePosition()); // arm to release
    	addSequential(new IntakeOutFull()); // pop cube out
    	pointsL = new ScaleLeftTurnAround().getLeftTrajectory();
    	pointsR = new ScaleLeftTurnAround().getRightTrajectory();
    	start = pointsL.get(0).position;
    	end = pointsR.get(pointsL.length()-1).position;
    	addSequential(new DriveProfile(pointsR, pointsL)); // run ScaleLeftTurnAround
    	addSequential(new WaitUntilPositionPercent(0.5, start, end)); // run ScaleLeftTurnAround
    	addSequential(new ElevatorGroundPosition()); // elevator to ground
    	addSequential(new ArmIntakePosition()); // arm to intake
    	addSequential(new IntakeIn()); // activate intake
    	pointsL = new ScaleLeftSwitchLeftCube().getLeftTrajectory();
    	pointsR = new ScaleLeftSwitchLeftCube().getRightTrajectory();
    	start = pointsL.get(0).position;
    	end = pointsR.get(pointsL.length()-1).position;
    	addSequential(new DriveProfile(pointsR, pointsL)); // run ScaleLeftSwitchLeftCube
    	addSequential(new WaitUntilPositionPercent(0.5, start, end)); // run ScaleLeftSwitchLeftCube
    	addSequential(new ElevatorSwitchPosition()); // elevator to switch
    	addSequential(new ArmReleasePosition()); // arm to release
    	addSequential(new IntakeOutFull()); // pop cube out
    }
}
