SUMMARY = "QtWebflix"
DESCRIPTION = "A viewer for netflix, amazon prime and similar"
PACKAGE_ARCH := "${MACHINE_ARCH}"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=84dcc94da3adb52b53ae4fa38fe49e5d"

DEPENDS = "qtwebengine upower qtwidevine"
RDEPENDS:${PN} = "upower qtwidevine"

SRCREV_qtwebflix = "${AUTOREV}"
SRCREV_qtdbusextended = "34971431233dc408553245001148d34a09836df1"
SRCREV_qtmpris = "7251898353f1f5804c9480172ad7df88c4fe7eb6"
SRCREV_FORMAT = "qtwebflix_qtdbusextended_qtmpris"

SRC_URI = "git://github.com/gort818/qtwebflix.git;protocol=https;name=qtwebflix;branch=master \
           git://github.com/nemomobile/qtdbusextended.git;destsuffix=git/lib/qtdbusextended;branch=master;name=qtdbusextended;protocol=https \
           git://github.com/sailfishos/qtmpris.git;destsuffix=git/lib/qtmpris;branch=master;name=qtmpris;protocol=https \
           file://0001-change-useragent.patch \
           "

S = "${WORKDIR}/git"

inherit qmake5 pkgconfig

do_install() {
	install -d ${D}/usr/bin
	install -m755 ${B}/qtwebflix ${D}/usr/bin

}

FILES:${PN} = "/usr/bin/qtwebflix"

PATH:prepend = "${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}:"
