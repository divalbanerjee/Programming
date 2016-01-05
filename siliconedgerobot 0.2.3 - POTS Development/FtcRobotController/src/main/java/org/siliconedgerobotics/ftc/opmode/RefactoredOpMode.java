package org.siliconedgerobotics.ftc.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by gaow on 1/4/2016.
 */

// don't like the name? you can always refactor it later
public class RefactoredOpMode extends OpMode
{
    // declare motor speed constants
    static final double DefaultSpeed = 0.5; // generic motor speed (50%)
    static final double DriveSpeed = 0.5; // drive motors and treads
    static final double SlideSpeed = 0.3; // linear slide
    static final double TapeSpeed = 0.85; // tape winch

    // declare servo position resolution
    static final double DefaultServoResolution = 0.01; // generic servo resolution
    static final double TapeResolution = 0.01; // tape arm servo
    static final double BucketResolution = 0.01; // bucket tilt servo

    // declare component names in hardware map
    static final String LEFT_MOTOR_NAME = "lmotor";
    static final String RIGHT_MOTOR_NAME = "rmotor";
    static final String TAPE_MOTOR_NAME = "tapemotor";
    static final String SLIDE_MOTOR_NAME = "slidemotor";
    static final String BRUSH_MOTOR_NAME = "brushmotor";
    static final String CONVEYOR_MOTOR_NAME = "conveyormotor";

    // declare servo names in hardware map
    static final String TAPE_ARM_SERVO_NAME = "tapeservo";
    static final String BUCKET_SERVO_NAME = "bucketservo";

    public void init()
    {

    }

    public void loop()
    {

    }

    @Override
    public void stop()
    {
        // TODO: retract tape winch and linear slides
    }

    public void getHardware()
    {

    }
}
