#!/usr/bin/python3

##################################################################################################

from __future__ import print_function
import subprocess
import os
import sys
import struct
import time

RPMB_KEY = b"VUPLUSDUO4KVUPLUSDUO4KVUPLUSDUO4"
RPMB_DATA_SIZE = 256
RPMB_START_OFFSET = 2
RPMB_MAGIC_NUMBER = 20181128

DEBUG_ENABLE = False

def bp3Log(s):
	if DEBUG_ENABLE:
		print(s)

class rpmbUpdate:
	def __init__(self):
		pass

	def readSubprocess(self, cmd):
		bp3Log("[readSubprocess] cmd : %s" % str(cmd))
		p = subprocess.Popen(cmd, stdout=subprocess.PIPE, stdin=subprocess.PIPE)
		(child_stdin, child_stdout) = (p.stdin, p.stdout)
		
		p.stdin.write(RPMB_KEY)
		p.stdin.flush()

		(out, err) = p.communicate()
		bp3Log("[readSubprocess] error : %s, outsize : %d" % (err, len(out)))
		return (out, err)

	def writeSubprocess(self, cmd, data = None):
		bp3Log("[writeSubprocess] cmd : %s" % str(cmd))
		p = subprocess.Popen(cmd, stdout=subprocess.PIPE, stdin=subprocess.PIPE)
		if data:
			p.stdin.write(data)
			p.stdin.flush()

		p.stdin.write(RPMB_KEY)
		p.stdin.flush()

		(out, err) = p.communicate()
		bp3Log("[writeSubprocess] error : %s, outsize : %d" % (err, len(out)))
		return (out, err)

	def getDataFromFile(self, aFile, size):
		dlist = []
		fd = open(aFile, "rb")

		total_size = 0

		while True:
			data = fd.read(size)
			if not data:
				break

			dlist.append(data)
			total_size += len(data)

		return (dlist, total_size)

	def registerRpmbKey(self):
		arg = ["mmc", "rpmb", "write-key", "/dev/mmcblk0rpmb", "-"]
		(out, err) = self.writeSubprocess(arg)
		if err:
			return False

		return True

	def zeroPadRpmbBlock(self, inData):
		outData = inData
		if len(inData) < RPMB_DATA_SIZE:
			remain = RPMB_DATA_SIZE - len(inData)
			outData = inData + '\x00'*remain

		return outData

	def writeRpmb(self, fn):
		if not os.path.exists(fn):
			bp3Log("file not exist %s" % fn)
			return False

		if os.stat(fn).st_size > 0x7FFFFFFF:
			bp3Log("Program is not support large file size.")
			return False

		# split data
		(fn_data, total_size) = self.getDataFromFile(fn, RPMB_DATA_SIZE)
		bp3Log("[readRpmb] %s : total %d bytes" % (fn, total_size))

		# offset init
		cur_offset = RPMB_START_OFFSET

		# save total size
		packed_magic_num = struct.pack('i', RPMB_MAGIC_NUMBER)
		packed_total_size = struct.pack('i', total_size)

		data = self.zeroPadRpmbBlock(packed_magic_num + packed_total_size)
		arg = ["mmc", "rpmb", "write-block", "/dev/mmcblk0rpmb", str(hex(cur_offset)), "-", "-"]
		(out, err) = self.writeSubprocess(arg, data)
		if err:
			error_exit(err)
			return False

		cur_offset += len(data)

		# save data
		for data in fn_data:
			arg = ["mmc", "rpmb", "write-block", "/dev/mmcblk0rpmb", str(hex(cur_offset)), "-", "-"]
			(out, err) = self.writeSubprocess(arg, self.zeroPadRpmbBlock(data))

			if err:
				error_exit(err)
				return False

			cur_offset += len(data)

		return True

	def readRpmb(self, fn):
		# target file open
		outfd = open(fn, "wb")
		if not outfd:
			bp3Log("file open error %s" % fn)
			return False

		# offset init
		cur_offset = RPMB_START_OFFSET

		# read magic number & data size
		cmd = ["mmc", "rpmb", "read-block", "/dev/mmcblk0rpmb", str(hex(cur_offset)), "1", "-", "-"]
		(data, err) = self.readSubprocess(cmd)
		if err:
			error_exit(err)
			return False

		cur_offset += len(data)
		
		if data is None:
			error_exit("read data size failed!")
			return False

		magic_number, = struct.unpack('i', data[0:4])
		data_size, = struct.unpack('i', data[4:8])

		bp3Log("[readRpmb] magic_number : %d, data_size : %d" % (magic_number, data_size))

		# read data
		while data_size > 0:
			#print "data_size : ", data_size
			cmd = ["mmc", "rpmb", "read-block", "/dev/mmcblk0rpmb", str(hex(cur_offset)), "1", "-", "-"]
			(data, err) = self.readSubprocess(cmd)
			if err:
				error_exit(err)
				return False

			elif data is None:
				error_exit("read rpmb data failed!")
				return False

			if len(data) > data_size:
				data = data[:data_size]

			outfd.write(data)
			data_size -= len(data)
			cur_offset += len(data)

		outfd.close()

		return True

	def checkMagicNumber(self):
		# offset init
		cur_offset = RPMB_START_OFFSET

		# read magic number
		cmd = ["mmc", "rpmb", "read-block", "/dev/mmcblk0rpmb", str(hex(cur_offset)), "1", "-", "-"]
		(data, err) = self.readSubprocess(cmd)
		if err:
			bp3Log("[getMagicNumber] read data size failed! (%s)" % err)
			return False
		
		elif data is None:
			bp3Log("[getMagicNumber] read data size failed!")
			return False

		magic_number, = struct.unpack('i', data[0:4])

		bp3Log("[getMagicNumber] magic_number : %d" % magic_number)

		return magic_number == RPMB_MAGIC_NUMBER

