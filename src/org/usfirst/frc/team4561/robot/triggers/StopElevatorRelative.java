package org.usfirst.frc.team4561.robot.triggers;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

public class StopElevatorRelative extends Trigger {

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return (Robot.elevator.getThrottle()==0 && Robot.oi.getControllerRightY() == 0);
	}

}
