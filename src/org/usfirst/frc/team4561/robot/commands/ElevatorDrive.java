package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

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
			if (!Robot.oi.getControllerButton(6)){
				Robot.elevator.ScalePositionHigh();
			}
			else{
				Robot.elevator.ScalePositionHighArmFlat();
			}
		}
		else if (Robot.oi.getControllerButton(3)){
			if (!Robot.oi.getControllerButton(6)){
				Robot.elevator.ScalePositionLow();
			}
			else {
				Robot.elevator.ScalePositionLowArmFlat();
			}
		}
		else if (Robot.oi.getControllerButton(4)){
			if (!Robot.oi.getControllerButton(6)){
				Robot.elevator.ScalePositionMid();
			}
			else {
				Robot.elevator.ScalePositionMidArmFlat();
			}
		}
		if (Robot.oi.getControllerRightY() != 0) {
			double output = Math.copySign(Math.pow(Robot.oi.getControllerRightY(), 4), -Robot.oi.getControllerRightY());
			if (output > 0 && output < 0.2) {
				output = output;//0.2; //TODO: Tune later
			}
			Robot.elevator.set(-Robot.oi.getControllerRightY());
			Robot.elevator.resetBetter();
		}
		if (Robot.oi.getControllerRightY() == 0 && RobotMap.ELEVATOR_PID) {
			Robot.elevator.setToGoal();
		}
		
		//SmartDashboard.putNumber("Elevator Pos", Robot.elevator.getElevatorPos());
	}

}
