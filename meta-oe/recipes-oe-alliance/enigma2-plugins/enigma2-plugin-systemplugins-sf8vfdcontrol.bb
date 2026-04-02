DESCRIPTION = "vfd controller"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PN = "enigma2-plugin-systemplugins-vfdcontrol"

COMPATIBLE_MACHINE = "xp1000"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/SF8VFDControl.git;protocol=https;branch=main"


do_install:append() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/VFDControl
    install -m 0755 ${S}/SF8VFDControl/led7ctrl ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/VFDControl/led7ctrl
}

FILES:${PN} += "/usr/lib/enigma2/python/Plugins/SystemPlugins/VFDControl/led7ctrl"
