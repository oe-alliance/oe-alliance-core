SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

KV = "3.14.29"
SRCDATE = "20161126"

PV = "${KV}+${SRCDATE}"
PR = "r1"

RDEPENDS_${PN} += "wetek-e2-procfs-${MACHINE} firmware-avl6211 firmware-mn88436 firmware-ap6210 firmware-dvb-usb-af9015"

SRC_URI = "file://wetek-dvb-modules-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    echo "wetekdvb" > ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    install -m 0755 ${WORKDIR}/wetekdvb.ko ${D}${nonarch_base_libdir}/modules/${KV}/extra/
}


FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"

do_package_qa() {
}

