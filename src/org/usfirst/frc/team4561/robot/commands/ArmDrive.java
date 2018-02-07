package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ArmDrive extends Command {

	public ArmDrive(){
		requires(Robot.armPID);
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void execute(){
		if (Robot.oi.getLeftButton(3)){
			Robot.armPID.increaseGoal();
		}
		if (Robot.oi.getLeftButton(4)){
			Robot.armPID.decreaseGoal();
		}
		if (Robot.oi.getLeftButton(2)){
			Robot.armPID.IntakePosition();
		}
		if (Robot.oi.getRightButton(2)){
			Robot.armPID.ReleasePosition();
		}
		Robot.armPID.setToGoal();
		if (Robot.armPID.getFwdSwitch()){
			Robot.armPID.setEncoderPos(1120);
		}
	}
	
	protected void stop(){
		Robot.armPID.stop();
	}
}
