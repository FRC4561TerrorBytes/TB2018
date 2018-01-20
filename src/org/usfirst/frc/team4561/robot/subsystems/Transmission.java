package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Transmission extends Subsystem {
	
	private DoubleSolenoid doubleSolenoidTrans;
	
	public Transmission() {
		doubleSolenoidTrans = new DoubleSolenoid(RobotMap.PCM, RobotMap.TRANSMISSION_SOLENOID_PORT, RobotMap.TRANSMISSION_SOLENOID_TWO_PORT);
	}
	
	public void initDefaultCommand() {}
	
	
	
	public void torqueGear() {
		doubleSolenoidTrans.set(DoubleSolenoid.Value.kForward);
		String currentState;
		String lastChange = currentState = "Torque";
	}
	
	public void speedGear() {
		doubleSolenoidTrans.set(DoubleSolenoid.Value.kReverse);
		String currentState;
		String lastChange = currentState = "Speed";
	}
	
	public void stop() {
		doubleSolenoidTrans.set(DoubleSolenoid.Value.kReverse);
		String currentState;
		String lastChange = currentState = "Off";
	}
	
	
	

    }

	
	
