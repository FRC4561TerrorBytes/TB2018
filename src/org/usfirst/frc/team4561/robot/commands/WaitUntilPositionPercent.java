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
		startPos = start*DriveTrainPID.kFeetToTicks;
		fullPos = end*DriveTrainPID.kFeetToTicks;
		this.toRunWhenComplete = toRunWhenComplete;
		System.out.println("Starting at " + startPos + ", going until " + fullPos*percent + " out of " + fullPos);
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return ((Robot.driveTrain.getLeftPos())+startPos) > (goal*fullPos);
	}
	protected void execute() {
		if ((Robot.driveTrain.getLeftPos()+startPos) > (goal*fullPos)) {
			System.out.println("It is greater than the set point, should be finishing now");
		}
	}
	protected void end(){
		
		System.out.println("WaitUntilPositionPercent finished at " + Robot.driveTrain.getLeftPos() + "/" + fullPos*goal + " out of " + fullPos);
		
		toRunWhenComplete.start();
	}
}
