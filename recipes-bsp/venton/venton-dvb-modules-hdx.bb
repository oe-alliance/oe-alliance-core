DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.2.2"
SRCDATE = "20120727"
PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI[md5sum] = "c092c4f4561d415c829b742b7728f044"
SRC_URI[sha256sum] = "70a7462e929f218483920c6a667e40fc91df1617149b6ee83e5796f085abbd69"

RDEPENDS_${PN} += "venton-fpupdate-hdx"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

PACKAGE_STRIP = "1"

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
