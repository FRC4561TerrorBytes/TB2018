package org.usfirst.frc.team4561.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4561.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * This is the non-PID elevator subsystem file.
 * @author Ben
 */
public class Elevator extends Subsystem {

    private WPI_TalonSRX motorOne;
    private WPI_TalonSRX motorTwo;
    private WPI_TalonSRX motorThree;
    private WPI_TalonSRX motorFour;
    public Elevator() {
    	motorOne = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_1_PORT);
    	motorTwo = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_2_PORT);
    	motorThree = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_3_PORT);
    	motorFour = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_4_PORT);
    }
    
    public void climbUp() {
    	// full forward
    	motorOne.set(1);
    	motorTwo.set(1);
    	motorThree.set(1);
    	motorFour.set(1);
    }
    
    public void stop() {
    	// stop
    	motorOne.set(0);
    	motorTwo.set(0);
    	motorThree.set(0);
    	motorFour.set(0);
    }
    
    public void climbDown() {
    	// full reverse
    	motorOne.set(-1);
    	motorTwo.set(-1);
    	motorThree.set(-1);
    	motorFour.set(-1);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

