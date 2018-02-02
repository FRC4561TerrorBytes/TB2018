package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {

	public TankDrive(){
		requires(Robot.DriveTrain);
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void execute(){
		Robot.DriveTrain.tankDrive(Robot.m_oi.getLeftStickY(), Robot.m_oi.getRightStickY());
	}
	
	protected void stop(){
		Robot.DriveTrain.tankDrive(0, 0);
	}

}
