package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Trajectory;

public class WaitUntilPosition extends Command {

	int goal;
	int startPos;
	public WaitUntilPosition(int pos, int start){
		goal = pos;
		startPos = start;
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return (Robot.driveTrain.getLeftPos()+startPos)>goal;
	}

}
