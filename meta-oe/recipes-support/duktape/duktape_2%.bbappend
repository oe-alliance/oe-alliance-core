FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:append = " file://iptvplayer.patch"

RCONFLICTS:${PN} = "libduktape207"
RREPLACES:${PN} = "libduktape207"

do_compile:append() {
    oe_runmake -f Makefile.cmdline
}

do_install:append() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/duk ${D}${bindir}/
}

FILES:${PN}:append = "${bindir}"
