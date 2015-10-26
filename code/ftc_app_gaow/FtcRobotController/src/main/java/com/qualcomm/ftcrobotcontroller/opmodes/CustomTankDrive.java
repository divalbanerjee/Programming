package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * @author Wei Gao
 * @version 0.0.1
 */
//FIXME test foo bar
/** Variant of K9TankDrive modified for 4 motors */
public class CustomTankDrive extends K9TankDrive {

    //master motor direction control
    boolean robotMotionReversed = false;

    //telemetry certain debug data on/off
    //TODO see if this can be controlled by hardware
    boolean telemetryEnabled = true;

    //motor names as seen by the hardware
    String lMotor1_Name = "lmotor_1";
    String lMotor2_Name = "lmotor_2";
    String rMotor1_Name = "rmotor_1";
    String rMotor2_Name = "rmotor_2";

    //create motor objects
    //two per side called in sync
    DcMotor lMotor1;
    DcMotor lMotor2;
    DcMotor rMotor1;
    DcMotor rMotor2;

    //need a constructor
    public CustomTankDrive()
    {
        //superconstructor call since no real changes are needed
        //it's basically a duplicate and modified K9TankDrive
        //also allows inheritance of stop() method - at least until we need to get data from that
        super();
    }

    /** control all 4 motors at once using speed for each side */
    public void drive(float rSpeed, float lSpeed)
    {
        lMotor1.setPower(lSpeed);
        lMotor2.setPower(lSpeed);
        rMotor1.setPower(rSpeed);
        rMotor2.setPower(rSpeed);
    }

    /** Initialization routine called at start of match */
    @Override
    public void init()
    {
        //get the motors from the hardware map
        lMotor1 = hardwareMap.dcMotor.get(lMotor1_Name);
        lMotor2 = hardwareMap.dcMotor.get(lMotor2_Name);
        rMotor1 = hardwareMap.dcMotor.get(rMotor1_Name);
        rMotor2 = hardwareMap.dcMotor.get(rMotor2_Name);

        //ternaries ensure that opposing motors always run in opposite directions
        //TODO ensure that the robot drives forward when robotMotionReversed==false
        DcMotor.Direction lDriveDir = (robotMotionReversed ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
        DcMotor.Direction rDriveDir = (robotMotionReversed ? DcMotor.Direction.FORWARD : DcMotor.Direction.REVERSE);
        //get and relay motor direction telemetry data
        String lDir = (lDriveDir == DcMotor.Direction.REVERSE) ? "REVERSE" : "FORWARD";
        String rDir = (rDriveDir == DcMotor.Direction.REVERSE) ? "REVERSE" : "FORWARD";
        if(telemetryEnabled)
        {
            telemetry.addData("SpamWarning","Telemetry is enabled. Expect log spam in loop()");
            telemetry.addData("lDirection", "lDir==" + lDir);
            telemetry.addData("rDirection", "rDir==" + rDir);
        }
        //set the correct motor direction
        lMotor1.setDirection(lDriveDir);
        lMotor2.setDirection(lDriveDir);
        rMotor1.setDirection(rDriveDir);
        rMotor2.setDirection(rDriveDir);
    }

    /** Main function which loops during teleop play */
    @Override
    public void loop()
    {
        //for reference:
        //gamepad1, gamepad2 can be referenced directly

        //TODO use the equation from Team 7612 code to put robot movement on one joystick
        //get the values on each desired joystick
        float left = -gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;

        //Range.clip "clips" the values
        //so they're never beyond +/- 1
        //Range.clip(number, lowerBound, upperBound)
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        //scale the inputs correctly, then cast back to float
        left = (float)scaleInput(left);
        right = (float)scaleInput(right);

        //get telemetry
        if(telemetryEnabled)
        {
            telemetry.addData("yLeftSpeed",left);
            telemetry.addData("yRightSpeed",right);
        }
        //drive the motors
        drive(right,left);


    }

    //TODO add stop() method if final data needed from sensors (in future)

}
