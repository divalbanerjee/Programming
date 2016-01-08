package org.siliconedgerobotics.ftc.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Wei on 1/4/2016.
 * @author radioactivated (Wei)
 */

// don't like the name? you can always refactor it later
public class TeleOpMode extends CommonOpMode
{
    // declare motor speed ceiling
    static final double DefaultSpeed = 0.5; // generic motor speed (50%)
    static final double DriveSpeed = 1; // drive motors and treads
    static final double SlideSpeed = 0.3; // linear slide
    static final double TapeSpeed = 0.85; // tape winch
    static final double SweeperSpeed = 0.5;
    static final double ConveyorSpeed = 0.5;

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

    /*
        Declare deadzones on controller float values for better idling

        Did you know? Controllers stored improperly can have joysticks drift due to constant pressure.
        This means that a slight value will register on the joystick even while idling,
        leading to the robot creeping forward slowly. Deadzones give a margin that is ignored, preventing
        this issue.
     */
    static final double CONTROLLER_DEFAULT_DEADZONE = 0.075; //subject to change since this 0 to 1 scale is weird

    // declare mininum threshold for triggers to activate
    static final double TRIGGER_THRESHOLD = 0.25;

    // declare motor and servo variables
    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorSlide;
    DcMotor motorTape;
    DcMotor motorSweep;
    DcMotor motorConveyor;
    Servo servoTape;
    Servo servoBucket;

    public TeleOpMode()
    {
        super();
    }

    public void init()
    {
        // Register motors from hardware map
        motorLeft = hardwareMap.dcMotor.get(LEFT_MOTOR_NAME);
        motorRight = hardwareMap.dcMotor.get(RIGHT_MOTOR_NAME);
        motorTape = hardwareMap.dcMotor.get(TAPE_MOTOR_NAME);
        motorSlide = hardwareMap.dcMotor.get(SLIDE_MOTOR_NAME);
        motorSweep = hardwareMap.dcMotor.get(BRUSH_MOTOR_NAME);
        motorConveyor = hardwareMap.dcMotor.get(CONVEYOR_MOTOR_NAME);

        // Register servos from hardware map
        servoTape = hardwareMap.servo.get(TAPE_ARM_SERVO_NAME);
        servoBucket = hardwareMap.servo.get(BUCKET_SERVO_NAME);

        // set correct direction of motors
        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorRight.setDirection(DcMotor.Direction.FORWARD);
        motorTape.setDirection(DcMotor.Direction.FORWARD);
        motorSlide.setDirection(DcMotor.Direction.FORWARD);
        motorSweep.setDirection(DcMotor.Direction.FORWARD);
        motorConveyor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void loop()
    {
        // dyanamically declare variables for controls
        // gamepad 1 : driving
        float throttle = gamepad1.left_stick_y;
        float steering = gamepad1.left_stick_x; // it's a vector value
        boolean tapeExtend = gamepad1.right_trigger > TRIGGER_THRESHOLD;
        boolean tapeRetract = gamepad1.left_trigger > TRIGGER_THRESHOLD;
        boolean tapeUp = gamepad1.right_bumper;
        boolean tapeDown = gamepad1.left_bumper;
        // gamepad 2 : appendage control
        boolean sweeperIn = gamepad2.a;
        boolean sweeperOut = gamepad2.b;
        boolean slideUp = gamepad2.dpad_up;
        boolean slideDown = gamepad2.dpad_down;
        boolean basketLeft = gamepad2.dpad_left;
        boolean basketRight = gamepad2.dpad_right;
        boolean conveyorUp = gamepad2.left_bumper;
        //boolean conveyorDown = gamepad2.left_trigger > TRIGGER_THRESHOLD; // uncomment line for reversible conveyor controlled by ltrigger

        // driving logic
        drive(throttle + steering, throttle - steering);

        // tape winch
        if(tapeExtend) {
            motorTape.setPower(TapeSpeed);
        }
        else if (tapeRetract) {
            motorTape.setPower(-TapeSpeed);
        }
        else {
            motorTape.setPower(0);
        }

        // tape arm
        if(tapeUp) {
            servoTape.setPosition(servoTape.getPosition() + TapeResolution);
        }
        else if(tapeDown) {
            servoTape.setPosition(servoTape.getPosition() - TapeResolution);
        }

        // sweeper control
        if(sweeperIn){
            motorConveyor.setPower(SweeperSpeed);
        }
        else if(sweeperOut) {
            motorConveyor.setPower(-SweeperSpeed);
        }
        else {
            motorSweep.setPower(0);
        }

        // conveyor
        if(conveyorUp) {
            motorConveyor.setPower(ConveyorSpeed);
        }
        /*
        else if {conveyorDown) { // uncomment to allow conveyor reversal
            motorConveyor.setPower(-ConveyorSpeed);
        }
        */
        else {
            motorConveyor.setPower(0);
        }

        // slide control
        if(slideUp){
            motorSlide.setPower(SlideSpeed);
        }
        else if(slideDown) {
            motorSlide.setPower(-SlideSpeed);
        }
        else{
            motorSlide.setPower(0);
        }

        // basket control
        if(basketRight) {
            servoBucket.setPosition(servoBucket.getPosition() + BucketResolution);
        }
        else if(basketLeft) {
            servoBucket.setPosition(servoBucket.getPosition() - BucketResolution);
        }
        // TODO: automatic return to center
    }

    @Override
    public void stop()
    {
        // TODO: retract tape winch and linear slides
    }

    /*
    * Scales an input given from joystick or trigger based on
    * motor speed cap and given deadzone value.
    *
    * @param input The input value to be scaled
    * @param deadzone Value in either direction in which signals are ignored
    * @param cap Maximum speed possible from scaling
    * @return Scaled motor speed value
    */
    double scaleInputToMotor(double input, double deadzone, double cap)
    {
        input = (Math.abs(input) > deadzone) ? input : 0;
        input = Range.clip(input,-1,1);
        input = (float)scaleInput(input);
        input = cap*input;
        return input;

    }

    // method specifically drives treads
    // TODO add a proper javadoc to this
    void drive(double lPower, double rPower)
    {
        motorLeft.setPower(scaleInputToMotor(lPower,CONTROLLER_DEFAULT_DEADZONE,DriveSpeed));
        motorRight.setPower(scaleInputToMotor(rPower,CONTROLLER_DEFAULT_DEADZONE,DriveSpeed));
    }

    // code from originally provided opmode
    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}
