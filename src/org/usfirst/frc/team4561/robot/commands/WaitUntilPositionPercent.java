package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.subsystems.DriveTrainPID;

import edu.wpi.first.wpilibj.command.Command;

public class WaitUntilPositionPercent extends Command {

	double goal;
	double startPos;
	double fullPos;
	Command toRunWhenComplete;
	
	public WaitUntilPositionPercent(double percent, Command toRunWhenComplete) {
		goal = percent;
	}
	
	@Deprecated
	public WaitUntilPositionPercent(double percent, double start, double end, Command toRunWhenComplete){
		goal = percent;
		startPos = start*DriveTrainPID.kFeetToTicks;
		fullPos = end*DriveTrainPID.kFeetToTicks;
		this.toRunWhenComplete = toRunWhenComplete;
		System.out.println("Starting at " + startPos + ", going until " + fullPos*percent + " out of " + fullPos);
		Robot.driveTrain.resetEncoders();
	}
	
	@Override
	protected void initialize() {
		startPos = Robot.motionProfileRunner.getCurrentTrajectory().getLeftArrayFirstPosition() * DriveTrainPID.kFeetToTicks;
		fullPos = Robot.motionProfileRunner.getCurrentTrajectory().getLeftArrayLastPosition() * DriveTrainPID.kFeetToTicks;
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Math.abs((Robot.driveTrain.getLeftPos())+startPos) > Math.abs(goal*fullPos);
	}
	protected void execute() {
		if (Math.abs(Robot.driveTrain.getLeftPos()+startPos) > Math.abs(goal*fullPos)) {
			System.out.println("It is greater than the set point, should be finishing now");
		}
		else {
			//System.out.println(Math.abs(Robot.driveTrain.getLeftPos()+startPos) + " < " + Math.abs(goal*fullPos));
		}
	}
	protected void end(){
		
		System.out.println("WaitUntilPositionPercent finished at " + Math.abs(Robot.driveTrain.getLeftPos()) + "/" + Math.abs(fullPos*goal) + " out of " + Math.abs(fullPos));
		
		toRunWhenComplete.start();
	}
}