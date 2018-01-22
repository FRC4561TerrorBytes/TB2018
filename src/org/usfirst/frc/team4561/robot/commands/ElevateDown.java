package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Make the non-PID climber climb down.
 * @author Ben
 */
public class ElevateDown extends Command {

    public ElevateDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.m_Elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.m_Elevator.climbDown();
    	
    	SmartDashboard.putNumber("Elevator Encoder Position", Robot.m_Elevator.getEncoderPosition());
    	SmartDashboard.putNumber("Elevator Encoder Velocity", Robot.m_Elevator.getEncoderPosition());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_Elevator.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
