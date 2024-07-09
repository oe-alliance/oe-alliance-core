KV = "4.4.35"
SRCDATE = "20191212"

do_install:append() {
	install -d ${D}/lib/firmware/brcm
	install -d ${D}/${sysconfdir}/modules-load.d
	install -m 0644 ${UNPACKDIR}/brcmfmac43455-sdio.bin ${D}/lib/firmware/brcm
	install -m 0644 ${UNPACKDIR}/brcmfmac43455-sdio.txt ${D}/lib/firmware/brcm
	install -m 0644 ${UNPACKDIR}/*.ko ${D}${nonarch_base_libdir}/modules/${KV}/extra
	install -m 0644 ${UNPACKDIR}/*.conf ${D}/${sysconfdir}/modules-load.d
}

FILES:${PN} += "/lib/firmware/brcm"

require dinobot-dvb-modules2.inc

SRC_URI += "file://u53x-platform.zip file://dinobot-sdio-wifi.zip"

SRC_URI[md5sum] = "885f3611ad924defcf1e1e1e590e4e4d"
SRC_URI[sha256sum] = "330142ee69ca19575d99c14d0cb230c89ef027bdee39c5416a67e63b8a0588a3"
