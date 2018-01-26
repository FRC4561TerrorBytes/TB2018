package org.usfirst.frc.team4561.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4561.robot.RobotMap;

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
	private WPI_TalonSRX motorTwo;
	public ArmPID() {
		motorOne = new WPI_TalonSRX(RobotMap.ARM_MOTOR_1_PORT);
		motorOne.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		motorTwo = new WPI_TalonSRX(RobotMap.ARM_MOTOR_2_PORT);
		motorTwo.set(follower, RobotMap.ARM_MOTOR_1_PORT);
	}
	// Arm on ground to intake block
	public void IntakePosition() {
	motorOne.set(ControlMode.Position, 0);
	if (RobotMap.ARM_DEBUG) {
		System.out.println("[Subsystem] ArmPID: Down to Intake Position");
	}
		}
	//Arm in straight release position
	public void ReleasePosition() {
		motorOne.set(ControlMode.Position, 0);
		if (RobotMap.ARM_DEBUG) {
			System.out.println("[Subsystem] ArmPID: Up to Release Position");
		}
	}
	/**
     * Gets the encoder position of the arm.
     */
    
    public double getEncoderPosition() {
    	return motorOne.getSelectedSensorPosition(0);
    }
    
    /**
     * Gets the encoder velocity of the arm.
     */
    
    public double getEncoderVelocity() {
    	return motorOne.getSelectedSensorVelocity(0);
    }
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
