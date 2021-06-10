SUMMARY = "Qt Touch-Friendly WebBrowser"
HOMEPAGE = "http://blog.qt.io/blog/2016/07/18/qt-webbrowser-1-0/"
SECTION = "multimedia"
PACKAGE_ARCH := "${MACHINE_ARCH}"
LICENSE = "GPL-3.0 & GFDL-1.3"
LIC_FILES_CHKSUM = " \
file://LICENSE.GPLv3;md5=a40e2bb02b1ac431f461afd03ff9d1d6 \
file://LICENSE.FDL;md5=6d9f2a9af4c8b8c3c769f6cc1b6aaf7e \
"

DEPENDS = " qtwebengine upower qtwidevine qtflashplayer"
RDEPENDS_${PN} = "qtquickcontrols qtquickcontrols2 qtgraphicaleffects qtvirtualkeyboard upower qtwidevine qtflashplayer"
PACKAGES += "${PN}-examples"

PR = "r1"

BRANCH = "dev"
SRCREV = "93637d1b4f32a1f73613f70b29544f232059d600"

SRC_URI = "git://code.qt.io/qt-apps/qtwebbrowser.git;protocol=git;branch=${BRANCH} \
"

S = "${WORKDIR}/git"

inherit qmake5

# Install qtwebbrowser under /usr/bin
do_install() {
install -d ${D}${bindir}
install -d ${D}${datadir}/examples/webengine/webbrowser/src
install -m 0755 src/qtwebbrowser ${D}${bindir}/qtwebbrowser
cp -R --no-dereference --preserve=mode,links ${S}/* ${D}${datadir}/examples/webengine/webbrowser/.
}


FILES_${PN} += "${bindir}/qtwebbrowser"
FILES_${PN}-examples += "${datadir}/examples/webengine/webbrowser/*"
