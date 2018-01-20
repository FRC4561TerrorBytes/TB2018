package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * This is the Intake
 * @author karth
 *
 */

public class Intake extends Subsystem {
//Two wheels: one turns left other turns right 	
	
	//Motors
	private WPI_TalonSRX intakeLeftMotor;
	private WPI_TalonSRX intakeRightMotor; 
	
	public Intake () {
		intakeLeftMotor = new WPI_TalonSRX (RobotMap.ARM_LEFT_MOTOR_PORT);
		intakeRightMotor = new WPI_TalonSRX (RobotMap.ARM_RIGHT_MOTOR_PORT);
	}
	
	//Intake speed
	public void leftIntake () {
		intakeLeftMotor.set(-1);
	}
	public void rightIntake () {
		intakeRightMotor.set(1);
	}
	
	public void leftIntakeStop () {
		intakeLeftMotor.set(0);
	}
	public void rightIntakeStop () {
		intakeRightMotor.set(0);
	}
	
	public void release () {
		intakeLeftMotor.set(0.5);
		intakeRightMotor.set(-0.5);
	}

	

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
