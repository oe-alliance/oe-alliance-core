SUMMARY = "dumpait"
PRIORITY = "required"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=84dcc94da3adb52b53ae4fa38fe49e5d"

inherit autotools-brokensep pkgconfig gitpkgv

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"
PR = "r0"

DEPENDS = "libdvbsi++"

SRC_URI = "git://github.com/oe-alliance/dumpait-legacy.git;protocol=https;branch=master"


S = "${WORKDIR}/git"
DESTDIR = "enigma2/python/Plugins/Extensions/HbbTV"

do_install() {
    install -d ${D}${libdir}/${DESTDIR}
    install -m 0755 ${S}/src/dumpait ${D}${libdir}/${DESTDIR}
}

FILES:${PN} = "${libdir}/${DESTDIR}/dumpait"
FILES:${PN}-dbg = "${libdir}/${DESTDIR}/.debug"
FILES:${PN}-src = "/usr/src"
