SUMMARY = "MPEG Transport Stream Toolkit"
DESCRIPTION = "TSDuck is an extensible toolkit for MPEG/DVB transport streams.\
	TSDuck is used in digital television systems for test, monitoring, integration, debug, lab, demo."
MAINTAINER = "https://tsduck.io/"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;beginline=1;endline=1;md5=c3af740e8628bb461ed34cc1b4bba078"

SRC_URI = "git://github.com/tsduck/tsduck.git;protocol=https;branch=master"
SRCREV = "0fbbc7e06460abb1fe1358233b66ae15b4fce122" 

DEPENDS = "gettext curl libedit"

S = "${WORKDIR}/git"

inherit gitpkgv autotools-brokensep pkgconfig gettext

PV = "3.21+gitr${SRCPV}"
PKGV = "3.21+git${GITPKGV}"
PR = "r0"

TARGET_CC_ARCH += "${LDFLAGS}"
EXTRA_OEMAKE = "NOTEST=1 NOPCSC=1 NODTAPI=1 NOSRT=1 MAIN_ARCH=${TUNE_PKGARCH} SYSROOT=${D}"