SUMMARY = "STREAM memory bandwidth benchmark"
HOMEPAGE = "https://github.com/jeffhammond/STREAM"
LICENSE = "LicenseRef-PD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/PD;md5=b3597d12946881e13cb3b548d1173851"

SRC_URI[sha256sum] = "0ed3046e1bb7da798458fd6bda6c94ccd851b80ed6f1dc55635dab9f1afe3bc3"

SRC_URI = "https://source.mynonpublic.com/stream-${PV}.zip"

S = "${UNPACKDIR}/stream-${PV}"

do_compile() {
	${CC} ${CFLAGS} ${LDFLAGS} stream.c -o streambench
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 streambench ${D}${bindir}
}
