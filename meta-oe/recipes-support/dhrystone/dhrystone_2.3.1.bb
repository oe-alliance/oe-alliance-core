SUMMARY = "Dhrystone CPU benchmark"
HOMEPAGE = "https://en.wikipedia.org/wiki/Dhrystone"
LICENSE = "LicenseRef-PD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/PD;md5=b3597d12946881e13cb3b548d1173851"

SRC_URI[sha256sum] = "427bd0b580d06bc06cf1713e560384cf6b45a7e9b3d9dcd783cda04ac48eb0fd"

SRC_URI = "https://source.mynonpublic.com/dhry-${PV}.zip"

S = "${UNPACKDIR}/dhry-${PV}"

do_compile() {
	${CC} ${CFLAGS} ${LDFLAGS} -DTIME dhry_1.c dhry_2.c -o dhry
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/dhry ${D}${bindir}
}

# Prevent procedure merging as required by dhrystone.c.
CFLAGS += "-fno-lto"
LDFLAGS += "-fno-lto"
