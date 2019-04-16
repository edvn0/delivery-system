package com.bth.Model;

public enum DTConstants {
  isRunning,
  //Variables for commands from/to SCS
  inputCommandSCS,
  outputCommandSCS,
  //Variables for controlling task thread
  runThreadIsStarted,
  runThreadIsExecuted,

  //motor for drive forwards and backwards - connected to motor port D
  motorDrive,
  //motor for steering - connected to motor port C
  motorSteer,

  //motor for crane lifting - connected multiplexer port M1
  raneRotation,
  //motor for crane lifting - connected to motor port B
  craneLift,
  //motor for grabber - connected to motor port A
  craneGrabber,

  //sensor for proximity - connect to sensor port S1
  sensorProximity,
  //sensor for line reading - connected to sensor port S3
  lineReader,
  //sensor for crane rotation movement detection S4
  touchSensor,
}
