DESCRIPTION = "enigma2-plugin-picons-openxta-yweatherpicons"
MAINTAINER = "original by m43c0 and mmark and mod by mogli123"
SECTION = "base"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="1.0"
PR = "r0"


SRC_URI="git://github.com/XTAv2/YWeatherPicons.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/* "

do_install() {
    cp -rp ${S}/usr ${D}/
}

do_populate_sysroot[noexec] = "1"