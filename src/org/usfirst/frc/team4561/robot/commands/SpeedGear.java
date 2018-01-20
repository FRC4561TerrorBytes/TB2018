package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class SpeedGear extends Command {
	
	private Object robot;

	public SpeedGear() {
		requires(Robot.transmission);
	}

	
protected void initialize()  {
	setTimeout(1);
	if (RobotMap.TRANSMISSION_VERBOSE)  {
		System.out.println("[C:SpeedGear] Initializing...");
	}
}

	
protected void execute() {
	Robot.transmission.speedGear();
}


protected boolean isFinished1() {
	return isTimedOut();
	}

protected void end() {
	Robot.transmission.stop();
	if (RobotMap.TRANSMISSION_VERBOSE)
		System.out.println("C:SpeedGear]  Command finished.");
}


	protected boolean isFinished() {
		return false;
	}

}
