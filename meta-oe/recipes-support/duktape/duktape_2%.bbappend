FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:append = " file://iptvplayer.patch"

do_compile() {
    oe_runmake -f Makefile.cmdline
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/duk ${D}${bindir}/
}

FILES:${PN} = "${bindir}"
INSANE_SKIP:${PN} += "ldflags"
