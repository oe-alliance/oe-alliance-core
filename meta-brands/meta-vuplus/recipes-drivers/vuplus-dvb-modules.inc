SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.1.1"
KVPR = ""
KV_vusolo2 = "3.3.8"
KVPR_vusolo2 = "-2.0"
KV_vuduo2 = "3.3.8"
KVPR_vuduo2 = "-2.0"

PV = "${KV}+${SRCDATE}"
PR = "r9"

VUPLUS_KERNEL_GCC = "4.4.3"
DOWNLOADMACHINE ?= "${MACHINE}"
SRC_URI = "http://archive.vuplus.com/download/drivers/vuplus-dvb-modules-${DOWNLOADMACHINE}-${KV}-${VUPLUS_KERNEL_GCC}-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_vuplus.conf"

do_install() {
    install -d ${D}/lib/modules/${KV}${KVPR}/extra
    for f in *.ko; do
        install -m 0644 ${WORKDIR}/$f ${D}/lib/modules/${KV}${KVPR}/extra/$f;
    done
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in `ls | grep \\.ko | sed -e 's/.ko//g'`; do
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
