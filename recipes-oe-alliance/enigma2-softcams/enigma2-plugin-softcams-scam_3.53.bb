CAMNAME = "scam"
DESCRIPTION = "${CAMNAME} ${PV} softcam"
RDEPENDS_${PN} = "libcrypto-compat"

PR = "r2"

SRC_URI = "http://downloads.pli-images.org/softcams/scam_v${PV}.zip"

S = "${WORKDIR}/scam_v${PV}/"

INHIBIT_PACKAGE_STRIP = "1"

require softcam.inc

do_compile_prepend() {
	mv ${WORKDIR}/scam\ v${PV}/* ${S}
}

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${S}/${CAMNAME}.${TARGET_ARCH} ${D}/usr/bin/${CAMNAME}
}

SRC_URI[md5sum] = "89086f28a5b048512b76c8c24af4f77a"
SRC_URI[sha256sum] = "6e24a46a9c5a34037c036901271cc2a6ce7d29c49f4e120ad6dbd96b8097bff8"
