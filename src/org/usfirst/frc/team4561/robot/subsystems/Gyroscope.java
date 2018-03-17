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
		if (real) return gyro.getCompassHeading();
		else return 0;
	}
	public double getPressure() {
		if (real) return gyro.getBarometricPressure();
		else return 0;
	}
	public double getAltitude() {
		if (real) return gyro.getAltitude();
		else return 0;
	}
	public double getAccel() {
		if (real) return gyro.getAccelFullScaleRangeG();
		else return 0;
	}
	public double getTemp() {
		if (real) return gyro.getTempC();
		else return 0;
	}
	public double getSpeedX() {
		if (real) return gyro.getVelocityX();
		else return 0;
	}
	public double getSpeedY() {
		if (real) return gyro.getVelocityY();
		else return 0;
	}
	public double getSpeedZ() {
		if (real) return gyro.getVelocityZ();
		else return 0;
	}
	public double getDisplacementX() {
		if (real) return gyro.getDisplacementX();
		else return 0;
	}
	public double getDisplacementY() {
		if (real) return gyro.getDisplacementY();
		else return 0;
	}
	public double getDisplacementZ() {
		if (real) return gyro.getDisplacementZ();
		else return 0;
	}
	public boolean isTipping() { //TODO: needs tuning probably
		return (getPitch() > 30 && getPitch() < 80) || (getRoll() > 25 && getRoll() < 80);
	}
	public boolean isTipped() {
		return (getPitch() >= 80) || (getRoll() >= 80);
	}
	public boolean isMoving() {
		if (real) return gyro.isMoving();
		else return false;
	}
	public boolean isRotating() {
		if (real) return gyro.isRotating();
		else return false;
	}
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
