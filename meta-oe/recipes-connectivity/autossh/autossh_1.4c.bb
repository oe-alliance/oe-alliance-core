SUMMARY = "autossh"
LICENSE = "GPLv2+"
PR = "r1"

LIC_FILES_CHKSUM = "file://autossh.c;beginline=1;endline=24;md5=755a81ffe573faf6c18d1f1061d097c4"

SRC_URI = "http://www.harding.motd.ca/autossh/${BPN}-${PV}.tgz"

inherit autotools-brokensep

S = "${WORKDIR}/${BPN}-${PV}"

do_compile() {
    oe_runmake
}

do_install() {
        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/${PN}-${PV}/autossh ${D}${bindir}/autossh
}

FILES_${PN} = "${bindir}/autossh"

SRC_URI[md5sum] = "26520eea934f296be0783dabe7fcfd28"
SRC_URI[sha256sum] = "6fcaba6a409a46bdf832086736bb8f09d245ebce11027f41d39588a95dc7fd1d"

INSANE_SKIP_${PN} += "ldflags"
