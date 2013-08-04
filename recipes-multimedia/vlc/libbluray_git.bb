SUMMARY = "Library to access Blu-Ray disks"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=435ed639f84d4585d93824e7da3d85da"
SRCREV = "3b9a9f044644a6abe9cb09377f714ded9fdd6c87"
PV = "0.2.1+git${SRCPV}"
PR = "r1"

SRC_URI = "git://git.videolan.org/${PN}.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools lib_package pkgconfig
