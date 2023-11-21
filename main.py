import random
import sys

def print_random_numbers(n):
    for _ in range(n):
        print(random.randint(1, 100))

# Get the number of times to print from the command-line arguments
num_times = int(sys.argv[1])

for _ in range(num_times):
    print_random_numbers(5)