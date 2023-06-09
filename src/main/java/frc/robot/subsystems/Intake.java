package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConfig;

public class Intake extends SubsystemBase {

    private final CANSparkMax leftIntake;
    private final CANSparkMax rightIntake;
    private final CANSparkMax intakeWheels;
    private final MotorControllerGroup intakeGroup;
    private double speed;
    private boolean spinning;

    public Intake() {

        leftIntake = new CANSparkMax(IntakeConfig.LEFT_INTAKE_PORT, MotorType.kBrushless);
        rightIntake = new CANSparkMax(IntakeConfig.RIGHT_INTAKE_PORT, MotorType.kBrushless);
        intakeWheels = new CANSparkMax(IntakeConfig.INTAKE_WHEELS_PORT, MotorType.kBrushless);

        intakeGroup = new MotorControllerGroup(leftIntake, rightIntake);

        leftIntake.restoreFactoryDefaults();
        rightIntake.restoreFactoryDefaults();
        intakeWheels.restoreFactoryDefaults();

        speed = 0;
        spinning = false;
    }

    @Override
    public void periodic() {
        if (getPosition() <= IntakeConfig.MAX_EXTEND_DISTANCE || speed < 0) {
            setSpeed(speed);
        } else {
            setSpeed(0);
        }
    }

    public void updateSpeed(double speed) {
        this.speed = speed;
    }

    public void setSpeed(double speed) {
        intakeGroup.set(speed);
    }

    public double getSpeed() {
        return speed;
    }

    public double getPosition() {
        return (leftIntake.getEncoder().getPosition() + rightIntake.getEncoder().getPosition()) / 2;
    }

    public boolean getSpinning() {
        return spinning;
    }

    public void startSpinning() {
        spinning = true;
        intakeWheels.set(IntakeConfig.SPINNING_SPEED);
    }

    public void stopSpinning() {
        spinning = false;
        intakeWheels.set(0.0);
    }

    public void toggleSpinning() {
        if (spinning)
            stopSpinning();
        else
            startSpinning();
    }

    public void spinBackwards() {
        spinning = true;
        intakeWheels.set(-IntakeConfig.SPINNING_SPEED);
    }

    public boolean atState(IntakeState state) {
        return Math.abs(getPosition() - getExtendDistance(state)) < IntakeConfig.POSITION_TOLERANCE;
    }

    public static double getExtendDistance(IntakeState state) {
        switch (state) {
            case Retracted:
                return 0;
            case Middle:
                return IntakeConfig.MID_EXTEND_DISTANCE;
            case Extended:
                return IntakeConfig.MAX_EXTEND_DISTANCE;
                default:
            return 0;
        }
    }

    public enum IntakeState {
        Retracted,
        Middle,
        Extended
    }
}
