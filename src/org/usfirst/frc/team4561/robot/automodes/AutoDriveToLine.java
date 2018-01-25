package org.usfirst.frc.team4561.robot.automodes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team4561.robot.commands.TorqueGear;
import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.TankDriveTimed;
/**
 * This auto mode drives past the auto line if the robot is on the left or right side of the field.
 * @author Ben
 */
public class AutoDriveToLine extends CommandGroup {
	public AutoDriveToLine() {
		// we need DriveTrain and transmission
		requires(Robot.DriveTrain);
		requires(Robot.transmission);
		// torque mode
		addSequential(new TorqueGear());
		// cross the auto line and score points
		addSequential(new TankDriveTimed(1, 1, 2));
		// addSequential(new WaitCommand(3));
		// done
	}
}