def checkMagicNumber():
	rpmbupdate = rpmbUpdate()
	if rpmbupdate.getMagicNumber():
		bp3Log("[checkMagicNumber] check ok!")
		return True
	else:
		bp3Log("[checkMagicNumber] check fail!")
		return False

##################################################################################################

import glob
import os

BP3_PART_MOUNTPOINT = '/tmp/bp3/'
BP3_BIN_NAME = 'bp3.bin'
BP3_INST_PRE_PATH = '/tmp/bp3/'
OLD_BP3_INST_PRE_PATH = '/usr/local/bp3/'
BP3_INST_PATH = BP3_INST_PRE_PATH+BP3_BIN_NAME
BP3_LINK_PATH = '/'+BP3_BIN_NAME

def runCmd(cmd):
	bp3Log("CMD : %s" % cmd)
	ret = os.system(cmd)
	return ret

def runPopen(cmd):
	bp3Log("CMD : %s" % cmd)
	data = os.popen(cmd).read()
	return data

def isExist(f):
	return os.access(f, os.F_OK)

def isLink(f):
	return os.path.islink(f)

class bp3Partition:
	def __init__(self, part_name = None):
		self.partName = part_name
		self.bp3 = None
		self.part = None
		self.prepare()

	def info(self):
		data = "[%s] bp3 : %s, part : %s, mounted : %s, bp3bin : %s" % (self.partName, self.bp3, self.part, self.isMount(), self.hasBp3Bin())
		bp3Log(data)

	def prepare(self):
		self.findBp3()
		self.doMount()

	def hasBp3Bin(self):
		return self.isMount() and os.access("%s/%s" % (self.getMountPoint(), BP3_BIN_NAME), os.F_OK)

	def getMountPoint(self):
		return BP3_PART_MOUNTPOINT + self.partName

	def findBp3(self):
		self.bp3 = None
		self.part = None
		devs = glob.glob("/dev/mmcblk?")
		if devs:
			for x in devs:
				data = runPopen("sgdisk -p %s" % x)
				for x in data.splitlines():
					if self.partName in x:
						self.bp3 = x.split()[0].strip()
						self.part = runPopen("ls /dev/mmcblk*p%s" % self.bp3).strip('\n')
						return				

	def bp3Mkfs(self):
		runCmd("mke2fs %s > /dev/null 2>&1" % self.part)
		runCmd("tune2fs -m 0 %s > /dev/null 2>&1" % self.part)
		self.doMount()

	def isMount(self):
		return os.path.ismount(self.getMountPoint())

	def doUmount(self):
		if self.isMount():
			runCmd("umount %s" % (self.getMountPoint()))
			return not self.isMount()

	def doMount(self):
		if not self.isMount():
			runCmd("mkdir -p %s" % self.getMountPoint())
			runCmd("mount %s %s > /dev/null 2>&1" % (self.part, self.getMountPoint()))

		return self.isMount()

	def bp3Fsck(self):
		runCmd("sync")
		return runCmd("e2fsck -y %s > /dev/null 2>&1" % self.part) == 0

	def updateBp3Bin(self, bp3bin):
		if not self.isMount():
			self.bp3Mkfs()
			self.doMount()

		if self.isMount():
			runCmd("cp %s %s" % (bp3bin, self.getMountPoint()))

