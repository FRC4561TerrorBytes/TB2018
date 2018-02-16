package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class ArmDrive extends Command {

	public ArmDrive(){
		requires(Robot.arm);
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void execute(){
		int pov = Robot.oi.getControllerPOV();
		if (pov == RobotMap.ARM_DOWN_POV || Robot.oi.getRightButton(8)){
			Robot.arm.IntakePosition();
		}
		else if (pov == RobotMap.ARM_MIDDLE_POV1 || pov == RobotMap.ARM_MIDDLE_POV2 || Robot.oi.getRightButton(10)){
			Robot.arm.ReleasePosition();
		}
		else if (pov == RobotMap.ARM_UP_POV || Robot.oi.getRightButton(12)){
			Robot.arm.UpPostition();
		}
		Robot.arm.set(-Robot.oi.getControllerLeftY());
		if (RobotMap.ARM_PID) Robot.arm.setToGoal();
		
	}
	
	protected void stop(){
		Robot.arm.stop();
	}
}
