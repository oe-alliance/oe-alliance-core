DEPENDS = "ambx"
DESCIPTION = "Python interface for amBX."
MAINTAINER = "PLi team"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://../COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://openpli.git.sourceforge.net/gitroot/openpli/plugin-ambx;protocol=git"

S = "${WORKDIR}/git/python"

inherit distutils

