SUMMARY = "MPEG Transport Stream Toolkit"
DESCRIPTION = "TSDuck is an extensible toolkit for MPEG/DVB transport streams.\
	TSDuck is used in digital television systems for test, monitoring, integration, debug, lab, demo."

require tsduck.inc

DEPENDS = "gettext curl libedit tsxml-native"

inherit gittag autotools-brokensep pkgconfig upx-compress

TARGET_CC_ARCH += "${LDFLAGS}"
EXTRA_OEMAKE = "CXXFLAGS_EXTRA=-Wno-maybe-uninitialized \
				MAIN_ARCH=${TUNE_PKGARCH} SYSROOT=${D} \
				NOTEST=1 NOPCSC=1 NODTAPI=1 NOSRT=1 NODOC=1 NOVATEK=1 NOPCSTD=1"

do_configure[noexec] = "1"

do_compile:prepend() {
	# point TSXML variable to native tsxml binary
	sed -i -e \
		"s|TSXML='LD_LIBRARY_PATH.*|TSXML='LD_LIBRARY_PATH=${RECIPE_SYSROOT_NATIVE}/usr/lib ${RECIPE_SYSROOT_NATIVE}/usr/bin/tsxml'|" \
		${S}/scripts/make-config.sh
}

do_install:append() {
	rm -rf ${D}${datadir}/bash-completion
}

FILES:${PN} += "${libdir}/libtscore.so ${libdir}/libtsduck.so"
FILES:${PN}-dev = "${includedir}/ ${datadir}/pkgconfig/"
