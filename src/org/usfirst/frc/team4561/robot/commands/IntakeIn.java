package org.usfirst.frc.team4561.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeIn extends CommandGroup {

    public IntakeIn() {
        addSequential(new IntakeLeft());
        addSequential(new IntakeRight());        
    }
}
