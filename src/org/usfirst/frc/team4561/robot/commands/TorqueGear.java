package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TorqueGear extends Command {
	private WPI_TalonSRX motorTwo = new WPI_TalonSRX(RobotMap.TRANSMISSION_TORQUE_BUTTON);
	
	public TorqueGear() {
		requires(Robot.transmission);
	}
	
protected void initialize() {
	setTimeout(1);
	double transmission = 0;
	SmartDashboard.putNumber("TorqueGear initializes", transmission);
	if (RobotMap.TRANSMISSION_DEBUG) {
		System.out.println("[C:TorqueGear] Command initializes.");
	}
}

protected void execute() {
	Robot.transmission.torqueGear();
	String transmission = Robot.transmission.getTransMode();
	SmartDashboard.putString("Transmission Mode", transmission);
	if (RobotMap.TRANSMISSION_DEBUG) {
		System.out.println("[TorqueGear] Command executes." );
	}
}

protected boolean isFinished1() {
	return isTimedOut();
}

protected void end() {
	
	Robot.transmission.stop();
	if (RobotMap.TRANSMISSION_DEBUG) {
		System.out.println("C[TorqueGear] Command finished.");
	}
}

protected boolean isFinished() {
		return false;
	}


protected void interrupted() {
	double transmission = 0;
	SmartDashboard.putNumber("TorqueGear ends", transmission);
	if (RobotMap.TRANSMISSION_DEBUG) {
		System.out.println("C[TorqueGear] Command stop.");
	end();
	}
  }
}

