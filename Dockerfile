
# Use an official Python runtime as the base image
FROM python:3.9

# Set the working directory in the container
WORKDIR /app

# Copy the requirements.txt file to the container
COPY requirements.txt .

# Install the Python dependencies
RUN pip3.9 install --upgrade -r requirements.txt

# Set the command to run when the container starts
#CMD ["tail", "-f", "/dev/null"]
#run main.py
CMD ["python3.9", "main.py", "3"]


