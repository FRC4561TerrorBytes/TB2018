package org.usfirst.frc.team4561.robot.commands;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;

/**
* @author Krishna P
*/

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *This is the SpeedGear command
 *@author krishna
 */

public class SpeedGear extends Command {
	
	
	public SpeedGear() {
		requires(Robot.transmission);
	}

	
protected void initialize()  {
	if (RobotMap.TRANSMISSION_DEBUG)  {
		System.out.println("[C:SpeedGear] Initializing...");
	}
}



	
protected void execute() {
	Robot.transmission.speedGear();
	
	if (RobotMap.TRANSMISSION_DEBUG)  {
		System.out.println("[C:SpeedGear] Robot executes...");
	}
	
}




protected void end() {
	if (RobotMap.TRANSMISSION_DEBUG)
		System.out.println("C:SpeedGear]  Command finished.");
}

	protected void interrupted(){
		end();
	}

	protected boolean isFinished() {
		return true;
	}

}
