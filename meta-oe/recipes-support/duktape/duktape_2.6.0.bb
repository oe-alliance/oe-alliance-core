DESCRIPTION = "Duktape is an embeddable Javascript engine, with a focus on portability and compact footprint."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=c83446610de1f63c7ca60cfcc82dec9d"

PR = "r1"

SRC_URI = " \
	http://duktape.org/duktape-${PV}.tar.xz \
	file://iptvplayer.patch \
"

SRC_URI[md5sum] = "01ee8ecf3dd5c6504543c8679661bb20"
SRC_URI[sha256sum] = "96f4a05a6c84590e53b18c59bb776aaba80a205afbbd92b82be609ba7fe75fa7"

do_compile() {
    oe_runmake -f Makefile.cmdline
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/duk ${D}${bindir}/
}

FILES_${PN} = "${bindir}"

INSANE_SKIP_${PN} += "ldflags"
