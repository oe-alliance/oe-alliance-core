KV = "4.4.35"
SRCDATE = "20190717"

do_install_append() {
	install -d ${D}/lib/firmware/brcm
	install -m 0644 ${WORKDIR}/brcmfmac43455-sdio.bin ${D}/lib/firmware/brcm
	install -m 0644 ${WORKDIR}/brcmfmac43455-sdio.txt ${D}/lib/firmware/brcm
}

FILES_${PN} += "/lib/firmware/brcm"

require dinobot-dvb-modules2.inc

SRC_URI += "file://u53x-platform.zip"

SRC_URI[md5sum] = "d265d9110951e274a05c5a9f29fcdfe6"
SRC_URI[sha256sum] = "c350139fd83f039359b403c4374e7303aa85d33bda23c07c098e16efce880e09"
