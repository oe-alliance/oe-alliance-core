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

SRC_URI[md5sum] = "eb65ccb8b33f37a17ab499a3c43d9a1f"
SRC_URI[sha256sum] = "2d6cacae56b23c317fa19ca2882d774b358e83e34b31af155b85331e5025970f"
