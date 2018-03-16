package org.usfirst.frc.team4561.robot.subsystems;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gyroscope extends Subsystem {
	
	private AHRS gyro;
	private boolean real = true;
	
	public Gyroscope() {
		try {
			gyro = new AHRS(SPI.Port.kMXP);
		}
		catch (RuntimeException ex) {
			DriverStation.reportError("Failed to create Gyro - is it plugged in?", false);
			real = false;
		}
	}
	public double getPitch() {
		if (real) return gyro.getPitch();
		else return 0;
	}
	public double getRoll() {
		if (real) return gyro.getRoll();
		else return 0;
	}
	public double getYaw() {
		if (real) return gyro.getYaw();
		else return 0;
	}
	public double getAngle() {
		if (real) return gyro.getAngle();
		else return 0;
	}
	public double getRate() {
		if (real) return gyro.getRate();
		else return 0;
	}
	public boolean exists() {
		return real;
	}
	public double getHead() {
		return gyro.getCompassHeading();
	}
	public double getPressure() {
		return gyro.getBarometricPressure();
	}
	public double getAltitude() {
		return gyro.getAltitude();
	}
	public double getAccel() {
		return gyro.getAccelFullScaleRangeG();
	}
	public double getTemp() {
		return gyro.getTempC();
	}
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
