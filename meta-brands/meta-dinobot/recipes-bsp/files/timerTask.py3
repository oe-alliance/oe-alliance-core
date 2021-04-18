from __future__ import print_function
import os
import xml.etree.cElementTree
from datetime import datetime
from time import ctime, time, strftime, localtime, mktime
from threading import Timer

class TIMERTYPE:
    def __init__(self):
        pass

    NONE = 0
    WAKEUP = 1
    WAKEUPTOSTANDBY = 2
    AUTOSTANDBY = 3
    AUTODEEPSTANDBY = 4
    STANDBY = 5
    DEEPSTANDBY = 6
    REBOOT = 7
    RESTART = 8

class AFTEREVENT:
    def __init__(self):
        pass

    NONE = 0
    WAKEUP = 1
    WAKEUPTOSTANDBY = 2
    STANDBY = 3
    DEEPSTANDBY = 4

def delayrun1():
    os.system('reboot')

def delayrun(laser):
    if laser == 0:
        os.system('hipmoc')
    else:
        os.system('hipmoc %d' % laser)

def findPowerTimerFrist(xml):
    timertype = str(xml.get("timertype") or "wakeup")
    timertype = {
            "nothing": TIMERTYPE.NONE,
            "wakeup": TIMERTYPE.WAKEUP,
            "wakeuptostandby": TIMERTYPE.WAKEUPTOSTANDBY,
            "autostandby": TIMERTYPE.AUTOSTANDBY,
            "autodeepstandby": TIMERTYPE.AUTODEEPSTANDBY,
            "standby": TIMERTYPE.STANDBY,
            "deepstandby": TIMERTYPE.DEEPSTANDBY,
            "reboot": TIMERTYPE.REBOOT,
            "restart": TIMERTYPE.RESTART
            }[timertype]

    if timertype == TIMERTYPE.WAKEUP:
        if int(xml.get("disabled")) == 1:
            return -1

        begin = int(xml.get("begin"))
        print('[findPowerTimerFrist].createTimer begin : ', begin)

        if begin > int(time()):
            curtime = int(time())
            print('[findPowerTimerFrist] int(time()) : ', curtime)
            return begin

    return -1

def findRecordTimerFrist(xml):
    begin = int(xml.get("begin"))
    if int(xml.get("disabled")) == 1:
        return -1

    print('[findRecordTimerFrist].createTimer begin : ', begin)

    if begin > int(time()):
        curtime = int(time())
        print('[findRecordTimerFrist] int(time()) : ', curtime)
        return begin

    return -1

def createTimer(xml):
    timertype = str(xml.get("timertype") or "wakeup")
    timertype = {
            "nothing": TIMERTYPE.NONE,
            "wakeup": TIMERTYPE.WAKEUP,
            "wakeuptostandby": TIMERTYPE.WAKEUPTOSTANDBY,
            "autostandby": TIMERTYPE.AUTOSTANDBY,
            "autodeepstandby": TIMERTYPE.AUTODEEPSTANDBY,
            "standby": TIMERTYPE.STANDBY,
            "deepstandby": TIMERTYPE.DEEPSTANDBY,
            "reboot": TIMERTYPE.REBOOT,
            "restart": TIMERTYPE.RESTART
            }[timertype]
    begin = int(xml.get("begin"))
    print('[timerTask].createTimer begin : ', begin)
    end = int(xml.get("end"))
    print('[timerTask].createTimer end : ', end)

    if begin > int(time()):
        curtime = int(time())
        print('int(time()) : ', curtime)
        t=Timer(begin - curtime, delayrun)
        t.start()
        return 1

    return 0
    '''
    repeated = xml.get("repeated").encode("utf-8")
    print '[timerTask].createTimer repeated : ',repeated
    disabled = long(xml.get("disabled") or "0")
    print '[timerTask].createTimer disabled : ',disabled
    afterevent = str(xml.get("afterevent") or "nothing")
    print '[timerTask].createTimer afterevent : ',afterevent
    afterevent = {
            "nothing": AFTEREVENT.NONE,
            "wakeup": AFTEREVENT.WAKEUP,
            "wakeuptostandby": AFTEREVENT.WAKEUPTOSTANDBY,
            "standby": AFTEREVENT.STANDBY,
            "deepstandby": AFTEREVENT.DEEPSTANDBY
            }[afterevent]
    autosleepinstandbyonly = str(xml.get("autosleepinstandbyonly") or "no")
    print '[timerTask].createTimer autosleepinstandbyonly : ',autosleepinstandbyonly
    autosleepdelay = str(xml.get("autosleepdelay") or "0")
    print '[timerTask].createTimer autosleepdelay : ',autosleepdelay
    autosleeprepeat = str(xml.get("autosleeprepeat") or "once")
    print '[timerTask].createTimer autosleeprepeat : ',autosleeprepeat
    autosleepwindow = str(xml.get("autosleepwindow") or "no")
    print '[timerTask].createTimer autosleepwindow : ',autosleepwindow
    autosleepbegin = int(xml.get("autosleepbegin") or begin)
    print '[timerTask].createTimer autosleepbegin : ',autosleepbegin
    autosleepend = int(xml.get("autosleepend") or end)
    print '[timerTask].createTimer autosleepend : ',autosleepend

    nettraffic = str(xml.get("nettraffic") or "no")
    trafficlimit = int(xml.get("trafficlimit") or 100)
    netip = str(xml.get("netip") or "no")
    ipadress = str(xml.get("ipadress") or "0.0.0.0")

    for l in xml.findall("log"):
            ltime = int(l.get("time"))
            code = int(l.get("code"))
            msg = l.text.strip().encode("utf-8")
            print '[timerTask].createTimer ltime : ',ltime,' code : ',code,' msg : %s',msg
    '''

#==============powertime==================

xmlfile = open('/etc/enigma2/pm_timers.xml', 'r')
doc = xml.etree.cElementTree.parse(xmlfile)
xmlfile.close()

root = doc.getroot()

powerfristtime = -1
for timer in root.findall("timer"):
    powerfristtime = findPowerTimerFrist(timer)
    if powerfristtime != -1:
        break

#==============recordtime==================

xmlfile = open('/etc/enigma2/timers.xml', 'r')
doc = xml.etree.cElementTree.parse(xmlfile)
xmlfile.close()

root = doc.getroot()
recordfristtime = -1
for timer in root.findall("timer"):
    recordfristtime = findPowerTimerFrist(timer)
    if recordfristtime != -1:
        break

#========================run timer==========================
runcurtime = int(time())
if recordfristtime == -1 and powerfristtime == -1:
    delayrun(0)
elif recordfristtime == -1:
    delayrun(powerfristtime - runcurtime)
elif powerfristtime == -1:
    delayrun(recordfristtime - 5 * 60 - runcurtime)
elif recordfristtime < powerfristtime:
    delayrun(recordfristtime - 5 * 60 - runcurtime)
else:
    delayrun(powerfristtime - runcurtime)
