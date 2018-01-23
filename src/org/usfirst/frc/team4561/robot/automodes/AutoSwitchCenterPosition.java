package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.ArmReleasePosition;
import org.usfirst.frc.team4561.robot.commands.CheckSwitchSide;
import org.usfirst.frc.team4561.robot.commands.IntakeRelease;
import org.usfirst.frc.team4561.robot.commands.TankDriveTimed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This automode places a power cube on the switch if the robot is in the center of the arcade.
 * @author Ben
 */
public class AutoSwitchCenterPosition extends CommandGroup {

    public AutoSwitchCenterPosition() {
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
    	requires(Robot.DriveTrain);
    	requires(Robot.ArmPID);
    	requires(Robot.Intake);
    	addSequential(new CheckSwitchSide());
    	if (Robot.switchFMSSideRight) {
    		addSequential(new TankDriveTimed(1, 1, 1)); // forward
    		addSequential(new TankDriveTimed(1, 0, 0.25)); // turn right
    		addSequential(new TankDriveTimed(1, 1, 1.5)); // forward
    		addSequential(new TankDriveTimed(0, 1, 0.25)); // turn left
    		addSequential(new TankDriveTimed(1, 1, 1.25)); // forward
    		addSequential(new TankDriveTimed(0, 1, 0.25)); // turn left
    		addSequential(new TankDriveTimed(1, 1, 0.25)); // forward
    		addSequential(new ArmReleasePosition()); // lift arm
    		addSequential(new IntakeRelease()); // drop power cube
    	}
    	else {
    		addSequential(new TankDriveTimed(1, 1, 1)); // forward
    		addSequential(new TankDriveTimed(0, 1, 0.25)); // turn left
    		addSequential(new TankDriveTimed(1, 1, 1.5)); // forward
    		addSequential(new TankDriveTimed(1, 0, 0.25)); // turn right
    		addSequential(new TankDriveTimed(1, 1, 1.25)); // forward
    		addSequential(new TankDriveTimed(1, 0, 0.25)); // turn right
    		addSequential(new TankDriveTimed(1, 1, 0.25)); // forward
    		addSequential(new ArmReleasePosition()); // lift arm
    		addSequential(new IntakeRelease()); // drop power cube
    	}
    }
}
