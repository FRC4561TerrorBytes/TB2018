package org.usfirst.frc.team4561.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import org.usfirst.frc.team4561.robot.RobotMap;
import org.usfirst.frc.team4561.robot.commands.ArmDrive;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/** 
 * This is the Arm PID subsystem
 * @author Max
 *
 */
//TODO: Remove placeholder values
public class ArmPID extends Subsystem {
	private ControlMode follower = com.ctre.phoenix.motorcontrol.ControlMode.Follower;
	private WPI_TalonSRX motorOne;
	private int goal = 0;
	//private WPI_TalonSRX motorTwo;
	public ArmPID() {
		motorOne = new WPI_TalonSRX(RobotMap.ARM_MOTOR_1_PORT);
		motorOne.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		motorOne.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		motorOne.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		motorOne.configSetParameter(ParamEnum.eClearPositionOnLimitF, 1, 0, 0, 0);
		//motorTwo = new WPI_TalonSRX(RobotMap.ARM_MOTOR_2_PORT);
		//motorTwo.set(followerRobotMap.ARM_MOTOR_1_PORT);
		motorOne.config_kP(0, 90, 0);
		motorOne.config_kI(0, 0.005, 0);
		motorOne.config_kD(0, 0, 0);
		motorOne.configPeakOutputForward(0.5, 0);
		motorOne.configPeakOutputReverse(-0.5, 0);
		motorOne.configNominalOutputForward(0, 0);
		motorOne.configNominalOutputReverse(0, 0);
		motorOne.configAllowableClosedloopError(0, 5, 0);
	}
	// Arm on ground to intake block
	public void IntakePosition() {
		goal = 0;
		setToGoal();
		if (RobotMap.ARM_DEBUG) {
			System.out.println("[Subsystem] ArmPID: Down to Intake Position");
			}
		}
	//Arm in straight release position
	public void ReleasePosition() {
		goal = -1000;
		setToGoal();
		if (RobotMap.ARM_DEBUG) {
			System.out.println("[Subsystem] ArmPID: Up to Release Position");
		}
	}
	/**
     * Gets the encoder position of the arm.
     */
    public void setToGoal(){
    	motorOne.set(ControlMode.Position, goal);
    }
    public double getEncoderPosition() {
    	return motorOne.getSelectedSensorPosition(0);
    }
    public void increaseGoal(){
    		goal = goal + 20;
    }
    public void decreaseGoal(){
    		goal = goal - 20;
    }
    /**
     * Gets the encoder velocity of the arm.
     */
    
    public double getEncoderVelocity() {
    	return motorOne.getSelectedSensorVelocity(0);
    }
    public void stop(){
    	motorOne.set(ControlMode.PercentOutput, 0);
    }
	@Override
	protected void initDefaultCommand() {
		//setDefaultCommand(new ArmDrive());

	}
	

}
