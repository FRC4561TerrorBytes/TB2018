package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *This is the IntakeRelease command
 *@author karth
 */
public class IntakeInJig extends CommandGroup {
	public IntakeInJig(){
	addSequential(new IntakeLeft());
	addSequential(new IntakeRight());
	}
}
