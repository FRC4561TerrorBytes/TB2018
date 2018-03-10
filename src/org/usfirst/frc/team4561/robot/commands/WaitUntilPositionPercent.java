package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.subsystems.DriveTrainPID;

import edu.wpi.first.wpilibj.command.Command;

public class WaitUntilPositionPercent extends Command {

	double goal;
	double startPos;
	double fullPos;
	Command toRunWhenComplete;
	
	public WaitUntilPositionPercent(double percent, double start, double end, Command toRunWhenComplete){
		goal = percent;
		startPos = start;
		fullPos = end;
		this.toRunWhenComplete = toRunWhenComplete;
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return (Robot.driveTrain.getLeftPos())/(DriveTrainPID.kFeetToTicks)+startPos>=goal*fullPos;
	}
	
	protected void end(){
		
		System.out.println("WaitUntilPositionPercent finished.");
		toRunWhenComplete.start();
	}
}
