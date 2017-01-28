SUMMARY = "Atemio4you skin for EGAMI"
MAINTAINER = "mmark and egami team"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv

EPSM = "enigma2-plugin-skins.egami"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r3"

PACKAGES = "${EPSM}-a4you"
PROVIDES = "${EPSM}-a4you"

SRC_URI="git://github.com/a4tech/Egami-skins.git;protocol=git"

FILES_${EPSM}-a4you = "/usr/share/enigma2/a4you /usr/lib/*"

S = "${WORKDIR}/git"

RDEPENDS_${PN} = "font-roboto-enigma"

S = "${WORKDIR}/git"

do_compile_append() {
    python -O -m compileall ${S}
}

do_install() {
    cp -rp ${S}/Atemio4You/usr ${D}/
}
