import numpy as np 
from perlin_noise import PerlinNoise
import matplotlib.pyplot as plt
import sys
import os
import random

####### SETTINGS ##########
n = 100
x = 64 
y = 64
slopeMin = 10
slopeMax = 30

####### CMD ARGS #########
if len(sys.argv) != 3:
    print("Invalid number of arguments. Try python3 combined.py <start seed> <end seed>")
    os._exit(1)

seed_start = int(sys.argv[1])
seed_end = int(sys.argv[2])

######## DAY FUNC ##################
# 1. Generates the luminosity at a specific point at a simulation time
def dayFunc(t, upSlope, downSlope):
    t = t % 100
    cycleLen = 100
    if t < cycleLen/2:
        return min(float(1/upSlope*t), 1)
    elif t >= cycleLen/2:
        return max(float(1 - 1/downSlope*(t - cycleLen/2)), 0)
##### CLOUD BIAS #######
# 1. Multiplies Filtered Sun Value
# 2. Caps at 1 (no cloud)
# 3. Works within a radius at a randomly chosen point 
#   at the beginning of the simulation.
def cloudBias(val, x, y, multiplier, radius, circleX, circleY):
    p1 = np.array((x, y))
    p2 = np.array((circleX, circleY))
    dist = np.linalg.norm(p1 - p2)

    if dist <= radius:
        return min(val * multiplier, 1)
    else:
        return val

######### MAKE NOISE FUNC ##############
# 1. Generates an x by y grid of perlin noise 
# 2. Accepts a seed
def makeNoise(x, y, seed):
    noise1 = PerlinNoise(octaves=3, seed=seed)
    noise2 = PerlinNoise(octaves=6, seed=seed)
    noise3 = PerlinNoise(octaves=12, seed=seed)
    noise4 = PerlinNoise(octaves=24, seed=seed)

    xpix, ypix = x, y
    pic = []
    for i in range(xpix):
        row = []
        for j in range(ypix):
            noise_val = noise1([i/xpix, j/ypix])
            noise_val += 0.5 * noise2([i/xpix, j/ypix])
            noise_val += 0.25 * noise3([i/xpix, j/ypix])
            noise_val += 0.125 * noise4([i/xpix, j/ypix])

            row.append(noise_val)
        pic.append(row)

    return pic

########## PREVIEW START/END TRANSITION ##############
def preview(np_start, np_end, n):
    np_slope = (np_end - np_start) / n

    print(np_start)
    print("\n\n########\n\n")
    print(np_end)
    print("\n\n########\n\n")
    print(np_slope)

    fig = plt.figure()
    ax = fig.gca()
    h = ax.imshow(np_start)

    current = np_start
    for i in range(0, n):
        h.set_data(current)
        plt.draw()
        print(current)
        print("~~~~~~~~~~~")
        current += np_slope
        plt.pause(1e-3)


###### WRITE DATA TO FILE #########
def writeToFile(np_start, np_end, n):
    # Calculate transition slope
    np_slope = (np_end - np_start) / n

    # Calculate up and down slope 
    random.seed(seed_start)
    upSlope = random.randrange(slopeMin, slopeMax)

    random.seed(seed_end)
    downSlope = random.randrange(slopeMin, slopeMax)

    # Initialize solar values 
    vals = np.ones((x, y))

    # Initialize graph
    fig = plt.figure()
    ax = fig.gca()
    h = ax.imshow(vals)

    # Manage numpy data folder
    if os.path.isdir('np_output'):
        os.rmdir('np_output')
    os.mkdir('np_output')

    # Open file for output
    f = open('output.txt', 'w')

    # Start at first mask and iterate from there
    currentMask = np_start
    for i in range(0, n):
        # Populate array with daytime values
        vals = np.ones((x, y))
        vals *= dayFunc(i, upSlope, downSlope)

        # Apply mask
        vals = np.multiply(vals, currentMask)

        # Re-normalize
        # vals *= 25
        max_val = np.max(vals)
        if max_val:
            vals /= max_val

        print(vals)
        # print(np.max(vals))

        # Update mask
        currentMask += np_slope

        # Write to file
        f.write(str(vals.tolist()))
        f.write("\n\n")

        # Use numpy save to file
        np.savetxt('np_output/np_output' + str(i) + '.txt', vals)

        # Update image graph
        h.set_data(vals)
        plt.draw()
        plt.pause(1e-3)

    f.close()
###### PREVIEW DAY FUNCTION #########
def previewDay(seed_start, seed_end):
    random.seed(seed_start)
    upSlope = random.randrange(minSlope, maxSlope)

    random.seed(seed_end)
    downSlope = random.randrange(minSlope, maxSlope)

    for t in range(n):
        dayVal = dayFunc(t, upSlope, downSlope)
        print(dayVal)


start = makeNoise(x, y, seed_start)
end = makeNoise(x, y, seed_end)

############## SETUP START / END NUMPY ARRAYS & NORMALIZE ##################
np_start = np.array(start)
np_start_norm = np.linalg.norm(np_start)
np_start /= np_start_norm
np_start = np.abs(np_start)

np_end = np.array(end)
np_end_norm = np.linalg.norm(np_end)
np_end /= np_end_norm
np_end = np.abs(np_end)

############## PREVIEW OR WRITE TO FILE ##################
# preview(np_start, np_end, n)
writeToFile(np_start, np_end, n)
# previewDay(seed_start, seed_end)
