package org.usfirst.frc.team4561.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *This is the IntakeIn command
 *@author Karth, Lucas
 */
public class IntakeIn extends CommandGroup {

    public IntakeIn() {
        addParallel(new IntakeLeft());
        addSequential(new IntakeRight());        
    }
}
