PRINC = "1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
	
SRC_URI = "file://fw-ds3xxx.tar.gz"

S = "${WORKDIR}"

do_install() {
	install -d ${D}/lib/firmware
	install -m 0755 dvb-fe-ds300x.fw ${D}/lib/firmware/dvb-fe-ds300x.fw
	install -m 0755 dvb-fe-ds3103.fw ${D}/lib/firmware/dvb-fe-ds3103.fw
}

FILES_${PN} = "/lib/firmware"


