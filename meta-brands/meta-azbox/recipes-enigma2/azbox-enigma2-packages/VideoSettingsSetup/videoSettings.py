from Components.config import config, ConfigSubsection, ConfigSlider, ConfigSelection, ConfigNothing, NoSave
from Tools.CList import CList
from os import path as os_path
import os

class videoSettings:
    firstRun = True

    def __init__(self):
        self.last_modes_preferred = []
        self.createConfig()

    def createConfig(self, *args):
        config.vset = ConfigSubsection()
        config.vset.configsteps = NoSave(ConfigSelection(choices=[1,
         5,
         10,
         25], default=1))

        def setContrast(config):
            myval = int(config.value)
            self.myval1 = '1 ' + str(myval)
            try:
                os.system('echo "' + str(self.myval1) + '" > /proc/videosettings')
            except IOError:
                print "couldn't apply vset values."

        config.vset.contrast = ConfigSlider(default=128, limits=(0, 255))
        config.vset.contrast.addNotifier(setContrast)

        def setBrightness(config):
            myval = int(config.value)
            self.myval2 = '2 ' + str(myval - 128)
            try:
                os.system('echo "' + str(self.myval2) + '" > /proc/videosettings')
            except IOError:
                print "couldn't apply vset values."

        config.vset.brightness = ConfigSlider(default=128, limits=(0, 255))
        config.vset.brightness.addNotifier(setBrightness)

        def setSaturation(config):
            myval = int(config.value)
            self.myval3 = '3 ' + str(myval)
            try:
                os.system('echo "' + str(self.myval3) + '" > /proc/videosettings')
            except IOError:
                print "couldn't apply vset values."

        config.vset.saturation = ConfigSlider(default=128, limits=(0, 255))
        config.vset.saturation.addNotifier(setSaturation)

        def setHue(config):
            myval = int(config.value)
            self.myval4 = '4 ' + str(myval - 64)
            try:
                os.system('echo "' + str(self.myval4) + '" > /proc/videosettings')
            except IOError:
                print "couldn't apply vset values."

        config.vset.hue = ConfigSlider(default=64, limits=(0, 128))
        config.vset.hue.addNotifier(setHue)

        def setSplitMode(config):
            print '--> setting splitmode to:', str(config.value)

        config.vset.split = NoSave(ConfigNothing())


videoSettings()
