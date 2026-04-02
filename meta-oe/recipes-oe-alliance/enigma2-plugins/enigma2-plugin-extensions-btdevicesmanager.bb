DESCRIPTION = "this is bt devices manger to pair e.x keyboard or mouse"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "btinit bluez5-testtools bluez5 bluez-hcidump bluez-conf bluez-hidd bluez-alsa alsa-utils-aplay python3-pexpect"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/BTDevicesManager.git;protocol=https;branch=main"


do_install:append() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/BTDevicesManager
    install -m 0755 ${S}/BTDevicesManager/BTAudioConnect ${D}/usr/lib/enigma2/python/Plugins/Extensions/BTDevicesManager/BTAudioConnect
}

FILES:${PN} += "/usr/lib/enigma2/python/Plugins/Extensions/BTDevicesManager/BTAudioConnect"
