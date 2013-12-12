DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20131212"
KV = "3.12.4"
PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI = "http://archiv.mixos-support.com/ebox-dvb-modules-${MACHINE}-${KV}-${SRCDATE}.zip"


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

SRC_URI[md5sum] = "5d01db85a001549ef6dd82a866c8b8a3"
SRC_URI[sha256sum] = "94be39442d221301245ab61bb5c10a14eb4598ea03902e93e9e7cee85a7d9a1c"
