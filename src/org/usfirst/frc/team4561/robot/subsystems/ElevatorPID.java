package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.RobotMap;
import org.usfirst.frc.team4561.robot.commands.ElevatorDrive;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;



import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This is the Elavator PID subsystem
 * @author Snehil
 */

//TODO: Remove placeholder values
public class ElevatorPID extends Subsystem {
	
	private ControlMode follower = com.ctre.phoenix.motorcontrol.ControlMode.Follower;

	private WPI_TalonSRX motorOne = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_1_PORT);
	private WPI_TalonSRX motorTwo = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_2_PORT);
	
	private int goal = 0;
	
	public ElevatorPID() {
		//motorOne.set(RobotMap.ELEVATOR_MOTOR_1_PORT);
		motorOne.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
		motorTwo.set(follower, RobotMap.ELEVATOR_MOTOR_1_PORT);
		motorTwo.setInverted(false);
		motorOne.setInverted(true);
		motorOne.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		motorOne.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		motorOne.configSetParameter(ParamEnum.eClearPositionOnLimitR, 1, 0, 0, 0);
		motorOne.configReverseSoftLimitEnable(false, 0);
		motorOne.configForwardSoftLimitEnable(false, 0);
		motorOne.overrideSoftLimitsEnable(true);
		motorOne.overrideLimitSwitchesEnable(true);
		motorOne.configPeakCurrentLimit(20, 0);
		motorOne.enableCurrentLimit(false);
		motorOne.config_kP(0, 1, 0);
		motorOne.config_kI(0, 0, 0);
		motorOne.config_kD(0, 0, 0);
	}
	
	
	public void set(double speed){
		goal = goal + (int) (speed*20);
		if (RobotMap.ELEVATOR_PID) setToGoal();
		else motorOne.set(ControlMode.PercentOutput, speed);
	}
	public void setToGoal(){
		motorOne.set(ControlMode.Position, goal);
	}
	public void stop(){
		motorOne.set(ControlMode.PercentOutput, 0);
	}
	//Elevator position with Arm touching ground
	public void GroundPosition() {
		goal = 15;
		if (RobotMap.ELEVATOR_PID) setToGoal();
	}
	
	//Elevator position with Arm at switch height
	public void SwitchPosition() {
		goal = 85;
		if (RobotMap.ELEVATOR_PID) setToGoal();
	}
	
	//Elevator position with Arm at scale height
	public void ScalePositionMid() {
		goal = 0;
		if (RobotMap.ELEVATOR_PID) setToGoal();
	}
	public void ScalePositionLow(){
		goal = 0;
		if (RobotMap.ELEVATOR_PID) setToGoal();
	}
	public void ScalePositionHigh(){
		goal = 350;
		if (RobotMap.ELEVATOR_PID) setToGoal();
	}
	
    public void setPowerOne(double power) {
           motorOne.set(ControlMode.PercentOutput, power);
           
       }
       
    public void setPowerTwo(double power) {
           motorTwo.set(ControlMode.PercentOutput, power);
           
       }

	public double getElevatorPos(){
		return motorOne.getSelectedSensorPosition(0);
	}
	
	public double getElevatorSpeed(){
		return motorOne.getSelectedSensorVelocity(0);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new ElevatorDrive());

	}

	public double motorOneVoltage(){
		return motorOne.getMotorOutputVoltage();
	}
	public double motorTwoVoltage(){
		return motorTwo.getMotorOutputVoltage();
	}
	public boolean limitSwitch(){
		return motorOne.getSensorCollection().isRevLimitSwitchClosed();
	}
	public double getGoal(){
		return goal;
	}
}
