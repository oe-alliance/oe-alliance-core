KV = "4.4.35"
SRCDATE = "20191021"

do_install_append() {
	install -d ${D}/lib/firmware/brcm
	install -d ${D}/${sysconfdir}/modules-load.d
	install -m 0644 ${WORKDIR}/brcmfmac43455-sdio.bin ${D}/lib/firmware/brcm
	install -m 0644 ${WORKDIR}/brcmfmac43455-sdio.txt ${D}/lib/firmware/brcm
	install -m 0644 ${WORKDIR}/*.ko ${D}${nonarch_base_libdir}/modules/${KV}/extra
	install -m 0644 ${WORKDIR}/*.conf ${D}/${sysconfdir}/modules-load.d
}

FILES_${PN} += "/lib/firmware/brcm"

require dinobot-dvb-modules2.inc

SRC_URI += "file://u53x-platform.zip file://dinobot-sdio-wifi.zip"

SRC_URI[md5sum] = "442d14b6ccba4402cc4d0b31e4b3e99c"
SRC_URI[sha256sum] = "ccd931bcd84fa6b58421b0edc861146698db52fca4c2974fb0fc3ef08e70447a"
