FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "1"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://README;startline=1;endline=6;md5=57713aee3e85f7f3c17db37179dbe275"

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r3"

SRC_URI = "${ENIGMA2_SKINS_URI}"

