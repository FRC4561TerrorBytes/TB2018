package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.commands.ArmIntakePosition;
import org.usfirst.frc.team4561.robot.commands.ArmVertical;
import org.usfirst.frc.team4561.robot.commands.BobClose;
import org.usfirst.frc.team4561.robot.commands.BobOpen;
import org.usfirst.frc.team4561.robot.commands.IntakeIn;
import org.usfirst.frc.team4561.robot.commands.IntakeStop;
import org.usfirst.frc.team4561.robot.commands.RunTrajectoryOnboard;
import org.usfirst.frc.team4561.robot.commands.WaitUntilOnboardTrajectoryFinished;
import org.usfirst.frc.team4561.trajectories.MotionProfileOnboardRunner;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class IntakeCube extends CommandGroup {
	public IntakeCube() {
		addSequential(new ArmIntakePosition());
		addSequential(new BobOpen());
		addSequential(new IntakeIn());
		addSequential(new RunTrajectoryOnboard(MotionProfileOnboardRunner.TrajectorySelect.CubeLeftScaleLeft));
		addSequential(new WaitUntilOnboardTrajectoryFinished());
		addSequential(new BobClose());
		addSequential(new WaitCommand(0.25));
		addSequential(new IntakeStop());
		addSequential(new WaitCommand(0.25));
		addSequential(new ArmVertical());
	}
}
