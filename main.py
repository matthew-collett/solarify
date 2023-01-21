import csv, re, numpy

SIZE_TICK = 64 * 64

# To clear file on run
with open('answer.csv', 'w') as f:
    writer = csv.writer(f)

# Open raw data
raw_data = open("output.txt", "r")

#print(raw_data)

file = str(raw_data.readlines())
file = file.replace('[', '')
file = file.replace(']','')
file =file.replace(r'\n', '')
file = file.replace(",", ' ')
file = file.replace("'", "")
numbers = file.split()


res = [eval(i) for i in numbers]


totals = []
for i in range(SIZE_TICK):
    totals.append(0)

for i in range(len(res)): # change after
    totals[i % SIZE_TICK] += res[i]


a = list(numpy.array(totals).reshape(64, 64))
