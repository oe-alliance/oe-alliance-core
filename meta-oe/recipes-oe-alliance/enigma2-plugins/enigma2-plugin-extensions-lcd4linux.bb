DESCRIPTION = "Web/DPF/Samsung LCD Ansteuerung"
require conf/license/license-gplv2.inc

RDEPENDS:${PN} = "dpflib python3-icalendar python3-pyusb python3-codecs python3-datetime python3-pillow python3-image python3-shell python3-ctypes libusb-0.1-4 python3-mutagen python3-email python3-simplejson python3-soco"
DEPENDS = "png-util"
RDEPENDS:${PN}:append:vuduo2 = " png-util"
FILES:${PN}:append = " /usr/lib/enigma2/python/Components/Renderer/*.pyc"
FILES:${PN}-src:append = " /usr/lib/enigma2/python/Components/Renderer/*.py"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"
PR = "r1"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/LCD4linux.git;protocol=https;branch=main"

do_install:append () {
    install -d ${D}/usr/lib/enigma2/python/Components/Renderer
    install -m 0755 ${S}/LCD4linux/renderer/PixmapLcd4linux.py ${D}/usr/lib/enigma2/python/Components/Renderer/PixmapLcd4linux.py
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/LCD4linux
    install -m 0755 ${S}/LCD4linux/FritzCallUserAction.sh ${D}/usr/lib/enigma2/python/Plugins/Extensions/LCD4linux/FritzCallUserAction.sh
    # WebConfigSite reads these lines to build the web config page. Shipping just
    # them keeps the plugin sources off the box.
    grep -E "self\.list[1-4]\.append" ${S}/LCD4linux/plugin.py > ${D}/usr/lib/enigma2/python/Plugins/Extensions/LCD4linux/configlist.txt
}

require conf/python/python3-compileall.inc