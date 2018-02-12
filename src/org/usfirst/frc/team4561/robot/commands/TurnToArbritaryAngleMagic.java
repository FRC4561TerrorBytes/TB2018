package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 *This is the Motion Magic angle command
 *@author Lucas
 */
public class TurnToArbritaryAngleMagic extends Command {

	double angleA;
	
    public TurnToArbritaryAngleMagic(double angle) {
        requires(Robot.driveTrain);
        angle = angleA;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//setTimeout(0.125);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.resetGyro();
		Robot.driveTrain.goToAngle(angleA);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; //isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
