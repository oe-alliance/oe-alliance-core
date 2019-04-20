#!/usr/bin/python

import os, sys
sys.path.append("/usr/lib/enigma2/python")

try:
	CMDLINE = open('/proc/cmdline', 'r').readline().split(' ')
	CMDLINE = CMDLINE[3].split('=')
	kerneldevice = CMDLINE[1]
	#print kerneldevice
	os.symlink(kerneldevice, '/dev/kernel')
except:
	pass
