DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20121127"
KV = "2.6.18-7.4-ebox5000"
PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI = "http://archiv.mixos-support.com/ebox-dvb-modules-${MACHINE}-${KV}-${SRCDATE}.tar.bz2"


S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
	install -d ${D}/lib/modules/${KV}/extra
	for f in *.ko; do
		install -m 0644 ${WORKDIR}/$f ${D}/lib/modules/${KV}/extra/$f;
	done
	install -d ${D}/${sysconfdir}/modules-load.d
	for i in `ls | grep \\.ko | sed -e 's/.ko//g'`; do
		echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
	done
}

SRC_URI[md5sum] = "a04afd8785d2e9e17db99e40bd597f21"
SRC_URI[sha256sum] = "53c35e82a021a1cbda39ac80e8d48aa0fd4aa133722940f9d6de57bbcab0ada5"
