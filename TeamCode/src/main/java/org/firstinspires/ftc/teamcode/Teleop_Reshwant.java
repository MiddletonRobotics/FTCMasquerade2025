package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name= "masq")
public class Teleop_Reshwant extends OpMode {
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor BackLeft;
    private DcMotor BackRight;

    private DcMotor leftViperMotor;

    private DcMotor rightViperMotor;

    private DcMotor Viper;
    private boolean xButtonPreviousState;
    private boolean SlowMode;

    private double tgtPower;

    double ticks = 537.7;
    double newTarget;

    @Override
    public void init() {
        FrontLeft = hardwareMap.get(DcMotor.class, "FL");
        FrontRight = hardwareMap.get(DcMotor.class, "FR");
        BackLeft = hardwareMap.get(DcMotor.class, "BL");
        BackRight = hardwareMap.get(DcMotor.class, "BR");
        Viper = hardwareMap.get(DcMotor.class, "viper");
        leftViperMotor = hardwareMap.get(DcMotor.class, "LVM");
        rightViperMotor = hardwareMap.get(DcMotor.class, "RVM");


        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        Viper.setDirection(DcMotorSimple.Direction.FORWARD);
        leftViperMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightViperMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Viper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftViperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightViperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        xButtonPreviousState = false;
        SlowMode = false;

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        double drive = -gamepad1.left_stick_y * 0.7;
        double strafe = gamepad1.left_stick_x;
        double rotation = gamepad1.right_stick_x;

        telemetry.addData("Controller Left Y", -gamepad1.left_stick_y);
        telemetry.addData("Controller Left X", gamepad1.left_stick_x);
        telemetry.addData("Controller Right X", gamepad1.right_stick_x);


        if (gamepad1.x && !xButtonPreviousState) {
            SlowMode = !SlowMode;
        }

        xButtonPreviousState = gamepad1.x;


        double[] thetaSpeeds = {
                (drive + strafe + rotation),
                (drive - strafe - rotation),
                (drive - strafe + rotation),
                (drive + strafe - rotation),
        };

        if (SlowMode) {
            FrontLeft.setPower(thetaSpeeds[0] * 0.25);
            FrontRight.setPower(thetaSpeeds[1] * 0.25);
            BackLeft.setPower(thetaSpeeds[2] * 0.25);
            BackRight.setPower(thetaSpeeds[3] * 0.25);
        } else {
            FrontLeft.setPower(thetaSpeeds[0]);
            FrontRight.setPower(thetaSpeeds[1]);
            BackLeft.setPower(thetaSpeeds[2]);
            BackRight.setPower(thetaSpeeds[3]);
        }


        if (gamepad1.right_bumper) {
            Viper.setPower(1);
        } else {
            Viper.setPower(0);
            Viper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        if (gamepad1.left_bumper) {
            Viper.setPower(0.5);
        } else {
            Viper.setPower(0);
            Viper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        if (gamepad1.dpad_up) {
            leftViperMotor.setPower(0.75);
            rightViperMotor.setPower(0.75);
        } else {
            leftViperMotor.setPower(0);
            leftViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        if (gamepad1.dpad_down) {
            leftViperMotor.setPower(0.75);
            rightViperMotor.setPower(0.75);
        } else {
            leftViperMotor.setPower(0);
            leftViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightViperMotor.setPower(0);
            rightViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        }
        tgtPower = -this.gamepad1.left_stick_y;
        // check to see if we need to move the servo.

        telemetry.addData("Target Power", tgtPower);
        telemetry.addData("Status", "Running");
        telemetry.update();
        if (gamepad1.a) {
            encoder(4);
        }
        telemetry.addData("Left Viper Motor Ticks: ", leftViperMotor.getCurrentPosition());
        telemetry.addData("Right Viper Motor Ticks: ", rightViperMotor.getCurrentPosition());

        if (gamepad1.b) {
            tracker();
        }
    }
    public void encoder (int turnage) {
        newTarget = ticks/turnage;
        leftViperMotor.setTargetPosition((int)newTarget);
        rightViperMotor.setTargetPosition((int)newTarget);
        leftViperMotor.setPower(1);
        rightViperMotor.setPower(-1);
        leftViperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightViperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void tracker() {
        leftViperMotor.setTargetPosition(0);
        rightViperMotor.setTargetPosition(0);
        leftViperMotor.setPower(-1);
        rightViperMotor.setPower(1);
        leftViperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightViperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

}
