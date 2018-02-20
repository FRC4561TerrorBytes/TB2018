package org.usfirst.frc.team4561.robot.automodes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team4561.robot.commands.TorqueGear;
import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.commands.DriveMagic;
import org.usfirst.frc.team4561.robot.commands.PositionMode;
import org.usfirst.frc.team4561.robot.commands.TankDriveTimed;
/**
 * This auto mode drives past the auto line if the robot is on the left or right side of the field.
 * @author Ben
 */
public class AutoDriveToLine extends CommandGroup {
	
	double delay = Robot.oi.getDashboardDelaySlider();
	
	public AutoDriveToLine() {
		// torque mode
		addSequential(new TorqueGear());
		// wait preassigned time
    	addSequential(new WaitCommand(delay));
		// cross the auto line and score points
    	//System.out.println("Running");
		addSequential(new DriveMagic(200, 200));
//		addSequential(new WaitCommand(3));
		addSequential(new DriveMagic(-36,36));
		//addSequential(new TankDriveTimed(1, 1, 1));
		// addSequential(new WaitCommand(3));
		// done
	}
}
