package org.siliconedgerobotics.ftc.opmode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.siliconedgerobotics.ftc.opmode.RefactoredOpMode;

/**
 * Created by Wei on 1/6/2016.
 */
public abstract class AppendageHandler {

    abstract DcMotor registerAppendageMotor(String motorName);
    abstract Servo registerAppendageServo(String servoName);

}
