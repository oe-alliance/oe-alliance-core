SUMMARY = "Skins components for EGAMI"
MAINTAINER = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skinscomponents"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r2"

PACKAGES = "${EPSM}-egskincomponents"
PROVIDES = "${EPSM}-egskincomponents"

SRC_URI="git://github.com/a4tech/Egami-skins.git;protocol=git"

FILES_${EPSM}-egskincomponents = "/usr/lib/*"

S = "${WORKDIR}/git"

do_compile_append() {
    python -O -m compileall ${S}
}

do_install() {
    cp -rp ${S}/egami-common/usr ${D}/
}
