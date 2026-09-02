SUMMARY = "Optional RTL-SDR runtime for native Enigma2 DAB+ reception"
DESCRIPTION = "Feed-installable runtime package for Enigma2 DAB+ USB reception. It contains no Enigma2 code and pulls in librtlsdr and the welle.io-based compressed-AAC DAB backend."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "1.1"
PR = "r0"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = " \
	gstreamer1.0-libav \
    rtl-sdr \
    welle-cli \
"
