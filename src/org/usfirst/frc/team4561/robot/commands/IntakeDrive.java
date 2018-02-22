package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeDrive extends Command {

	public IntakeDrive(){
		requires(Robot.intake);
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	protected void execute(){
		if (Robot.oi.getRightButton(RobotMap.INTAKE_BUTTON)){
			Robot.intake.intakeIn();
		}
		else if (Robot.oi.getLeftButton(RobotMap.OUTTAKE_FULL_BUTTON)){
			Robot.intake.release();
		}
		else if (Robot.oi.getLeftButton(RobotMap.RELEASE_BUTTON)){
			Robot.intake.intakeOutHalf();
		}
		else{
			Robot.intake.set(Robot.oi.getControllerLTrigger(), Robot.oi.getControllerRTrigger());
		}
	}
}
