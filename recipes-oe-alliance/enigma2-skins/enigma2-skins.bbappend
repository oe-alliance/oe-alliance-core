FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "1"

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r3"

SRC_URI = "${ENIGMA2_SKINS_URI}"

