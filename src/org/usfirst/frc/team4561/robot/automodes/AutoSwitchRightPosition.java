package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.ArmReleasePosition;
import org.usfirst.frc.team4561.robot.commands.CheckSwitchSide;
import org.usfirst.frc.team4561.robot.commands.DriveMagic;
import org.usfirst.frc.team4561.robot.commands.IntakeRelease;
import org.usfirst.frc.team4561.robot.commands.TankDriveTimed;
import org.usfirst.frc.team4561.robot.commands.TurnToArbritraryAngleMagic;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This automode puts a block on the switch if the robot is on the right side of the field.
 * This is currently in progress.
 * @author Ben
 */
public class AutoSwitchRightPosition extends CommandGroup {

    public AutoSwitchRightPosition() {
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
  
    	// get side of switch from FMS
    	addSequential(new CheckSwitchSide());
    	// on the right
    	if (Robot.switchFMSSideRight) {
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new ArmReleasePosition());
    		addSequential(new IntakeRelease()); // drop power cube
    	}
    	// on the left
    	else {
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new TurnToArbritraryAngleMagic(270)); // turn left
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new TurnToArbritraryAngleMagic(90)); // turn right
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new ArmReleasePosition());
    		addSequential(new IntakeRelease()); // drop power cube
    	}
    }
}
