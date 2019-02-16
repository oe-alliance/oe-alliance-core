#!/usr/bin/python

import os, sys
sys.path.append("/usr/lib/enigma2/python")
from boxbranding import getMachineMtdKernel

try:
	STARTUP = open('/boot/STARTUP', 'r').readline().split(' ')
	STARTUP = STARTUP[1].split('.')
	kerneldevice = STARTUP[1]
	if kerneldevice == "kernel":
		kerneldevice = getMachineMtdKernel()
	#print kerneldevice
	os.symlink("/dev/" + kerneldevice, '/dev/kernel')
except:
	pass
