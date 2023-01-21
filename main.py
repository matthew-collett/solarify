import math
import csv, re, numpy

from numpy import argmax


# ------- Power Gen & Storage

# Power Output Function
def P(s):
    return 4 * s


# Battery Storage Capacity
def E(n):
    return 10 * n


# Maintain Cost of Solar Panels
def C(p, n):
    return 0.03 * p * 0.05 * n


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


def populateTotalEnergy():
    SIZE_TICK = 64 * 64

    # To clear file on run
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

    totals = []
    for i in range(SIZE_TICK):
        totals.append(0)

    for i in range(len(res)):  # change after
        totals[i % SIZE_TICK] += res[i]




    #print(numpy.argmax(totals))
                                                #      row       col      power
    #final_list = [[0] * 4096 for i in range(3)]  # [[,,,,,] , [,,,,,] , [,,,,,]]

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
        final_list[count].append(i)

        count = count + 1
    #print(final_list)



    return totals



a = populateTotalEnergy()
#print(a)
