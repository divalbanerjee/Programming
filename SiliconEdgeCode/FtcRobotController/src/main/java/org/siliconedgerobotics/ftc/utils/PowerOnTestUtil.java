package org.siliconedgerobotics.ftc.utils;
import com.qualcomm.ftcrobotcontroller.opmodes.*;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;
import com.qualcomm.robotcore.hardware.HardwareDevice;

/**
 * @deprecated
 * Created by Wei on 12/29/2015.
 * Apparently div is working on one, and I'm too lazy to finish this one
 */

@Deprecated
public class PowerOnTestUtil {

    public PowerOnTestUtil() {

    }

    public boolean attemptHardwareMapRegistration (Object part) {
        if (part instanceof HardwareDevice) {

            try {

            } catch (Exception e) {
                //RefactoredOpMode.
                return false;
            }

        }
        return false;
    }

}
