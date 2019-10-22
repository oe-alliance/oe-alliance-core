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

SRC_URI[md5sum] = "05e9085355ab11b4c2a260e3e71b64e6"
SRC_URI[sha256sum] = "486c8e434892a2a816a203de3fb9e059fa0a02d5e8b416b131bde279f531dadd"
