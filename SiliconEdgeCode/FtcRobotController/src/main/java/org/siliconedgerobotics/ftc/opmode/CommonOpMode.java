package org.siliconedgerobotics.ftc.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Wei on 1/8/2016.
 */
public abstract class CommonOpMode extends OpMode {

    // declare motor and servo variables
    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorSlide;
    DcMotor motorTape;
    DcMotor motorSweep;
    DcMotor motorConveyor;
    Servo servoTape;
    Servo servoBucket;

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

    public void init() {
        registerComponents();
    }
    public abstract void loop();

    private void registerComponents() {

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
    }

    void drive(double lPower, double rPower) {
        motorLeft.setPower(lPower);
        motorRight.setPower(rPower);
    }

    // TODO may need method for explicitly setting motor directions
}
