SUMMARY = "DTS Coherent Acoustics decoder with support for HD extensions"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING.LGPLv2.1;md5=4fbd65380cdd255951079008b364516c"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.2+git${SRCPV}"
PKGV = "0.2+git${GITPKGV}"

SRC_URI = "git://github.com/foo86/dcadec.git;protocol=https"

S = "${WORKDIR}/git"

inherit lib_package

EXTRA_OEMAKE = "CONFIG_SHARED=1"

do_install() {
    oe_runmake install DESTDIR="${D}" PREFIX="${prefix}" LIBDIR="${libdir}"
}
