SUMMARY = "dumpait"
PRIORITY = "required"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE.GPLv3;md5=5ed852a46d22220a8b07a68e564d84c7"

inherit autotools-brokensep pkgconfig gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r7"

PACKAGES += " ${PN}-src"

SRC_URI = "git://code.vuplus.com/git/dumpait.git;protocol=git"

S = "${WORKDIR}/git"
DESTDIR = "enigma2/python/Plugins/Extensions/HbbTV"

do_install() {
    install -d ${D}/usr/lib/${DESTDIR}
    install -m 0755 ${S}/src/dumpait ${D}/usr/lib/${DESTDIR}
}

FILES_${PN} = "${libdir}/${DESTDIR}/dumpait"
FILES_${PN}-dbg = "${libdir}/${DESTDIR}/.debug"
FILES_${PN}-src = "/usr/src"
