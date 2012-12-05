DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.1.1"
KVPR = ""
KV_vusolo2 = "3.3.6"
KVPR_vusolo2 = "-1.2"
KV_vuduo2 = "2.6.37"

PV = "${KV}+${SRCDATE}"
PR = "r5"

VUPLUS_KERNEL_GCC = "4.4.3"
# SRC_URI = "http://archive.vuplus.com/download/drivers/vuplus-dvb-modules-${DOWNLOADMACHINE}-${KV}-${VUPLUS_KERNEL_GCC}-${SRCDATE}.tar.gz"
SRC_URI = "http://archive.vuplus.com/download/drivers/beta/private/vuplus-dvb-modules-${DOWNLOADMACHINE}-${KV}-${VUPLUS_KERNEL_GCC}-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

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
