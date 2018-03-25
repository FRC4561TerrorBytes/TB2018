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
				Robot.elevator.ScalePositionHighFlat();
			}
			else{
				Robot.elevator.ScalePositionHighArmAngled();
			}
		}
		else if (Robot.oi.getControllerButton(3)){
			if (!Robot.oi.getControllerButton(6)){
				Robot.elevator.ScalePositionLowFlat();
			}
			else {
				Robot.elevator.ScalePositionLowArmAngled();
			}
		}
		else if (Robot.oi.getControllerButton(4)){
			if (!Robot.oi.getControllerButton(6)){
				Robot.elevator.ScalePositionMidFlat();
			}
			else {
				Robot.elevator.ScalePositionMidArmAngled();
			}
		}
		if (Robot.oi.getControllerRightY() != 0) {
			double output = Math.copySign(Math.pow(Robot.oi.getControllerRightY(), 8), -Robot.oi.getControllerRightY());
			Robot.elevator.set(-Robot.oi.getControllerRightY());
			Robot.elevator.resetBetter();
		}
		if (Robot.oi.getControllerRightY() == 0 && RobotMap.ELEVATOR_PID) {
			Robot.elevator.setToGoal();
		}
		
		//SmartDashboard.putNumber("Elevator Pos", Robot.elevator.getElevatorPos());
	}

}
