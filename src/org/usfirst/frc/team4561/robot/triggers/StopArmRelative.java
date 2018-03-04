package org.usfirst.frc.team4561.robot.triggers;

import org.usfirst.frc.team4561.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

public class StopArmRelative extends Trigger {

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return Robot.oi.getControllerLeftY()==0;
	}

}
