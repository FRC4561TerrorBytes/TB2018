package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.Robot;
import org.usfirst.frc.team4561.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
		
		intakeLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		intakeRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
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

	public double getIntakeLeftPosition() {
    	return intakeLeftMotor.getSelectedSensorPosition(0);
    }
	
	public double getIntakeRightPosition() {
    	return intakeRightMotor.getSelectedSensorPosition(0);
    }
	
	

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
