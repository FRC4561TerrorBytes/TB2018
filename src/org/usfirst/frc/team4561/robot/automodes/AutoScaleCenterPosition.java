package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * This automode places a power cube on the scale if the robot is in the center of the Arcade.
 * This is currently in progress.
 * @author Ben
 */
public class AutoScaleCenterPosition extends CommandGroup {

	double delay = Robot.oi.getDashboardDelaySlider();
	
    public AutoScaleCenterPosition() {
    	addSequential(new TorqueGear());
    	// wait preassigned time
    	addSequential(new WaitCommand(delay));
    	// if our side is the right
    	if (Robot.scaleFMSSideRight) {
    		addSequential(new TankDriveTimed(1, 1, 1)); // forward
    		addSequential(new TankDriveTimed(1, 0, 0.25)); // right
    		addSequential(new TankDriveTimed(1, 1, 1)); // forward
    		addSequential(new TankDriveTimed(0, 1, 0.25)); // left
    		addSequential(new TankDriveTimed(1, 1, 2)); // forward
    		addSequential(new TankDriveTimed(0, 1, 0.25)); // left
    		addSequential(new TankDriveTimed(1, 1, 0.25)); // forward
    		addSequential(new ArmReleasePosition());
    		addSequential(new IntakeRelease()); // drop power cube
    		addSequential(new ElevatorGroundPosition()); // put the elevator down
    	}
    	// if we are on the left
    	else {
    		addSequential(new TankDriveTimed(1, 1, 1)); // forward
    		addSequential(new TankDriveTimed(0, 1, 0.25)); // left
    		addSequential(new TankDriveTimed(1, 1, 1)); // forward
    		addSequential(new TankDriveTimed(1, 0, 0.25)); // right
    		addSequential(new TankDriveTimed(1, 1, 2)); // forward
    		addSequential(new TankDriveTimed(1, 0, 0.25)); // right
    		addSequential(new TankDriveTimed(1, 1, 0.25)); // forward
    		addSequential(new ArmReleasePosition());
    		addSequential(new IntakeRelease()); // drop power cube
    		addSequential(new ElevatorGroundPosition()); // put the elevator down
    	}
    }
}
