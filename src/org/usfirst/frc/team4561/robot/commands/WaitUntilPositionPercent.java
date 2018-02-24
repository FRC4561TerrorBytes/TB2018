package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.subsystems.DriveTrainPID;

import edu.wpi.first.wpilibj.command.Command;

public class WaitUntilPositionPercent extends Command {

	double goal;
	double startPos;
	double fullPos;
	
	public WaitUntilPositionPercent(double percent, double start, double end){
		goal = percent;
		startPos = start;
		fullPos = end;
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return (Robot.driveTrain.getLeftPos()+startPos)/(DriveTrainPID.kFeetToTicks)>=goal*fullPos;
	}
	
	protected void end(){
		System.out.println("It happened");
	}
}
