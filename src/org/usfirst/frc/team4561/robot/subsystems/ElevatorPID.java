package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;



import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorPID extends Subsystem {
	
	private ControlMode follower = com.ctre.phoenix.motorcontrol.ControlMode.Follower;

	private WPI_TalonSRX motorOne = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_1_PORT);
	private WPI_TalonSRX motorTwo = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_2_PORT);
	private WPI_TalonSRX motorThree = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_3_PORT);
	private WPI_TalonSRX motorFour = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_4_PORT);
	
	public ElevatorPID() {
		motorOne.set(RobotMap.ELEVATOR_MOTOR_1_PORT);
		motorTwo.set(follower, RobotMap.ELEVATOR_MOTOR_1_PORT);
		motorThree.set(follower, RobotMap.ELEVATOR_MOTOR_1_PORT);
		motorFour.set(follower, RobotMap.ELEVATOR_MOTOR_1_PORT);
		
		motorOne.set(ControlMode.Position , 0);
		motorOne.config_kF(0, 0, 0);
		motorOne.config_kP(0, 0, 0);
		motorOne.config_kI(0, 0, 0);
		motorOne.config_kP(0, 0, 0);
	}
	
	//Elevator position with Arm touching ground
	public void GroundPosition() {
		motorOne.set(ControlMode.Position, 0);
	}
	
	//Elevator position with Arm at switch height
	public void SwitchPosition() {
		motorOne.set(ControlMode.Position, 0.25);
	}
	
	//Elevator position with Arm at scale height
	public void ScalePosition() {
		motorOne.set(ControlMode.Position, 1);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
