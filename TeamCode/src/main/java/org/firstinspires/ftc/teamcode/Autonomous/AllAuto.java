package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous")
public class AllAuto extends OpMode  {
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor WiperMotor;

    private ElapsedTime Runtime;

    private Servo Claw;
    private Servo rollerClaw;

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
    }

    @Override
    public void start() {
        Runtime.reset();
    }

    @Override
    public void loop() {
        if(Runtime.seconds() < 2) {
            FrontLeft.setPower(0.4);
            FrontRight.setPower(0.4);
            BackLeft.setPower(0.4);
            BackRight.setPower(0.4);
        } else if (Runtime.seconds() < 4 && Runtime.seconds() > 2) {
            FrontLeft.setPower(0.0);
            FrontRight.setPower(0.0);
            BackLeft.setPower(0.0);
            BackRight.setPower(0.0);
        } else if (Runtime.seconds() < 6 && Runtime.seconds() > 4) {
        FrontLeft.setPower(0.0);
        FrontRight.setPower(0.0);
        BackLeft.setPower(0.0);
        BackRight.setPower(0.0);
    }
    }
}
