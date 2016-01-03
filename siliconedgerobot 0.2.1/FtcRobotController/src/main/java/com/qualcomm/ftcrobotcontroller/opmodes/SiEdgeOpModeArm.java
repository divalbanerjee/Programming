package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
//Pavan Vattyam CREATED THIS
//Wei Gao refactored this so it would be worse

public class SiEdgeOpModeArm extends OpMode {

    DcMotor motorLeft1;
    DcMotor motorRight1;

    DcMotor motorTape;

    DcMotor motorBrush;


    public SiEdgeOpModeArm() {

    }

    //initialization routine
    @Override
    public void init() {


        motorLeft1 = hardwareMap.dcMotor.get("lmotor_1");
        motorRight1 = hardwareMap.dcMotor.get("rmotor_1");
        //lmotor_1 ----> LeftMotor
        //rmotor_1 ----> RightMotor

        motorTape = hardwareMap.dcMotor.get("motor_c");

        motorBrush = hardwareMap.dcMotor.get("motor_brush");

        motorLeft1.setDirection(DcMotor.Direction.FORWARD);
        motorRight1.setDirection(DcMotor.Direction.FORWARD);

        //Turn the collector
        motorTape.setDirection(DcMotor.Direction.FORWARD);
        motorBrush.setDirection(DcMotor.Direction.FORWARD);
    }

    //main function body
    @Override
    public void loop() {

        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left
        //If you can read this, you get a free dooby. Doobies are good, doobies are good, doobies are good, doobies are good

        //FIXME kind of a kludge, recheck hardware to see if motors were mixed up
        //FIXME b/c x and y axes are flippped

        float throttle = -gamepad1.left_stick_x;
        float direction = gamepad1.left_stick_y;


        //Silicon Edge: We Specialize in Kludges

        float right = throttle - direction;
        float left = throttle + direction;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);


        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speedy


        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);
        //Sets theo
        // write the values to the motors
        motorLeft1.setPower(left);
        motorRight1.setPower(right);


        /*Using the button a on controller 2, when pressed, Tape motor turns,
         on release, motor doesn't turn
         */

        //gamepad button events
            //Turn tape measure when a is pressed and retrat when b is pressed
            if(gamepad1.a) {
                motorTape.setPower(0.5);
            }

            //motorTape.setPower(gamepad2.a ? -0.5 : 0);
            else if(gamepad1.b) {
                motorTape.setPower(-0.5);
            }
            else{
                motorTape.setPower(0.0);
            }

        //turn spinner when a is pressed reverse when b is presed.
        if(gamepad2.a){
            motorBrush.setPower(.5);
        }else if(gamepad2.b){
            motorBrush.setPower(-.5);
        }
        else{
            motorBrush.setPower(0);
        }

        /*telemetry.addData("Text", "*** Robot Data***");
        *//*telemetry.addData("arm", "arm:  " + String.format("%.2f", armPosition));
        telemetry.addData("claw", "claw:  " + String.format("%.2f", clawPosition));*//*
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
*/
    }


    @Override
    public void stop() {

    }

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

