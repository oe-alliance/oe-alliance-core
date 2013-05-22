from Plugins.Plugin import PluginDescriptor
from Components.ConfigList import ConfigListScreen
from Components.config import getConfigListEntry, config, ConfigNothing, ConfigSelection
from Components.ActionMap import ActionMap
from Components.Sources.StaticText import StaticText
from Screens.Screen import Screen
from Screens.MessageBox import MessageBox
import videoSettings
from os import path as os_path

class videoSettingsSetup(Screen, ConfigListScreen):
    skin = '\n\t\t<screen name="videoSettingsSetup" position="center,center" size="560,200" title="videoSettingsSetup">\n\t\t\t<ePixmap pixmap="skin_default/buttons/red.png" position="0,0" size="140,40" alphatest="on" />\n\t\t\t<ePixmap pixmap="skin_default/buttons/green.png" position="140,0" size="140,40" alphatest="on" />\n\t\t\t<ePixmap pixmap="skin_default/buttons/blue.png" position="420,0" size="140,40" alphatest="on" />\n\t\t\t<widget source="key_red" render="Label" position="0,0" zPosition="1" size="140,40" font="Regular;20" halign="center" valign="center" backgroundColor="#9f1313" transparent="1" />\n\t\t\t<widget source="key_green" render="Label" position="140,0" zPosition="1" size="140,40" font="Regular;20" halign="center" valign="center" backgroundColor="#1f771f" transparent="1" />\n\t\t\t<widget source="key_blue" render="Label" position="420,0" zPosition="1" size="140,40" font="Regular;20" halign="center" valign="center" backgroundColor="#18188b" transparent="1" />\n\t\t\t<widget name="config" position="5,50" size="550,350" scrollbarMode="showOnDemand" />\n\t\t\t<ePixmap pixmap="skin_default/div-h.png" position="0,160" zPosition="1" size="560,2" />\n\t\t\t<widget source="introduction" render="Label" position="5,170" size="550,30" zPosition="10" font="Regular;21" halign="center" valign="center" backgroundColor="#25062748" transparent="1" />\n\t\t</screen>'

    def __init__(self, session):
        Screen.__init__(self, session)
        self.session = session
        self.onChangedEntry = []
        self.setup_title = 'videoSettings'
        self.list = []
        self.xtdlist = []
        ConfigListScreen.__init__(self, self.list, session=self.session, on_change=self.changedEntry)
        self['actions'] = ActionMap(['SetupActions', 'ColorActions'], {'ok': self.apply,
         'cancel': self.keyCancel,
         'save': self.apply,
         'blue': self.keyBlue}, -2)
        self['key_red'] = StaticText(_('Cancel'))
        self['key_green'] = StaticText(_('OK'))
        self['key_blue'] = StaticText(_('Default'))
        self['introduction'] = StaticText()
        self.createSetup()
        self.onLayoutFinish.append(self.layoutFinished)

    def layoutFinished(self):
        self.setTitle(_('Video Setup'))

    def addToConfigList(self, description, configEntry, add_to_xtdlist = False):
        if isinstance(configEntry, ConfigNothing):
            return None
        entry = getConfigListEntry(description, configEntry)
        self.list.append(entry)
        if add_to_xtdlist:
            self.xtdlist.append(entry)
        return entry

    def createSetup(self):
        self.list = []
        self.xtdlist = []
        addToConfigList = self.addToConfigList
        self.contrastEntry = addToConfigList(_('Contrast'), config.vset.contrast)
        self.saturationEntry = addToConfigList(_('Saturation'), config.vset.saturation)
        self.hueEntry = addToConfigList(_('Hue'), config.vset.hue)
        self.brightnessEntry = addToConfigList(_('Brightness'), config.vset.brightness)
        self.splitEntry = addToConfigList(_('Split preview mode'), config.vset.split, True)
        add_to_xtdlist = self.splitEntry is not None
        self['config'].list = self.list
        self['config'].l.setSeperation(300)
        self['config'].l.setList(self.list)
        if self.selectionChanged not in self['config'].onSelectionChanged:
            self['config'].onSelectionChanged.append(self.selectionChanged)
        self.selectionChanged()

    def selectionChanged(self):
        self['introduction'].setText(_('Current value: ') + self.getCurrentValue())

    def PreviewClosed(self):
        self['config'].invalidate(self['config'].getCurrent())
        self.createSetup()

    def keyLeft(self):
        current = self['config'].getCurrent()
        if current == self.splitEntry:
            ConfigListScreen.keyLeft(self)
        elif current != self.splitEntry and current in self.xtdlist:
            self.previewlist = [current, self.splitEntry]
            maxvalue = current[1].max
            self.session.openWithCallback(self.PreviewClosed, videoSettingsPreview, configEntry=self.previewlist, oldSplitMode=config.vset.split.value, maxValue=maxvalue)
        else:
            self.previewlist = [current]
            maxvalue = current[1].max
            self.session.openWithCallback(self.PreviewClosed, videoSettingsPreview, configEntry=self.previewlist, oldSplitMode=None, maxValue=maxvalue)

    def keyRight(self):
        current = self['config'].getCurrent()
        if current == self.splitEntry:
            ConfigListScreen.keyRight(self)
        elif current != self.splitEntry and current in self.xtdlist:
            self.previewlist = [current, self.splitEntry]
            maxvalue = current[1].max
            self.session.openWithCallback(self.PreviewClosed, videoSettingsPreview, configEntry=self.previewlist, oldSplitMode=config.vset.split.value, maxValue=maxvalue)
        else:
            self.previewlist = [current]
            maxvalue = current[1].max
            self.session.openWithCallback(self.PreviewClosed, videoSettingsPreview, configEntry=self.previewlist, oldSplitMode=None, maxValue=maxvalue)

    def confirm(self, confirmed):
        if not confirmed:
            print 'not confirmed'
        else:
            self.keySave()

    def apply(self):
        self.session.openWithCallback(self.confirm, MessageBox, _('Use this Video settings?'), MessageBox.TYPE_YESNO, timeout=20, default=False)

    def cancelConfirm(self, result):
        if not result:
            return
        self.close()

    def keyCancel(self):
        if self['config'].isChanged():
            self.session.openWithCallback(self.cancelConfirm, MessageBox, _('Really close without saving settings?'))
        else:
            self.close()

    def keyBlueConfirm(self, confirmed):
        if not confirmed:
            print 'not confirmed'
        else:
            if self.contrastEntry is not None:
                config.vset.contrast.setValue(128)
            if self.saturationEntry is not None:
                config.vset.saturation.setValue(128)
            if self.hueEntry is not None:
                config.vset.hue.setValue(65)
            if self.brightnessEntry is not None:
                config.vset.brightness.setValue(128)
            self.keySave()

    def keyBlue(self):
        self.session.openWithCallback(self.keyBlueConfirm, MessageBox, _('Reset video settings to system defaults?'), MessageBox.TYPE_YESNO, timeout=10, default=False)

    def changedEntry(self):
        for x in self.onChangedEntry:
            x()

        self.selectionChanged()

    def getCurrentEntry(self):
        return self['config'].getCurrent()[0]

    def getCurrentValue(self):
        return str(self['config'].getCurrent()[1].getText())

    def createSummary(self):
        from Screens.Setup import SetupSummary
        return SetupSummary


