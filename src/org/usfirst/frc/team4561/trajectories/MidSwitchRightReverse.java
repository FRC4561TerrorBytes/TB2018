package org.usfirst.frc.team4561.trajectories;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class MidSwitchRightReverse extends Path {
public MidSwitchRightReverse() {
    	
    	// Create waypoints (knots of the Hermite spline). Units are in feet.
    	// First point is the starting position, last point is the end.
    	// Angles are in radians, positive Y is to the left, positive X is forward
    	points = new Waypoint[] {
    			new Waypoint(5, 13.23, 0),
    			new Waypoint(7, 10, Pathfinder.d2r(-60)),
    			new Waypoint(11.67, 8.04, 0)
    	};

    	reverse = true;
    	generateTrajectoriesAndArrays();
    	
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
