SECTION = "base"

inherit oea-wifi-driver
require conf/license/license-close.inc

PROVIDES += "vuplus-wifi-util"
RPROVIDES:${PN} += "vuplus-wifi-util"

PV = "16.1"
PR = "${SRCDATE}.${SRCDATE_PR}.1"

SRC_URI = " \
    https://source.mynonpublic.com/vuplus/release/wifi/vuplus-wifi-util-${MACHINE}-${PV}-${SRCDATE}.${SRCDATE_PR}.tar.gz \
"

S = "${UNPACKDIR}"

do_package_qa() {
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES:${PN} = "/"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/bcmdl ${D}${bindir}
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/wl ${D}${bindir}
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/wl-config.sh ${D}${bindir}
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/wl-down.sh ${D}${bindir}
	sed  's!wl ssid $SSID!wl ssid "$SSID"!' -i ${D}${bindir}/wl-config.sh

	install -d ${D}/usr/local/modules
	install -m 0644 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/dhd.ko ${D}/usr/local/modules/dhd.ko

	install -d ${D}/lib/firmware/bcm43569/
	install -m 0644 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/bcm43569-firmware.bin ${D}/lib/firmware/bcm43569/
	install -m 0644 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/bcm43569_iton.nvm ${D}/lib/firmware/bcm43569/

}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} = "already-stripped"

oea_wifi_pick() {
	if ${@'true' if forced_oot_wifi(d, 'bcmdhd') else 'false'}; then
		rm -f ${D}/usr/local/modules/dhd.ko
		# Some of these boxes load the module from a udev helper instead.
		for f in ${D}${INIT_D_DIR}/${INITSCRIPT_NAME} ${D}${sysconfdir}/udev/bcmwifi_drv.sh; do
			[ -f "$f" ] || continue
			sed -i -e 's!^insmod $DRV_PATH/dhd.ko!modprobe bcmdhd!' -e '/^DRV_PATH=/d' "$f"
			# The directory tells enigma2 to drive the chip through the vendor tools.
			sed -i -e '/^rm -r \/tmp\/bcm/d' -e '/^mkdir -p \/tmp\/bcm/d' "$f"
		done
	fi
}
do_install[postfuncs] += "oea_wifi_pick"

RDEPENDS:${PN} += "${@'bcmdhd' if forced_oot_wifi(d, 'bcmdhd') else ''}"