class videoSettingsPreview(Screen, ConfigListScreen):
    skin = '\n\t\t<screen name="videoSettingsPreview" position="center,360" size="560,170" title="videoSettingsPreview">\n\t\t\t<ePixmap pixmap="skin_default/buttons/red.png" position="0,0" size="140,40" alphatest="on" />\n\t\t\t<ePixmap pixmap="skin_default/buttons/green.png" position="140,0" size="140,40" alphatest="on" />\n\t\t\t<widget source="key_red" render="Label" position="0,0" zPosition="1" size="140,40" font="Regular;20" halign="center" valign="center" backgroundColor="#9f1313" transparent="1" />\n\t\t\t<widget source="key_green" render="Label" position="140,0" zPosition="1" size="140,40" font="Regular;20" halign="center" valign="center" backgroundColor="#1f771f" transparent="1" />\n\t\t\t<widget name="config" position="5,50" size="550,80" scrollbarMode="showOnDemand" />\n\t\t\t<ePixmap pixmap="skin_default/div-h.png" position="0,130" zPosition="1" size="560,2" />\n\t\t\t<widget source="introduction" render="Label" position="0,140" size="550,25" zPosition="10" font="Regular;21" halign="center" valign="center" backgroundColor="#25062748" transparent="1" />\n\t\t</screen>'

    def __init__(self, session, configEntry = None, oldSplitMode = None, maxValue = None):
        Screen.__init__(self, session)
        self.onChangedEntry = []
        self.setup_title = 'videoSettings'
        self.maxValue = maxValue
        self.configStepsEntry = None
        self.isStepSlider = None
        self.list = []
        self.configEntry = configEntry
        ConfigListScreen.__init__(self, self.list, session=session, on_change=self.changedEntry)
        self['actions'] = ActionMap(['SetupActions'], {'ok': self.keySave,
         'cancel': self.keyCancel,
         'save': self.keySave}, -2)
        self['key_red'] = StaticText(_('Cancel'))
        self['key_green'] = StaticText(_('OK'))
        self['introduction'] = StaticText()
        self.createSetup()
        self.onLayoutFinish.append(self.layoutFinished)

    def layoutFinished(self):
        self.setTitle(_('Video settings preview'))

    def createSetup(self):
        self.list = []
        if self.maxValue == 255:
            self.configStepsEntry = getConfigListEntry(_('Change step size'), config.vset.configsteps)
        if self.configEntry is not None:
            self.list = self.configEntry
        if self.maxValue == 255:
            self.list.append(self.configStepsEntry)
        self['config'].list = self.list
        self['config'].l.setSeperation(300)
        self['config'].l.setList(self.list)
        if self.selectionChanged not in self['config'].onSelectionChanged:
            self['config'].onSelectionChanged.append(self.selectionChanged)
        self.selectionChanged()

    def selectionChanged(self):
        self['introduction'].setText(_('Current value: ') + self.getCurrentValue())
        try:
            max_avail = self['config'].getCurrent()[1].max
            if max_avail == 255:
                self.isStepSlider = True
            else:
                self.isStepSlider = False
        except AttributeError:
            print 'no max value'

    def keyLeft(self):
        if self.isStepSlider is True:
            self['config'].getCurrent()[1].increment = config.vset.configsteps.value
        ConfigListScreen.keyLeft(self)

    def keyRight(self):
        if self.isStepSlider is True:
            self['config'].getCurrent()[1].increment = config.vset.configsteps.value
        ConfigListScreen.keyRight(self)

    def keySave(self):
        self.close()

    def keyCancel(self):
        for x in self['config'].list:
            x[1].cancel()

        self.close()

    def changedEntry(self):
        for x in self.onChangedEntry:
            x()

        self.selectionChanged()

    def getCurrentEntry(self):
        return self['config'].getCurrent()[0]

    def getCurrentValue(self):
        return str(self['config'].getCurrent()[1].getText())

    def createSummary(self):
        from Screens.Setup import SetupSummary
        return SetupSummary


def videoSettingsSetupMain(session, **kwargs):
    session.open(videoSettingsSetup)


def startSetup(menuid):
    if menuid != 'system':
        return []
    return [(_('Video settings'),
      videoSettingsSetupMain,
      'videoSettings_setup',
      41)]


def Plugins(**kwargs):
    list = []
    list.append(PluginDescriptor(name=_('videoSettings Setup'), description=_('Video Settings Setup'), where=PluginDescriptor.WHERE_MENU, fnc=startSetup))
    return list
