package org.usfirst.frc.team4561.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RELEASE extends CommandGroup {

    public RELEASE() {
       addParallel(new RightRollerForwards());
       addSequential(new LeftRollerForwards());
    }
}
