package org.usfirst.frc.team4561.trajectories;
import org.usfirst.frc.team4561.robot.RobotMap;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Trajectory from left scale position to pivot position.
 * @author Max
 *
 */
public class LeftScaleTurnAroundS3 extends Path {
	
	// ALL units are in feet. Commands convert them to TalonSRX ticks later.
	
    public LeftScaleTurnAroundS3() {
    	
    	// Create waypoints (knots of the Hermite spline). Units are in feet.
    	// First point is the starting position, last point is the end.
    	// Angles are in radians, positive Y is to the left, positive X is forward
    	points = new Waypoint[] {
    			new Waypoint(24.88, 20.15, 0),
    			    	new Waypoint(21, 24, Pathfinder.d2r(270))
    			};
    	
    	// Create the trajectory for the center of the robot
    	trajectory = Pathfinder.generate(points, config);
    	
    	// Change that trajectory into two separate trajectories: one for the left side and one for the right
    	modifier = new TankModifier(trajectory).modify(RobotMap.WHEELBASE_WIDTH);
    	left = modifier.getLeftTrajectory();
    	right = modifier.getRightTrajectory();
    	
    	/* To print out points along trajectory...
    	 
	    	for (int i = 0; i < left.length(); i++) {
	    		Trajectory.Segment seg = trajectory.get(i);
	    
	    		System.out.printf("%f,%f,%f,%f,%f,%f,%f,%f\n", 
	        		seg.dt, seg.x, seg.y, seg.position, seg.velocity, 
	            	seg.acceleration, seg.jerk, seg.heading);
			}
	
    	 */
    }
}
