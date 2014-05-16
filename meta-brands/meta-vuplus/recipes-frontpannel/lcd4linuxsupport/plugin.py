
import os
from time import time
from datetime import datetime

from enigma import eTimer
from Components.config import ConfigSelection, ConfigSelectionNumber, ConfigSlider, ConfigYesNo
from Plugins.Plugin import PluginDescriptor
from fcntl import ioctl

from pngutil import png_util
pngutil = png_util.PNGUtil()
lcd4linuxPluginPath = "/usr/lib/enigma2/python/Plugins/Extensions/LCD4linux"

LCDType1_id_lver = "150"
LCDType1_id = "350"

from Plugins.Extensions.LCD4linux import plugin

def getResolution(t, r="0"):
	if t[:1] == "5":
		ttt = LCD4linux.xmlLCDType.value.split("x")
		MAX_W,MAX_H = int(ttt[0]),int(ttt[1])
	elif t[1:] == "1":
		MAX_W,MAX_H = 320,240
	elif t[1:] == "2":
		MAX_W,MAX_H = 240,320
	elif t[1:] in ["3","4","5","10"]:
		MAX_W,MAX_H = 800,480
	elif t[1:] in ["6","9","11","12"]:
		MAX_W,MAX_H = 800,600
	elif t[1:] in ["7","8","13"]:
		MAX_W,MAX_H = 1024,600
	elif t[1:] == "17":
		MAX_W,MAX_H = 220,176
	elif t[1:] == "18":
		MAX_W,MAX_H = 255,64
	elif t[1:] == "30":
		MAX_W,MAX_H = 400,240
	elif t[1:] == "20":
		MAX_W,MAX_H = LCD4linux.SizeW.value,LCD4linux.SizeH.value
	elif t[1:] == "50": # vuduo2
		MAX_W,MAX_H = 400,240
	if r in ["90","270"]:
		MAX_W,MAX_H = MAX_H,MAX_W
	return MAX_W,MAX_H

plugin_writeHelligkeit = None
g_min = 0
g_max = None
def writeHelligkeit_lver(hell):
	global g_min
	global g_max
	global plugin_writeHelligkeit
	global LCDType1_id_lver
	plugin_writeHelligkeit(hell)
	if plugin.config.plugins.LCD4linux.LCDType1.value == LCDType1_id_lver:
		if g_max is None:
			g_min, g_max = getHellRange(plugin.config.plugins.LCD4linux.Helligkeit)
		updateBrightness(int(hell), g_min, g_max)

def writeHelligkeit(hell,hell2,hell3,STOP = False):
	global g_min
	global g_max
	global old_hell
	global plugin_writeHelligkeit
	global LCDType1_id
	plugin_writeHelligkeit(hell, hell2, hell3, STOP)
	if plugin.LCD4linux.LCDType1.value == LCDType1_id:
		if g_max is None:
			g_min, g_max = getHellRange(plugin.LCD4linux.Helligkeit)
		updateBrightness(int(hell), g_min, g_max)

def getHellRange(ins):
	try:
		if isinstance(ins, ConfigSelectionNumber):
			choices = []
			for s in ins.choices.choices:
				choices.append(int(s))
			return (min(choices), max(choices))
		elif isinstance(ins, ConfigSlider):
			return (ins.min, ins.max)
		else:
			return (0,10)
	except:
		return (0,10)

old_hell = 40
def updateBrightness(hell, _min, _max):
	try:
		if _min == _max: return
		hell = int( 255*hell/(_max - _min) )
		if hell >= 250:
			hell = 255
		global old_hell
		if hell == old_hell:
			return
		else:
			old_hell = hell
		print "[LCD4linux Support] update Brightness : ",hell
		led_fd = open("/dev/lcd2",'rw')
		ioctl(led_fd, 0x10, hell)
		led_fd.close()
	except:
		pass

class pngUtilTimer:
	def __init__(self):
		self.PIC = "/tmp/dpf"
		self.updateTimer = eTimer()
		self.updateTimer.callback.append(self.updateLCD)
		self.pngutilconnect = pngutil.connect()
		self.last_mod_time = 0
		self.oldPluginVer = False

	def startUpdateTimer(self):
		if self.pngutilconnect :
			self.updateTimer.start(1000, True)

	def updateLCD(self):
		self.updateTimer.stop()
		if self.oldPluginVer:
			lcd = plugin.config.plugins.LCD4linux.LCDType1.value
		else:
			lcd = plugin.LCD4linux.LCDType1.value
		if lcd[1:] == "50":
			if os.path.exists(self.PIC):
				modify_time = os.stat(self.PIC).st_mtime
				if modify_time != self.last_mod_time:
					self.last_mod_time = modify_time
					pngutiltime = time()
					pngutil.send(self.PIC)
					# print "[LCD4linux Support] time (write to lcd) : ",time()-pngutiltime
		self.updateTimer.start(500, True)

	def setLcd4linuxDuo2(self):
		global plugin_writeHelligkeit
		try:
			lcdtype = plugin.LCDType
			print "[LCD4linux Support] set LCD4linux for DUO2 (ver %s)"%plugin.Version
			if plugin.Version == "V0.8r3":
				global LCDType1_id_lver
				self.oldPluginVer = True
				lcdtype.append((LCDType1_id_lver, _("Vu+ Duo2 LCD 400x240")))
				plugin.config.plugins.LCD4linux.LCDType1 = ConfigSelection(choices = lcdtype, default=LCDType1_id_lver)
				plugin.config.plugins.LCD4linux.Standby = ConfigSelection(choices = [("0", _("off")), ("1", _("on"))], default="0")
				plugin.config.plugins.LCD4linux.Thread = ConfigYesNo(default = True)
				plugin.config.plugins.LCD4linux.Helligkeit = ConfigSlider(default = 1,  limits = (0, 7))
				plugin_writeHelligkeit = plugin.writeHelligkeit
				plugin.writeHelligkeit = writeHelligkeit_lver
			else:
				global LCDType1_id
				lcdtype.append((LCDType1_id, _("Vu+ Duo2 LCD 400x240")))
				plugin.LCD4linux.LCDType1 = ConfigSelection(choices = lcdtype, default=LCDType1_id)
				plugin.LCD4linux.Standby = ConfigSelection(choices = [("0", _("off")), ("1", _("on"))], default="0")
				plugin.LCD4linux.Helligkeit = ConfigSelectionNumber(0, 10, 1, default = 2)
				plugin_writeHelligkeit = plugin.writeHelligkeit
				plugin.writeHelligkeit = writeHelligkeit
			plugin.getResolution = getResolution
			self.PIC = plugin.PIC+".png"
			return True
		except:
			return False

pngutiltimer = pngUtilTimer()

def lcdtimer(reason, **kwargs):
	if pngutiltimer.setLcd4linuxDuo2():
		pngutiltimer.startUpdateTimer()

def Plugins(**kwargs):
	list = []
	global lcd4linuxPluginPath
	if os.path.exists(lcd4linuxPluginPath+"/plugin.pyo"):
		list.append(
			PluginDescriptor(name="LCD4linuxSupport",
			description="LCD4linuxSupport",
			where = [PluginDescriptor.WHERE_SESSIONSTART],
			fnc = lcdtimer))
	return list

