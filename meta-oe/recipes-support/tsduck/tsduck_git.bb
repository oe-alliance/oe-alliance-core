SUMMARY = "MPEG Transport Stream Toolkit"
DESCRIPTION = "TSDuck is an extensible toolkit for MPEG/DVB transport streams.\
	TSDuck is used in digital television systems for test, monitoring, integration, debug, lab, demo."
MAINTAINER = "https://tsduck.io/"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;beginline=1;endline=1;md5=c3af740e8628bb461ed34cc1b4bba078"

SRC_URI = "git://github.com/tsduck/tsduck.git;protocol=https;branch=master"

SRCREV = "b18489209c7871a35a49af98be431699b85df9c0"
PV = "git"
PKGV = "${GITPKGVTAG}"
PR = "r0"

DEPENDS = "gettext curl libedit"

inherit gittag autotools-brokensep pkgconfig upx-compress

TARGET_CC_ARCH += "${LDFLAGS}"
EXTRA_OEMAKE = "CXXFLAGS_EXTRA=-Wno-maybe-uninitialized \
				MAIN_ARCH=${TUNE_PKGARCH} SYSROOT=${D} STRIP=/bin/true \
				NOTEST=1 NOPCSC=1 NODTAPI=1 NOSRT=1 NODOC=1 NOVATEK=1 NOPCSTD=1"

do_configure[noexec] = "1"

do_install:append() {
	rm -rf ${D}${datadir}/bash-completion
}

FILES:${PN} += "${libdir}/libtscore.so ${libdir}/libtsduck.so"
FILES:${PN}-dev = "${includedir}/ ${datadir}/pkgconfig/"
