package org.usfirst.frc.team4561.robot.automodes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team4561.robot.commands.TorqueGear;
import org.usfirst.frc.team4561.robot.commands.TurnToArbritraryAngleMagic;
import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;
import org.usfirst.frc.team4561.robot.commands.DriveMagic;
import org.usfirst.frc.team4561.robot.commands.TankDriveTimed;
/**
 * This auto mode drives past the auto line if the robot is in the center of the field.
 * This is currently in progress.
 * @author Ben
 */
public class AutoDriveToLineCenter extends CommandGroup {
	
	double delay = Robot.oi.getDashboardDelaySlider();
	
	public AutoDriveToLineCenter() {
		// torque mode
		addSequential(new TorqueGear());
		// wait preassigned time
    	addSequential(new WaitCommand(delay));
		// forward
		addSequential(new DriveMagic(100, 100));
		// turn right
		addSequential(new DriveMagic((int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4), -(int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4)));
		// forward
		addSequential(new DriveMagic(25, 25));
		// turn left
		addSequential(new DriveMagic(-(int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4), (int) (RobotMap.DRIVETRAIN_CIRCUMFERENCE/4)));
		// cross the line and score points
		addSequential(new DriveMagic(30, 30));
		// addSequential(new WaitCommand(3));
		// done
	}
}
