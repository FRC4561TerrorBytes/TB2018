package org.usfirst.frc.team4561.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4561.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ArmPID extends Subsystem {
	private ControlMode follower = com.ctre.phoenix.motorcontrol.ControlMode.Follower;
	private WPI_TalonSRX motorOne;
	private WPI_TalonSRX motorTwo;
	public ArmPID() {
		motorOne = new WPI_TalonSRX(RobotMap.ARM_MOTOR_1_PORT);
		motorTwo.set(follower, RobotMap.ARM_MOTOR_1_PORT);
	}
	// Arm on ground to intake block
	public void IntakePosition() {
	motorOne.set(ControlMode.Position, 0);
	}
	//Arm in straight release position
	public void ReleasePosition() {
		motorOne.set(ControlMode.Position, 0);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
