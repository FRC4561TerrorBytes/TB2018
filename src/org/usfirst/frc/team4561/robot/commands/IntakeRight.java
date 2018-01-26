package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 *
 */
public class IntakeRight extends Command {

    public IntakeRight() {
        requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(0.125);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.rightIntake();
    	SmartDashboard.putNumber("IntakeRight Encoder Position", Robot.intake.getIntakeLeftPosition());
    	SmartDashboard.putNumber("IntakeRight Encoder Velocity", Robot.intake.getIntakeRightPosition());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.rightIntakeStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
