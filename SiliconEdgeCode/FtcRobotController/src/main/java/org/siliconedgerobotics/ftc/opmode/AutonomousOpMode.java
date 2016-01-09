package org.siliconedgerobotics.ftc.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Wei on 1/6/2016.
 */

// the fabled autonomous
public class AutonomousOpMode extends CommonOpMode {

    public AutonomousOpMode() {
        super();
    }

    // spoiler alert: init() already defined in superclass

    public void loop(){
        /*
            TODO: Sensor input
            - push sensors
            - rotary encoders from motors
            - rotary encoders from possible odometers
            - color sensors for possible beacon shenanigans
         */
        drive(0.2,0.2);
        try {
            wait(1000);
        } catch (InterruptedException e) {
            telemetry.addData("interrupted","Wait method was interrupted!");
        }
    }

}
