package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class TorqueGear extends Command {
	
	public TorqueGear() {
		requires(Robot.transmission);
	}
	
protected void initialize() {
	setTimeout(1);
	if (RobotMap.TRANSMISSION_VERBOSE) {
		System.out.println("[C:TorqueGear] Command finished.");
	}
}

protected void execute() {
	Robot.transmission.torqueGear();
}

protected boolean isFinished1() {
	return isTimedOut();
}

protected void end() {
	Robot.transmission.stop();
	if (RobotMap.TRANSMISSION_VERBOSE) {
		System.out.println("C[TorqueGear] Command finished.");
	}
}

protected boolean isFinished() {
		return false;
	}


protected void interrupted() {
	end();
	}
}

