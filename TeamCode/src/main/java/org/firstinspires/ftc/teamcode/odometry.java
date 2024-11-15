package org.firstinspires.ftc.teamcode;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Utilities.Constants;

public class odometry {
    private final ElapsedTime timer;

    private double leftEncoderPosition;
    private double rightEncoderPosition;
    private double horizontalEncoderPosition;

    private double previousLeftEncoderPosition = 0;
    private double previousRightEncoderPosition = 0;
    private double previousHorizontalEncoderPosition = 0;

    public void resetEncoders() {
        leftEncoderPosition = 0;
        rightEncoderPosition = 0;
        horizontalEncoderPosition = 0;
    }

    private double x_pos = 0;
    private double y_pos = 0;
    private double heading = 0;

    public odometry(ElapsedTime timer) {

        this.timer = timer;
    }

    public void start() {
        timer.reset();
        resetEncoders();
    }

    public void update() {
        double currentTime = timer.seconds();
        double leftEncoderDelta = (leftEncoderPosition - previousLeftEncoderPosition) * Constants.DISTANCE_PER_PULSE;
        double rightEncoderDelta = (rightEncoderPosition - previousRightEncoderPosition) * Constants.DISTANCE_PER_PULSE;
        double horizontalEncoderDelta = (horizontalEncoderPosition - previousHorizontalEncoderPosition) * Constants.DISTANCE_PER_PULSE;

        double phi = (leftEncoderDelta - rightEncoderDelta) / Constants.TRACKWIDTH;
        double delta_middle_pos = (leftEncoderDelta + rightEncoderDelta) / 2;
        double delta_perp_pos = horizontalEncoderDelta - Constants.CENTER_WHEEL_DISTANCE  * phi;

        double delta_x = delta_middle_pos * cos(heading) - delta_perp_pos * sin(heading);
        double delta_y = delta_middle_pos * sin(heading) + delta_perp_pos * cos(heading);

        x_pos += delta_x;
        y_pos += delta_y;
        heading += phi;

        previousLeftEncoderPosition = leftEncoderPosition;
        previousRightEncoderPosition = rightEncoderPosition;
        previousHorizontalEncoderPosition = horizontalEncoderPosition;

}
}
