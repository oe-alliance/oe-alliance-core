KV = "4.4.35"
SRCDATE = "20190906"

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

SRC_URI[md5sum] = "713bd8baf854e4e2697e6dae6ef8169b"
SRC_URI[sha256sum] = "b56a85306a0cfe16744e9955bb24c8b55cd0d331fc018bc4b0d12da3e8a3311d"
