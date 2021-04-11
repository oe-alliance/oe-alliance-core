#!/usr/bin/python

import os

file = '/boot/STARTUP'
myfile = open(file, 'r')
data = myfile.read().replace('\n', '')
myfile.close()

rootfsdevice = data.split("=", 1)[1].split(" ", 1)[0]
kerneldevice = rootfsdevice[:-1] + str(int(rootfsdevice[-1:]) - 1)

if os.access('/dev/kernel', os.R_OK):
	os.remove('/dev/kernel')
	os.symlink(kerneldevice, '/dev/kernel')
else:
	os.symlink(kerneldevice, '/dev/kernel')

# print kerneldevice
