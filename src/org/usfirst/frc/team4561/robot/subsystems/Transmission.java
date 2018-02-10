package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.RobotMap;
/**
* @author Krishna P
*/

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *This is the Transmission subsystem
 * @author Krishna
 */
public class Transmission extends Subsystem {
	
	
	private DoubleSolenoid doubleSolenoidTrans;
	
	public Transmission() {
		//doubleSolenoidTrans = new DoubleSolenoid(RobotMap.PCM, RobotMap.TRANSMISSION_SOLENOID_PORT, RobotMap.TRANSMISSION_SOLENOID_TWO_PORT);
	}
	
	public void initDefaultCommand() {}
	
	
	
	public void torqueGear() {
		if (RobotMap.TRANSMISSION_DEBUG) {
  			System.out.println("[Transmission] Switching to high torque gear...");
		}
		
		
		doubleSolenoidTrans.set(DoubleSolenoid.Value.kForward);
		
		
		
	}
	
	public String getTransMode() {
		String transmission = doubleSolenoidTrans.get().toString();
		return transmission;
	}
	
	public void speedGear() {
		String transmission = doubleSolenoidTrans.get().toString();
		SmartDashboard.putString("SpeedGear", transmission);
		if (RobotMap.TRANSMISSION_DEBUG) {
  			System.out.println("[Transmission] Switching to high speed gear...");
		}
  			doubleSolenoidTrans.set(DoubleSolenoid.Value.kReverse);
  			
		
	
	}
		
		
		
	
	public void stop() {
		String transmission = doubleSolenoidTrans.get().toString();
		SmartDashboard.putString("Stop", transmission);
		if (RobotMap.TRANSMISSION_DEBUG) {
  			System.out.println("[Transmission] Robot stops");
		}
  			doubleSolenoidTrans.set(DoubleSolenoid.Value.kOff);
  			
		
	}
	
}
	
	
	



	
	
