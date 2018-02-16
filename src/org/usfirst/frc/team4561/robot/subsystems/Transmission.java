package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.RobotMap;
/**
* @author Krishna P
*/

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *This is the Transmission subsystem
 * @author Krishna
 */
public class Transmission extends Subsystem {
	
	
	private DoubleSolenoid doubleSolenoidTransLeft = new DoubleSolenoid(RobotMap.PCM, RobotMap.TRANSMISSION_SOLENOID_PORT, RobotMap.TRANSMISSION_SOLENOID_PORT_TWO);
	private DoubleSolenoid doubleSolenoidTransRight = new DoubleSolenoid(RobotMap.PCM, RobotMap.TRANSMISSION_SOLENOID_TWO_PORT, RobotMap.TRANSMISSION_SOLENOID_TWO_PORT_TWO);
	private Solenoid funSolenoid = new Solenoid(RobotMap.PCM, 7);
	public Transmission() {
	}
	
	public void initDefaultCommand() {}
	
	
	
	public void torqueGear() {
		if (RobotMap.TRANSMISSION_DEBUG) {
  			System.out.println("[Transmission] Switching to high torque gear...");
		}
		
		
		doubleSolenoidTransLeft.set(DoubleSolenoid.Value.kForward);
		doubleSolenoidTransRight.set(DoubleSolenoid.Value.kForward);
		
		
	}
	
	public String getTransMode() {
		String transmission = doubleSolenoidTransLeft.get().toString();
		return transmission;
	}
	
	public void speedGear() {
		if (RobotMap.TRANSMISSION_DEBUG) {
  			System.out.println("[Transmission] Switching to high speed gear...");
		}
  			doubleSolenoidTransLeft.set(DoubleSolenoid.Value.kReverse);
  			doubleSolenoidTransRight.set(DoubleSolenoid.Value.kReverse);
		
	
	}
		
		
		
	
	public void stop() {
		
		if (RobotMap.TRANSMISSION_DEBUG) {
  			System.out.println("[Transmission] Robot stops");
		}
  			doubleSolenoidTransLeft.set(DoubleSolenoid.Value.kOff);
  			doubleSolenoidTransLeft.set(DoubleSolenoid.Value.kOff);
		
	}
	
}
	
	
	



	
	
