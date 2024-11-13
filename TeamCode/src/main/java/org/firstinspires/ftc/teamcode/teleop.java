package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utilities.Constants;

@TeleOp(name= "teleop")
public class teleop extends OpMode {
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor WiperMotor;

    private Servo Claw;
    private Servo rollerClaw;

    private boolean aButtonPreviousState;
    private boolean bButtonPreviousState;
    private boolean xButtonPreviousState;
    boolean leftBumperButtonPreviousState;
    boolean rightBumperButtonPreviousState;
    private enum DrivetrainMode {
        SLOW,
        FAST,
        NORMAL
    };
    private DrivetrainMode DrivetrainState;

    @Override
    public void init() {
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");

        Claw = hardwareMap.get(Servo.class, "Claw");
        Claw.setDirection(Servo.Direction.REVERSE);

        WiperMotor = hardwareMap.get(DcMotor.class, "WiperMotor");
        WiperMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        rollerClaw = hardwareMap.get(Servo.class, "rollerClaw");
        rollerClaw.setDirection(Servo.Direction.REVERSE);

        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);

        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        WiperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        aButtonPreviousState = false;
        bButtonPreviousState = false;
        xButtonPreviousState = false;
        leftBumperButtonPreviousState = false;
        rightBumperButtonPreviousState = false;

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop(){}

    @Override
    public void start(){
        resetRuntime();
        Claw.setPosition(0.0);
        rollerClaw.setPosition(1.0);
        WiperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        DrivetrainState = DrivetrainMode.NORMAL;
    }

    @Override
    public void loop() {
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotation = gamepad1.right_stick_x;

        telemetry.addData("Controller Left Y", -gamepad1.left_stick_y);
        telemetry.addData("Controller Left X", gamepad1.left_stick_x);
        telemetry.addData("Controller Right X", gamepad1.right_stick_x);
        telemetry.addData("Claw Position", Claw.getPosition());

        if(gamepad1.a && !aButtonPreviousState) {
            Claw.setPosition(0.03);
        } else if (gamepad1.b && bButtonPreviousState) {
            Claw.setPosition(0.55);
        }

        if(gamepad1.left_bumper && !leftBumperButtonPreviousState) {
            WiperMotor.setTargetPosition(Constants.ViperUpperGoalPosition);
            WiperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            WiperMotor.setPower(0.75);
        } else if(gamepad1.right_bumper && !rightBumperButtonPreviousState) {
            WiperMotor.setTargetPosition(Constants.ViperRetractedPosition);
            WiperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            WiperMotor.setPower(0.75);
        }

        if(gamepad1.x && !xButtonPreviousState) {
            if(DrivetrainState == DrivetrainMode.SLOW) {
                DrivetrainState = DrivetrainMode.NORMAL;
            } else {
                DrivetrainState = DrivetrainMode.SLOW;
            }
        }

        aButtonPreviousState = gamepad1.a;
        bButtonPreviousState = gamepad1.b;
        xButtonPreviousState = gamepad1.x;
        leftBumperButtonPreviousState = gamepad1.left_bumper;
        rightBumperButtonPreviousState = gamepad1.right_bumper;

        double[] thetaSpeeds = {
                (drive + strafe + rotation),
                (drive - strafe - rotation),
                (drive - strafe + rotation),
                (drive + strafe - rotation),
        };

        if(DrivetrainState == DrivetrainMode.SLOW) {
            FrontLeft.setPower(thetaSpeeds[0] * 0.25);
            FrontRight.setPower(thetaSpeeds[1] * 0.25);
            BackLeft.setPower(thetaSpeeds[2] * 0.25);
            BackRight.setPower(thetaSpeeds[3] * 0.25);
        } else if(DrivetrainState == DrivetrainMode.FAST) {
            FrontLeft.setPower(thetaSpeeds[0] * 0.95);
            FrontRight.setPower(thetaSpeeds[1] * 0.95);
            BackLeft.setPower(thetaSpeeds[2] * 0.95);
            BackRight.setPower(thetaSpeeds[3] * 0.95);
        } else {
            FrontLeft.setPower(thetaSpeeds[0] * 0.75);
            FrontRight.setPower(thetaSpeeds[1] * 0.75);
            BackLeft.setPower(thetaSpeeds[2] * 0.75);
            BackRight.setPower(thetaSpeeds[3] * 0.75);
        }
    }
}

