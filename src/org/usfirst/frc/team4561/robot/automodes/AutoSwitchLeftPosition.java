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
 * This automode puts a block on the switch if the robot is on the left side of the field.
 * This is currently in progress.
 * @author Ben
 */
public class AutoSwitchLeftPosition extends CommandGroup {

	double delay = Robot.oi.getDashboardDelaySlider();
	
    public AutoSwitchLeftPosition() {
        
    	addSequential(new TorqueGear());
    	// wait preassigned time
    	addSequential(new WaitCommand(delay));
    
    	// get side of switch from FMS
    	addSequential(new CheckSwitchSide());
    	// on the left
    	if (!(Robot.switchFMSSideRight)) {
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new ArmReleasePosition());
    		addSequential(new IntakeRelease()); // drop power cube
    	}
    	// on the right
    	else {
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new TurnToArbritraryAngleMagic(90)); // turn right
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new TurnToArbritraryAngleMagic(270)); // turn left
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new ArmReleasePosition());
    		addSequential(new IntakeRelease()); // drop power cube
    	}
    }
}
