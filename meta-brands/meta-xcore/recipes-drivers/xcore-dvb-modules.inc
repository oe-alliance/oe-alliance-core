SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/xcore/${MACHINE_DRIVER}-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in ${KOFILES} ; do
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

do_install_append_xc7362() {
    install -d ${D}/lib/firmware
    if [ -e ${WORKDIR}/mn88472.fw ]; then
        install -m 0755 ${WORKDIR}/mn88472.fw ${D}/lib/firmware
    fi
    if [ -e ${WORKDIR}/mn88473.fw ]; then
       install -m 0755 ${WORKDIR}/mn88473.fw ${D}/lib/firmware
    fi
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
FILES_${PN}_append_xc7362 += "/lib/firmware"