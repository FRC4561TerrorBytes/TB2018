package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 *This is the IntakeIn command
 *@author Karth, Lucas
 */
public class IntakeIn extends Command {
	
	public IntakeIn(){
		requires(Robot.intake);
	}

    protected void execute(){
    	System.out.println(Robot.intake.detectorState());
    	if(Robot.intake.detectorState() == false){
    		stop();
    	}
    	else{
    		Robot.intake.intakeIn();
    	}
    }
    protected boolean isFinished(){
    	return false;
    }
    protected void stop(){
    	Robot.intake.stop();
    }
    protected void interrupted(){
    	stop();
    }
}
