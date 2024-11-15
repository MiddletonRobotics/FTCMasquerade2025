
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "StrafeAuto", group = "Auto")
public class AutoStrafe extends LinearOpMode {

    // Declare motor variables
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;


    @Override
    public void runOpMode() {
        // Initialize motors
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FL");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FR");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BL");
        backRightMotor = hardwareMap.get(DcMotor.class, "BR");

        // Set all motors to brake when stopped
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse on side
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Strafe left for 2 seconds
        turnLeft();

        sleep(2000);

        // Stop for a short time
        stopMotors();
        sleep(500);

        // Strafe right for 2 seconds
        turnRight();
        sleep(2000);

        // Stop all motors
        stopMotors();
    }

    // Method to strafe left
    private void turnLeft() {
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setPower(0.4);
        frontRightMotor.setPower(-0.4);
        backLeftMotor.setPower(-0.4);
        backRightMotor.setPower(0.4);
    }

    // Method to strafe right
    private void turnRight() {
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setPower(-0.4);
        frontRightMotor.setPower(0.4);
        backLeftMotor.setPower(0.4);
        backRightMotor.setPower(-0.4);
    }

    // Method to stop all motors
    private void stopMotors() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }
}
