package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.ArmReleasePosition;
import org.usfirst.frc.team4561.robot.commands.CheckSwitchSide;
import org.usfirst.frc.team4561.robot.commands.DriveMagic;
import org.usfirst.frc.team4561.robot.commands.IntakeRelease;
import org.usfirst.frc.team4561.robot.commands.TankDriveTimed;
import org.usfirst.frc.team4561.robot.commands.TorqueGear;
import org.usfirst.frc.team4561.robot.commands.TurnToArbritraryAngleMagic;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * This automode places a power cube on the switch if the robot is in the center of the arcade.
 * This is currently in progress.
 * @author Ben
 */
public class AutoSwitchCenterPosition extends CommandGroup {

	double delay = Robot.oi.getDashboardDelaySlider();
	
    public AutoSwitchCenterPosition() {
        
    	addSequential(new TorqueGear());
    	// wait preassigned time
    	addSequential(new WaitCommand(delay));
    	
    	// get side of switch from FMS
    	addSequential(new CheckSwitchSide());
    	// on the right
    	if (Robot.switchFMSSideRight) {
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new TurnToArbritraryAngleMagic(90)); // turn right
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new TurnToArbritraryAngleMagic(270)); // turn left
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new TurnToArbritraryAngleMagic(270)); // turn left
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
    		addSequential(new TurnToArbritraryAngleMagic(90)); // turn right
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new ArmReleasePosition());
    		addSequential(new IntakeRelease()); // drop power cube
    	}
    }
}
