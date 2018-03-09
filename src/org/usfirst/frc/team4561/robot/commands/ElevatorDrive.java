package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorDrive extends Command {

	public ElevatorDrive(){
		requires(Robot.elevator);
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void execute(){
		if (Robot.oi.getControllerButton(1)){
			if (!Robot.oi.getControllerButton(6)){
				Robot.elevator.GroundPosition();
			}
			else{
				Robot.elevator.GroundPosition();
			}
		}
		else if (Robot.oi.getControllerButton(2)){
			Robot.elevator.ScalePositionHigh();
		}
		else if (Robot.oi.getControllerButton(3)){
			if (!Robot.oi.getControllerButton(6)){
				Robot.elevator.ScalePositionLow();
			}
			else {
				Robot.elevator.ScalePositionLow();
			}
		}
		else if (Robot.oi.getControllerButton(4)){
			Robot.elevator.ScalePositionMid();
		}
		else if (Robot.oi.getControllerButton(6)){
			Robot.elevator.SwitchPosition();
		}
		Robot.elevator.set(-Robot.oi.getControllerRightY());
		
		//SmartDashboard.putNumber("Elevator Pos", Robot.elevator.getElevatorPos());
	}

}
