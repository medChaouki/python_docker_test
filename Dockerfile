
# Use an official Python runtime as the base image
FROM python:3.9

# Set the working directory in the container
WORKDIR /app

# Copy the requirements.txt file to the container
COPY requirements.txt .

#copy all python files to the container
COPY *.py ./

#copy the test image to the container
COPY test.png ./

# Install the Python dependencies
RUN pip3.9 install --upgrade -r requirements.txt

# Install dependencies
RUN apt-get update && \
    apt-get install -y x11-apps

# Set the display variable
ENV DISPLAY=:0

# Set the command to run when the container starts
CMD ["tail", "-f", "/dev/null"]



