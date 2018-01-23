package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IntakeLeft extends Command {

    public IntakeLeft() {
    	requires(Robot.Intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(0.125);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.Intake.leftIntake();
    	SmartDashboard.putNumber("IntakeLeft Encoder Position", Robot.Intake.getIntakeLeftPosition());
    	SmartDashboard.putNumber("IntakeLeft Encoder Velocity", Robot.Intake.getIntakeRightPosition());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.Intake.leftIntakeStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
