package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveMagic extends Command {

	int left;
	int right;
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public DriveMagic(int Left, int Right){
		left = Left;
		right = Right;
		requires(Robot.driveTrain);
	}
	protected void execute(){
		
			double tLeft = -3600*left;
			double tRight = 1800*right;
			Robot.driveTrain.magicDrive(tLeft, tRight);
			System.out.print(tLeft);
			System.out.print(" ");
			System.out.println(tRight);
			
		SmartDashboard.putNumber("Left Speed", Robot.driveTrain.getLeftSpeed());
		SmartDashboard.putNumber("Right Speed", Robot.driveTrain.getRightSpeed());
		SmartDashboard.putNumber("Left Pos", Robot.driveTrain.getLeftPos());
		SmartDashboard.putNumber("Right Pos", Robot.driveTrain.getRightPos());
		SmartDashboard.putNumber("Left Error", Robot.driveTrain.getLeftError());
		SmartDashboard.putNumber("Right Error", Robot.driveTrain.getRightError());
		SmartDashboard.putNumber("Gyro Angle", Robot.driveTrain.getGyroAngle());
		SmartDashboard.putNumber("Gyro Rate", Robot.driveTrain.getGyroRate());
		SmartDashboard.putNumber("Correction Value", 0);
		SmartDashboard.putNumber("Avg Speed", Robot.driveTrain.avgSpeed());


	}
}
