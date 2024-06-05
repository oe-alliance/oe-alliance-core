SUMMARY = "Touchscreen calibration data"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/COPYING;md5=4b5fcfc87fb615860d398b5e38685edf"
BBCLASSEXTEND = "native"

SRC_URI = "file://pointercal \
           file://COPYING"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
	# Only install file if it has a contents
	if [ -s ${UNPACKDIR}/pointercal ]; then
	        install -d ${D}${sysconfdir}/
	        install -m 0644 ${UNPACKDIR}/pointercal ${D}${sysconfdir}/
	fi
}

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
INHIBIT_DEFAULT_DEPS = "1"
