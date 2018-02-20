package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;
import org.usfirst.frc.team4561.robot.commands.ArmReleasePosition;
import org.usfirst.frc.team4561.robot.commands.DriveMagic;
import org.usfirst.frc.team4561.robot.commands.ElevatorGroundPosition;
import org.usfirst.frc.team4561.robot.commands.ElevatorScalePosition;
import org.usfirst.frc.team4561.robot.commands.IntakeRelease;
import org.usfirst.frc.team4561.robot.commands.TankDriveTimed;
import org.usfirst.frc.team4561.robot.commands.TorqueGear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * This automode places a power cube on the scale if the robot is on the right side of the Arcade.
 * This is currently in progress.
 * @author Ben
 */
public class AutoScaleRightPosition extends CommandGroup {

	double delay = Robot.oi.getDashboardDelaySlider();
	
    public AutoScaleRightPosition() {
        
    	addSequential(new TorqueGear());
    	// wait preassigned time
    	addSequential(new WaitCommand(delay));
    
    	// if our side is the right
    	if ((Robot.scaleFMSSideRight)) {
    		addSequential(new DriveMagic(200, 200)); // forward
    		addSequential(new DriveMagic(-(int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4), (int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4))); // right
    		addSequential(new DriveMagic(10,10)); // forward
    		addSequential(new DriveMagic((int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4),-(int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4)));
    		addSequential(new DriveMagic(96,96));
    		addSequential(new ElevatorScalePosition()); // elevate
    		addSequential(new DriveMagic(10,10));
    		addSequential(new ArmReleasePosition());
    		addSequential(new IntakeRelease()); // drop power cube
    	}
    	// else
    	else {
    		addSequential(new DriveMagic(100, 100)); // forward
    		addSequential(new DriveMagic(-(int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4), (int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4))); // right
    		addSequential(new DriveMagic(337, 337)); // forward
    		addSequential(new DriveMagic((int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4), -(int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4))); // left
    		addSequential(new DriveMagic(196, 196)); // forward
    		addSequential(new ElevatorScalePosition());
    		addSequential(new DriveMagic(10,10));
    		addSequential(new ArmReleasePosition());
    		addSequential(new IntakeRelease()); // drop power cube
    		addSequential(new ElevatorGroundPosition()); // put the elevator down
    	}
    }
}
