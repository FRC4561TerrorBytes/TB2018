package org.usfirst.frc.team4561.robot.automodes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team4561.robot.commands.TorqueGear;
import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.TankDriveTimed;
/**
 * This auto mode drives past the auto line if the robot is in the center of the field.
 * This is currently in progress.
 * @author Ben
 */
public class AutoDriveToLineCenter extends CommandGroup {
	public AutoDriveToLineCenter() {
		// we need DriveTrain and transmission
		requires(Robot.DriveTrain);
		requires(Robot.transmission);
		// torque mode
		addSequential(new TorqueGear());
		// forward
		addSequential(new TankDriveTimed(1, 1, 1));
		// turn right
		addSequential(new TankDriveTimed(1, 0.5, 1));
		// forward
		addSequential(new TankDriveTimed(1, 1, 1));
		// turn left
		addSequential(new TankDriveTimed(0.5, 1, 1));
		// cross the line and score points
		addSequential(new TankDriveTimed(1, 1, 1));
		// addSequential(new WaitCommand(3));
		// done
	}
}
