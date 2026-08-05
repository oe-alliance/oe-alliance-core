DESCRIPTION = "Vuplus bluetooth plugin for ${MACHINE}"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "btinit bluez5-testtools bluez5 bluez-hcidump bluez-hidd bluealsa alsa-utils-aplay python3-pexpect"

COMPATIBLE_MACHINE = "vuduo4klite"
inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/VUBTDevicesManager.git;protocol=https;branch=main"


do_install:append() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/BTDevicesManager
    install -m 0755 ${S}/VUBTDevicesManager/BTAudioConnect ${D}/usr/lib/enigma2/python/Plugins/Extensions/BTDevicesManager/BTAudioConnect
}

FILES:${PN} += "/usr/lib/enigma2/python/Plugins/Extensions/BTDevicesManager/BTAudioConnect"
