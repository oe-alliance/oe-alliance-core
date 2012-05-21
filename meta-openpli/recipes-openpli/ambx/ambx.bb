DEPENDS = "virtual/libusb0"
DESCIPTION = "Provides video grabber and amBX USB control."
MAINTAINER = "PLi team"
PACKAGE_ARCH = "${MACHINE}"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

PACKAGES =+ "${PN}-test"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://openpli.git.sourceforge.net/gitroot/openpli/plugin-ambx;protocol=git"

S = "${WORKDIR}/git"

# Dunno why, but it won't build in parallel
PARALLEL_MAKE = ""

EXTRA_OECONF = "--with-chipset=${CHIPSET}"
inherit autotools

FILES_${PN}-test = "/usr/bin/unittest /usr/bin/grabvid /usr/bin/testambx"
FILES_${PN}-dbg += "/usr/lib/python2.5/site-packages/.debug"

# link against local so's instead of the staged ones
EXTRA_OEMAKE = 'LDFLAGS="-L${S} -L${STAGING_DIR}/${TARGET_SYS}/lib"'
