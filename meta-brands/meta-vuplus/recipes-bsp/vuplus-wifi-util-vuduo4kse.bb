require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuduo4kse)$"

PV = "17.1"
SRCDATE = "20250704"
SRCDATE_PR = "r1"

PR = "${SRCDATE}.${SRCDATE_PR}.1"

SRC_URI = " \
	https://source.mynonpublic.com/vuplus/release/wifi/vuplus-wifi-util-${MACHINE}-${PV}-${SRCDATE}.${SRCDATE_PR}.tar.gz \
"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 60 S ."
INITSCRIPT_NAME = "vuplus-wifi-init.sh"

CYW4373_INITSCRIPT_NAME = "cyw4373-wifi-init.sh"
CYW4373_INITSCRIPT_PARAMS = "start 75 S ."

do_install:append() {
	install -d ${D}${sysconfdir}/udev
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/bcmwifi_firmware.sh ${D}${sysconfdir}/udev/
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/bcmwifi_drv.sh ${D}${sysconfdir}/udev/
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/${CYW4373_INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${CYW4373_INITSCRIPT_NAME}
	install -m 0644 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/brcmfmac.ko ${D}/usr/local/modules/brcmfmac.ko
	install -m 0644 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/brcmutil.ko ${D}/usr/local/modules/brcmutil.ko
	install -m 0644 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/compat.ko ${D}/usr/local/modules/compat.ko
	install -m 0644 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/cfg80211.ko ${D}/usr/local/modules/cfg80211.ko
	install -d ${D}/lib/firmware/cypress/
	install -m 0644 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/cyfmac4373.bin ${D}/lib/firmware/cypress/
	install -m 0644 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/cyfmac4373.clm_blob ${D}/lib/firmware/cypress/
	install -m 0644 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/brcmfmac4373-usb.txt ${D}/lib/firmware/cypress/
	install -m 0644 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/regulatory.db ${D}/lib/firmware/
}

SRC_URI[md5sum] = "afa959f5c55172198c86ee7802276719"
SRC_URI[sha256sum] = "96b2b7aac76da53d125d8e400596b8ac7b1d2dc6189a89da0370864de742361f"
