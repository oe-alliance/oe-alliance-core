KV = "4.4.35"
SRCDATE = "20180427"

KOFILES = "kds mali_kbase hi_dbe hi_tuner hi_sci hi_pmoc tntfs"

do_install_append() {
	install -d ${D}/lib/firmware/brcm
	install -m 0644 ${S}/brcmfmac4339-sdio.bin ${D}/lib/firmware/brcm
	install -m 0644 ${S}/brcmfmac4339-sdio.txt ${D}/lib/firmware/brcm
}

FILES_${PN} += "/lib/firmware/brcm"

require dinobot-dvb-modules.inc

SRC_URI += "file://u5pvr-platform.zip"

SRC_URI[md5sum] = "35a39cb7c946c8316180391967efb165"
SRC_URI[sha256sum] = "5f6f7f3e71ef8202f58d24a539abb170b21c521c865b40421dc68d71d05befcf"


