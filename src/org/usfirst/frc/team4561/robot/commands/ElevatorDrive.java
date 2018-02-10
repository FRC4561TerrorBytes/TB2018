package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorDrive extends Command {

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void execute(){
		if (Robot.oi.getControllerButton(1)){
			if (Robot.oi.getControllerRTrigger()){
				Robot.elevator.ScalePositionLow();
			}
			else{
				Robot.elevator.GroundPosition();
			}
		}
		else if (Robot.oi.getControllerButton(3)){
			if (Robot.oi.getControllerRTrigger()){
				Robot.elevator.ScalePositionMid();
			}
			else {
				Robot.elevator.SwitchPosition();
			}
		}
		else if (Robot.oi.getControllerButton(4)){
			Robot.elevator.ScalePositionHigh();
		}
		Robot.elevator.set(Robot.oi.getControllerRightY());
	}

}
