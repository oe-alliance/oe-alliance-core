CAMNAME = "rqcamd"
DESCRIPTION = "${CAMNAME} ${PV} softcam"

PR = "r0"

SRC_URI = "http://downloads.pli-images.org/softcams/${CAMNAME}-${PV}-binaries.tar.gz"

S = "${WORKDIR}/"

INHIBIT_PACKAGE_STRIP = "1"

require softcam.inc

CONFFILES = "/usr/bin/${CAMNAME}.conf"

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${S}/${CAMNAME}.mips ${D}/usr/bin/${CAMNAME}
	install -m 0644 ${S}/${CAMNAME}.conf ${D}/usr/bin/${CAMNAME}.conf
}

SRC_URI[md5sum] = "92443a7945d8d6d721b73ac3401dd136"
SRC_URI[sha256sum] = "0ae380c8e2a124843a82d106ec342c59836dbfd688a624012c18e3f26fb44ca5"
