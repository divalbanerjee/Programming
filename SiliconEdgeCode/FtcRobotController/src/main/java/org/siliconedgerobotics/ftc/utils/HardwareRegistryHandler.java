package org.siliconedgerobotics.ftc.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Wei on 1/8/2016.
 */

/**
* Utility class for wrapping registration methods for robot components.
* This allows the same hardware to be easily used by different opmodes.
*
* @deprecated
* Use CommonOpMode instead
*/

@Deprecated
public class HardwareRegistryHandler {

    /**
    * Hardware map, provided by the opmode utilizing this class
    */
    HardwareMap map;

    /**
    * Constructor
    * @param map The hardware map provided by an opmode
    */
    public HardwareRegistryHandler(HardwareMap map) {
        this.map = map;
    }

    public DcMotor registerMotor(String deviceName)
    {
        try {
            return map.dcMotor.get(deviceName);
        }
        catch(IllegalArgumentException e) {
            // TODO relay some kind of error message
            return null;
        }
    }

    public Servo registerServo(String deviceName) {
        try {
            return map.servo.get(deviceName);
        } catch(IllegalArgumentException e){
            // TODO relay some kind of error message
            return null;
        }
    }


}
