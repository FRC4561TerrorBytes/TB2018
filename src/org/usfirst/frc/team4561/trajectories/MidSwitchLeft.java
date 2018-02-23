package org.usfirst.frc.team4561.trajectories;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Trajectory from middle starting position to the left side of the switch.
 * @author kaiza
 *
 */
public class MidSwitchLeft extends Path {
	// ALL units are in feet. Commands convert them to TalonSRX ticks later.
	
	Trajectory.Config config;
	Waypoint[] points;
    Trajectory trajectory;
    TankModifier modifier;
    Trajectory left;
    Trajectory right;
	
    public MidSwitchLeft() {
    	
    	// Create the Trajectory Configuration
    	//
    	// Arguments:
    	// Fit Method:          HERMITE_CUBIC or HERMITE_QUINTIC
    	// Sample Count:        SAMPLES_HIGH (100 000)
    	//	                      SAMPLES_LOW  (10 000)
    	//	                      SAMPLES_FAST (1 000)
    	// Time Step:           0.05 Seconds
    	// Max Velocity:        1.7 ft/s
    	// Max Acceleration:    2.0 ft/s/s
    	// Max Jerk:            60.0 ft/s/s/s
    	config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
    			Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
    	
    	// Create waypoints (knots of the Hermite spline).
    	// First point is the starting position, last point is the end.
    	// Angles are in radians
    	// Positive Y is to the left, positive X is forward
    	// TODO: Not actually real points for MidSwitchLeft right now
    	points = new Waypoint[] {
    			new Waypoint(3.22, 13.23, 0),
    			new Waypoint(6, 15, Pathfinder.d2r(45)),
    			new Waypoint(11.67, 18.01, 0)
    	};
    	
    	trajectory = Pathfinder.generate(points, config);
    	// Wheelbase Width
    	modifier = new TankModifier(trajectory).modify(1.865); // According to CAD
    	// Do something with the new Trajectories...
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

	@Override
	public Trajectory getLeftTrajectory() {
		// TODO Auto-generated method stub
		return left;
	}

	@Override
	public Trajectory getRightTrajectory() {
		// TODO Auto-generated method stub
		return right;
	}
}
