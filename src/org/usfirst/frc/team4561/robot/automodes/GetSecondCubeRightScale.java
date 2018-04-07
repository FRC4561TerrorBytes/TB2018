package org.usfirst.frc.team4561.robot.automodes;

import org.usfirst.frc.team4561.robot.commands.ArmAngle;
import org.usfirst.frc.team4561.robot.commands.ArmIntakePosition;
import org.usfirst.frc.team4561.robot.commands.ArmVertical;
import org.usfirst.frc.team4561.robot.commands.BobClose;
import org.usfirst.frc.team4561.robot.commands.BobOpen;
import org.usfirst.frc.team4561.robot.commands.ElevatorGroundPosition;
import org.usfirst.frc.team4561.robot.commands.ElevatorScalePosition;
import org.usfirst.frc.team4561.robot.commands.IntakeIn;
import org.usfirst.frc.team4561.robot.commands.IntakeRelease;
import org.usfirst.frc.team4561.robot.commands.IntakeStop;
import org.usfirst.frc.team4561.robot.commands.RunTrajectoryOnboard;
import org.usfirst.frc.team4561.robot.commands.TurnMagic;
import org.usfirst.frc.team4561.robot.commands.WaitUntilOnboardTrajectoryFinished;
import org.usfirst.frc.team4561.trajectories.MotionProfileOnboardRunner;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class GetSecondCubeRightScale extends CommandGroup {
	public GetSecondCubeRightScale() {
		addSequential(new ElevatorGroundPosition());
		addSequential(new ArmIntakePosition());
		addSequential(new BobOpen());
		addSequential(new IntakeIn());
		addSequential(new RunTrajectoryOnboard(MotionProfileOnboardRunner.TrajectorySelect.CubeRightScaleRight));
		addSequential(new WaitUntilOnboardTrajectoryFinished());
		addSequential(new BobClose());
		addSequential(new IntakeStop());
		addSequential(new ArmVertical());
		addSequential(new TurnMagic(-180));
		addSequential(new ElevatorScalePosition());
		addSequential(new RunTrajectoryOnboard(MotionProfileOnboardRunner.TrajectorySelect.CubeRightScaleRight));
		addSequential(new WaitUntilOnboardTrajectoryFinished());
		addSequential(new ArmAngle());
		addSequential(new WaitCommand(0.25));
		addSequential(new IntakeRelease());
	}
}
