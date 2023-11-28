import serial
import time
import serial.tools.list_ports

def find_stm32_ttyacm_port():
    # Get a list of all available serial ports
    ports = serial.tools.list_ports.comports()

    # Check each port for STM32 device
    for port in ports:
        if "STM32" in port.description.upper():  # Adjust the condition based on your STM32 device's description
            return port.device

    # Return None if no STM32 device is found
    return None


stm32_port = find_stm32_ttyacm_port()
if stm32_port:
    print(f"STM32 device found on port: {stm32_port}")
    # Open the serial port
    ser = serial.Serial(stm32_port, baudrate=9600) 

    # Read from the serial port for 2 seconds
    end_time = time.time() + 2
    while time.time() < end_time:
        if ser.in_waiting > 0:
            line = ser.readline().decode('utf-8').rstrip()
            print(line)

    # Close the serial port
    ser.close()
else:
    print("No STM32 device found.")
