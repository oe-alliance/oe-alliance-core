SUMMARY = "llmnrd - Link-Local Multicast Resolution (LLMNR) Daemon for Linux"
HOMEPAGE = "https://github.com/tklauser/llmnrd"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
# PR = "r2"

# CFLAGS_append = " -I${S}  -I${S}/lib "

# SRC_URI = "git://github.com/tklauser/llmnrd.git"
SRC_URI = "git://github.com/Schimmelreiter/llmnrd.git \
    file://llmnrd.sh \
"

S = "${WORKDIR}/git"

inherit pkgconfig update-rc.d

INITSCRIPT_NAME = "llmnrd"

EXTRA_OEMAKE = " \
    'CC=${CC}' \
"

do_compile() {
    oe_runmake CROSS_COMPILE="${TARGET_PREFIX}"
}

do_install() {
    install -d ${D}/usr/bin
    install -m 755 ${S}/llmnrd ${D}/usr/bin
    install -m 755 ${S}/llmnr-query ${D}/usr/bin
    install -d ${D}/etc/init.d
    install -m 755 ${WORKDIR}/llmnrd.sh ${D}/etc/init.d/llmnrd
}
