package org.usfirst.frc.team4561.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Intake extends CommandGroup {

    public Intake() {
        addSequential(new IntakeLeft());
        addSequential(new IntakeRight());        
    }
}
