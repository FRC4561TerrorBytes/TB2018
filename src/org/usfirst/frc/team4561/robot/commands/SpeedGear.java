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
	private WPI_TalonSRX motorOne = new WPI_TalonSRX(RobotMap.TRANSMISSION_SPEED_BUTTON);
	
	
	public SpeedGear() {
		requires(Robot.transmission);
	}

	
protected void initialize()  {
	setTimeout(1);
	if (RobotMap.TRANSMISSION_DEBUG)  {
		System.out.println("[C:SpeedGear] Initializing...");
	}
}



	
protected void execute() {
	Robot.transmission.speedGear();
	String transmission = Robot.transmission.getTransMode();
	SmartDashboard.putString("Transmission Mode", transmission);
	if (RobotMap.TRANSMISSION_DEBUG)  {
		System.out.println("[C:SpeedGear] Robot executes...");
	}
	
}




protected void end() {
	if (RobotMap.TRANSMISSION_DEBUG)
		System.out.println("C:SpeedGear]  Command finished.");
}


	protected boolean isFinished() {
		return isTimedOut();
	}

}
