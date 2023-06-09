// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConfig;

public class Drivetrain extends SubsystemBase {

  // private final NetworkTableEntry xPosEntry, yPosEntry,
  // leftOutputEntry, rightOutputEntry;

  private final CANSparkMax leftDrive;
  private final CANSparkMax rightDrive;

  private final CANSparkMax leftFollow;
  private final CANSparkMax rightFollow;

  private final MotorControllerGroup leftGroup;
  private final MotorControllerGroup rightGroup;

  // private final AHRS gyro;

  // private final DifferentialDriveOdometry odometry;

  private double leftSpeed, rightSpeed;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    leftDrive = new CANSparkMax(DrivetrainConfig.LEFT_DRIVE_PORT, MotorType.kBrushless);
    rightDrive = new CANSparkMax(DrivetrainConfig.RIGHT_DRIVE_PORT, MotorType.kBrushless);

    leftFollow = new CANSparkMax(DrivetrainConfig.LEFT_FOLLOW_PORT, MotorType.kBrushless);
    rightFollow = new CANSparkMax(DrivetrainConfig.rightFollowPort, MotorType.kBrushless);

    leftGroup = new MotorControllerGroup(leftDrive, leftFollow);
    rightGroup = new MotorControllerGroup(rightDrive, rightFollow);

    // gyro = new AHRS();

    // odometry = new DifferentialDriveOdometry(new Rotation2d(), new Pose2d());

    rightDrive.restoreFactoryDefaults();
    leftDrive.restoreFactoryDefaults();
    leftFollow.restoreFactoryDefaults();
    rightFollow.restoreFactoryDefaults();

    enableBrakeMode();

    // ShuffleboardTab driveTab = Shuffleboard.getTab("Drivetrain");

    // xPosEntry = driveTab.add("X", 0.0).getEntry();
    // yPosEntry = driveTab.add("Y", 0.0).getEntry();
    // leftOutputEntry = driveTab.add("Left Speed", 0.0).getEntry();
    // rightOutputEntry = driveTab.add("Right Speed", 0.0).getEntry();

    leftSpeed = rightSpeed = 0;
  }

  @Override
  public void periodic() {
    
    updateSpeeds(leftSpeed, rightSpeed);
  }

  public void updateSpeeds(double left, double right) {
    leftGroup.set(left);
    rightGroup.set(right);

    // leftOutputEntry.setDouble(left);
    // rightOutputEntry.setDouble(right);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void enableBrakeMode() {
    leftDrive.setIdleMode(IdleMode.kBrake);
    rightDrive.setIdleMode(IdleMode.kBrake);
    leftFollow.setIdleMode(IdleMode.kBrake);
    rightFollow.setIdleMode(IdleMode.kBrake);
  }

  public void enableCoastMode() {
    leftDrive.setIdleMode(IdleMode.kCoast);
    rightDrive.setIdleMode(IdleMode.kCoast);
    leftFollow.setIdleMode(IdleMode.kCoast);
    rightFollow.setIdleMode(IdleMode.kCoast);
  }

  public void setSpeeds(double left, double right) {
    leftSpeed = left;
    rightSpeed = right;
  }

  public double getLeftSpeed() {
    return leftSpeed;
  }

  public double getRightSpeed() {
    return rightSpeed;
  }
}
