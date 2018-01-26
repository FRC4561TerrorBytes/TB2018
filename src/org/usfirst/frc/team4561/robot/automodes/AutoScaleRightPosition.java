package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.ArmReleasePosition;
import org.usfirst.frc.team4561.robot.commands.ElevatorGroundPosition;
import org.usfirst.frc.team4561.robot.commands.ElevatorScalePosition;
import org.usfirst.frc.team4561.robot.commands.IntakeRelease;
import org.usfirst.frc.team4561.robot.commands.TankDriveTimed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This automode places a power cube on the scale if the robot is on the right side of the Arcade.
 * This is currently in progress.
 * @author Ben
 */
public class AutoScaleRightPosition extends CommandGroup {

    public AutoScaleRightPosition() {
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
    	
    	// we require DriveTrain, ArmPID, Intake
    	requires(Robot.DriveTrain);
    	requires(Robot.ArmPID);
    	requires(Robot.Intake);
    	requires(Robot.ElevatorPID);
    	// if our side is the right
    	if (Robot.scaleFMSSideRight) {
    		addSequential(new TankDriveTimed(1, 1, 3)); // forward
    		addSequential(new TankDriveTimed(0, 1, 0.25)); // left
    		addSequential(new TankDriveTimed(1, 1, 0.25)); // forward
    		addSequential(new ElevatorScalePosition()); // elevate
    		addSequential(new ArmReleasePosition()); // lift arm
    		addSequential(new IntakeRelease()); // drop power cube
    		addSequential(new ElevatorGroundPosition()); // put the elevator down
    	}
    	// else
    	else {
    		addSequential(new TankDriveTimed(1, 1, 1)); // forward
    		addSequential(new TankDriveTimed(0, 1, 0.25)); // left
    		addSequential(new TankDriveTimed(1, 1, 2)); // forward
    		addSequential(new TankDriveTimed(1, 0, 0.25)); // right
    		addSequential(new TankDriveTimed(1, 1, 2)); // forward
    		addSequential(new TankDriveTimed(1, 0, 0.25)); // right
    		addSequential(new TankDriveTimed(1, 1, 0.25)); // forward
    		addSequential(new ElevatorScalePosition()); // elevate
    		addSequential(new ArmReleasePosition()); // lift arm
    		addSequential(new IntakeRelease()); // drop power cube
    		addSequential(new ElevatorGroundPosition()); // put the elevator down
    	}
    	
    }
}
