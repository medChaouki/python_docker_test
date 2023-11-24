import serial
import time

# Open the serial port
ser = serial.Serial('/dev/ttyACM1', baudrate=9600) 

# Read from the serial port for 2 seconds
end_time = time.time() + 2
while time.time() < end_time:
    if ser.in_waiting > 0:
        line = ser.readline().decode('utf-8').rstrip()
        print(line)

# Close the serial port
ser.close()