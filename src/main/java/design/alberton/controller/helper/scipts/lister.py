import math
import csv, re, numpy

from itertools import chain
from numpy import argmax
import openpyxl
import pandas as pd

# ------- Power Gen & Storage

# Power Output Function
def P(s):
    return 4 * s


# Battery Storage Capacity
def E(n):
    return 10 * n


# Maintain Cost of Solar Panels
def C(p, n):
    return 0.03 * p + 0.05 * n


def marketMultiplier(t):
    # t being time in between purchases
    if t < 0: return 1
    if t > 50:
        return 50
    else:
        return 1.5 - (t / 100)


def buyEnergy(t):
    return 0.03 * marketMultiplier(t)


def sellEnergy(t):
    return 0.02 * (1 / marketMultiplier(t))


def cityEnergyDemand():
    # energy_demand = 50*math.sin(((2*math.pi)/100)*t) + 50*math.sin(((4*math.pi)/100)*t) + 120  # kW/tick
    total_daily_energy_demand = 12000  # integrated from energy demand function
    return total_daily_energy_demand

def currentCityDemand(t):
    energy_demand = 50*math.sin(((2*math.pi)/100)*t) + 50*math.sin(((4*math.pi)/100)*t) + 120
    return energy_demand

def getRAW():
    with open('answer.csv', 'w') as f:
        writer = csv.writer(f)

    # Open raw data
    raw_data = open("output.txt", "r")

    # print(raw_data)

    file = str(raw_data.readlines())
    file = file.replace('[', '')
    file = file.replace(']', '')
    file = file.replace(r'\n', '')
    file = file.replace(",", ' ')
    file = file.replace("'", "")
    numbers = file.split()

    res = [eval(i) for i in numbers]

    return res


def populateTotalEnergy():
    SIZE_TICK = 64 * 64

    # To clear file on run
    with open('answer.csv', 'w') as f:
        writer = csv.writer(f)

    # Open raw data
    raw_data = open("output.txt", "r")

    file = str(raw_data.readlines())
    file = file.replace('[', '')
    file = file.replace(']', '')
    file = file.replace(r'\n', '')
    file = file.replace(",", ' ')
    file = file.replace("'", "")
    numbers = file.split()

    res = [eval(i) for i in numbers]

    totals = []
    for i in range(SIZE_TICK):
        totals.append(0)

    for i in range(len(res)):
        totals[i % SIZE_TICK] += res[i]

    return totals

def sortedSolarArrayInformation(totals):
    final_list = [[0] * 0 for i in range(4096)]

    count = 0
    for i in totals:
        if count == 0:
            row = 0
        else:
            row = math.floor(count / 64)
        col = count % 64

        final_list[count].append(row)
        final_list[count].append(col)
        final_list[count].append(P(i))

        count = count + 1

    x = sorted(final_list, key=lambda x: -x[2])
    return x

TOTAL_ENERGY_DATA = populateTotalEnergy()
SORTED_ENERGY_DATA = sortedSolarArrayInformation(TOTAL_ENERGY_DATA)

def getTopPositions(lst):
    DAILY_RATE = 12000
    power_generated = 0
    gridCount = 0
    i = 0

    while(power_generated < DAILY_RATE):
        power_generated = power_generated + lst[gridCount][2]
        gridCount = gridCount + 1

    return gridCount


def optimizeCost(p, n):
    init_cost = C(p, n)

x= []
y = []
for i in range(100):
    x.append(i)
    y.append(50*math.sin(((2*math.pi)/100)*int(i)) + 50*math.sin(((4*math.pi)/100)*int(i)) + 120 )

def valAt(tick, x, y, lst):
    idx = (tick*4096)+math.floor(x)+y - 1
    return lst[idx]


def allgreen_simulation():
    balance = 0
    batteries = 9
    energyStorage = 0
    count = getTopPositions(SORTED_ENERGY_DATA)
    data = getRAW()
    tickSum = 0
    cost = C(count, batteries) * 100

    with open('Simulation.txt', 'w') as f:

        print("Simulation of Renewable Energy City RetroFit\nRequired Solar Panels: {}".format(count) )
        f.write("Simulation of Renewable Energy City RetroFit\nRequired Solar Panels: {}".format(count))

        print("\nMaintenance Cost: {}$".format(cost) )
        f.write("\nMaintenance Cost: {}$".format(cost))

        for i in range(100):
            print("\n\nTick: {}".format(i))
            f.write("\n\nTick: {}".format(i))
            print("\n\nMarket Need: {}KWT".format(int(currentCityDemand(i))))
            f.write("\n\nMarket Need: {}KWT".format(int(currentCityDemand(i))))

            for j in range(count):
                tickSum += 12 * (valAt(i, SORTED_ENERGY_DATA[j][0], SORTED_ENERGY_DATA[j][1], data))

            print(" Supply Provided: {}KWT".format(int(tickSum)))
            f.write(" Supply Provided: {}KWT".format(int(tickSum)))
            f.write("\n")

            leftOverPower = int(currentCityDemand(i)) - tickSum
            f.write("Power Required: {}KWT".format(leftOverPower))

            tickSum = 0

        print("\nPlease run Solarify.java to view results\nRaw data can also be viewed in simulation.txt")
        f.write("\nPlease run Solarify.java to view results\nRaw data can also be viewed in simulation.txt")
    data_generator()

def convertTo1D():
    flatten_list = list(chain.from_iterable(SORTED_ENERGY_DATA))
    return flatten_list

def data_generator():
    df = pd.DataFrame(SORTED_ENERGY_DATA)
    df = df.head(getTopPositions(SORTED_ENERGY_DATA))
    df.to_excel('data.xlsx', index=False, header=False)

allgreen_simulation()