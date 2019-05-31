SECTION = "base"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PROVIDES += "vuplus-wifi-util"
RPROVIDES_${PN} += "vuplus-wifi-util"

PV="16.1"
PR="${SRCDATE}.${SRCDATE_PR}"

SRC_URI = " \
    http://archive.vuplus.com/download/build_support/vuplus/vuplus-wifi-util-${MACHINE}-${PV}-${PR}.tar.gz \
"

S="${WORKDIR}/vuplus-wifi-util-${MACHINE}"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/* ${D}${bindir}
}

do_package_qa() {
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES_${PN}="/"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/bcmdl ${D}${bindir}
	install -m 0755 ${S}/wl ${D}${bindir}
	install -m 0755 ${S}/wl-config.sh ${D}${bindir}
	install -m 0755 ${S}/wl-down.sh ${D}${bindir}
	sed  's!wl ssid $SSID!wl ssid "$SSID"!' -i ${D}${bindir}/wl-config.sh

	install -d ${D}/usr/local/modules
	install -m 0644 ${S}/dhd.ko ${D}/usr/local/modules/dhd.ko

	install -d ${D}/lib/firmware/bcm43569/
	install -m 0644 ${S}/bcm43569-firmware.bin ${D}/lib/firmware/bcm43569/
	install -m 0644 ${S}/bcm43569_iton.nvm ${D}/lib/firmware/bcm43569/

}

INSANE_SKIP_${PN} = "already-stripped"