##################################################################################################

def bp3Main(newBp3Path = None):
	rpmb = rpmbUpdate()
	rpmb_checkok = rpmb.checkMagicNumber()
	bp30 = bp3Partition("bp30")
	bp31 = bp3Partition("bp31")
	bp30.info()
	bp31.info()
	bp30_mp = bp30.getMountPoint()
	bp31_mp = bp31.getMountPoint()
	
	# bp30 exist check
	if bp30.bp3 is None:
		return

	# update new bp30.bin
	if newBp3Path and isExist(newBp3Path) and not isLink(newBp3Path):
		bp30.updateBp3Bin(newBp3Path)
		bp31.updateBp3Bin(newBp3Path)
		runCmd("mv %s %s" % (newBp3Path, BP3_INST_PATH))

	# restore each other
	else:
		if not bp30.hasBp3Bin() and bp31.hasBp3Bin():
			bp3bin = "%s/%s" % (bp31_mp, BP3_BIN_NAME)
			bp30.updateBp3Bin(bp3bin)

		elif bp30.hasBp3Bin() and not bp31.hasBp3Bin():
			bp3bin = "%s/%s" % (bp30_mp, BP3_BIN_NAME)
			bp31.updateBp3Bin(bp3bin)

		elif not bp30.hasBp3Bin() and not bp31.hasBp3Bin():
			if isExist(BP3_INST_PATH):
				bp30.updateBp3Bin(BP3_INST_PATH)
				bp31.updateBp3Bin(BP3_INST_PATH)

	# link bp30.bin
	if not isExist(BP3_INST_PRE_PATH):
		runCmd("mkdir -p %s" % BP3_INST_PRE_PATH)

	if not isExist(BP3_INST_PATH):
		if bp30.hasBp3Bin():
			runCmd("cp %s/%s %s" % (bp30_mp, BP3_BIN_NAME, BP3_INST_PATH))
		elif bp31.hasBp3Bin():
			runCmd("cp %s/%s %s" % (bp31_mp, BP3_BIN_NAME, BP3_INST_PATH))

	if isExist(BP3_LINK_PATH):
		if not isLink(BP3_LINK_PATH):
			if not isExist(BP3_INST_PATH):
				bp30.updateBp3Bin(BP3_LINK_PATH)
				bp31.updateBp3Bin(BP3_LINK_PATH)
				runCmd("mv %s %s" % (BP3_LINK_PATH, BP3_INST_PATH))
			else:
				runCmd("rm %s" % BP3_LINK_PATH)
		elif os.path.realpath(BP3_LINK_PATH) != os.path.realpath(BP3_INST_PATH):
			runCmd("rm %s" % BP3_LINK_PATH)

	if not bp30.hasBp3Bin() and not bp31.hasBp3Bin():
		if rpmb_checkok:
			print("get data from rpmb..")
			rpmb.readRpmb(BP3_INST_PATH)
			if isExist(BP3_INST_PATH):
				bp30.updateBp3Bin(BP3_INST_PATH)
				bp31.updateBp3Bin(BP3_INST_PATH)
		else:
			print("Warning!!!!!!!!! we lost b3data!")

	if not isExist(BP3_LINK_PATH) and isExist(BP3_INST_PATH):
		runCmd("ln -sf %s %s" % (BP3_INST_PATH, BP3_LINK_PATH))

	if not rpmb_checkok and isExist(BP3_INST_PATH):
		print("backup %s to rpmb." % BP3_INST_PATH)
		rpmb.registerRpmbKey()
		rpmb.writeRpmb(BP3_INST_PATH)

	if isExist(OLD_BP3_INST_PRE_PATH):
		runCmd("rm -rf %s" % OLD_BP3_INST_PRE_PATH)

	bp30.doUmount()
	bp31.doUmount()
	runCmd("sync")

if __name__ == "__main__":
	newBp3Bin = None
	if len(sys.argv) == 2 and isExist(sys.argv[1]):
		newBp3Bin = sys.argv[1]
	bp3Main(newBp3Bin)

