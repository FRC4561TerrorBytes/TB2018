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
		Robot.armPID.setToGoal();
	}
	
	protected void stop(){
		Robot.armPID.stop();
	}
}
