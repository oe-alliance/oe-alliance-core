SUMMARY = "PLi-Scale-HD skin, scalable clean maximum screen utilisation"
DESCRIPTION = "Screen based on resizable templates."
MAINTAINER = "Stephen R. van den Berg AKA BuGless <srb@cuci.nl>"
LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"
PACKAGE_ARCH = "all"

BRANCH = "master"

SRC_URI = "git://devel.cuci.nl/enigma2-skin-pli-scale-hd;protocol=git;branch=${BRANCH}"

FILES_${PN} = "/"
RDEPENDS_${PN} = "enigma2-fonts"

S = "${WORKDIR}/git"

do_compile() {
}

do_install() {
	${S}/install.sh "${S}" "${D}"
}
