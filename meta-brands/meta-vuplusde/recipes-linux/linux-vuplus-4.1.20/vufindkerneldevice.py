#!/usr/bin/python

# Find kerneldevice in vuduo4klite

import os, time

data = None

if not data:
	try:
		# gb default
		print("try gb default")
		file = '/boot/STARTUP'
		myfile = open(file, 'r')
		data = myfile.read().replace('\n', '')
		myfile.close()
	except:
		print("NOK")
		pass

if not data:
	try:
		# vu future default
		print("try future default")
		file = '/boot/start/STARTUP'
		myfile = open(file, 'r')
		data = myfile.read().replace('\n', '')
		myfile.close()
	except:
		print("NOK")
		pass

if not data:
	try:
		# tmp mount
		from tempfile import mkdtemp
		tempDir = mkdtemp(prefix="boot_")
		print("try tmp mount: " + tempDir)
		os.system("/bin/mount -r -t auto /dev/mmcblk0p1 " + tempDir)
		time.sleep(2)
		file = tempDir + '/STARTUP'
		myfile = open(file, 'r')
		data = myfile.read().replace('\n', '')
		myfile.close()
	except:
		print("NOK")
		pass

if data:
	try:
		kerneldevice = data.split("kernel=", 1)[1].split(" ", 1)[0]

		if os.access('/dev/kernel', os.R_OK): os.remove('/dev/kernel')
		os.symlink(kerneldevice, '/dev/kernel')
		print("Kernel device: " + kerneldevice + " successful mounted to /dev/kernel")
	except:
		print("symlink /dev/kernel NOK")
		pass
