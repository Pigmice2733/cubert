package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConfig;

public class Shooter extends SubsystemBase {
    private final CANSparkMax leftShoot, rightShoot;
    private double speed;

    MotorControllerGroup shooters;

    public Shooter() {
        leftShoot = new CANSparkMax(ShooterConfig.LEFT_SHOOT_PORT, MotorType.kBrushless);
        rightShoot = new CANSparkMax(ShooterConfig.RIGHT_SHOOT_PORT, MotorType.kBrushless);

        leftShoot.restoreFactoryDefaults();
        rightShoot.restoreFactoryDefaults();

        shooters = new MotorControllerGroup(leftShoot, rightShoot);

        speed = 0;

    }

    @Override
    public void periodic() {
        updateSpeeds(speed);
    }

    public void updateSpeeds(double speed) {
        shooters.set(speed * ShooterConfig.MAX_SPEED);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
