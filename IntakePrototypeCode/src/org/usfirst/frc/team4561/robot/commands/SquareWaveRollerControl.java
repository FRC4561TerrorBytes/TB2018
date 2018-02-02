package org.usfirst.frc.team4561.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class SquareWaveRollerControl extends CommandGroup {

    public SquareWaveRollerControl() {
    	addSequential(new LeftRollerBackwards());
    	addSequential(new RightRollerBackwards());
    }
}
