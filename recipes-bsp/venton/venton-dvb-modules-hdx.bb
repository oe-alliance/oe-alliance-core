DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.2.2"
SRCDATE = "20120803"
PV = "${KV}+${SRCDATE}"
PR = "r3"

SRC_URI[md5sum] = "1113a7d1aa9de9d624e25a1af8871750"
SRC_URI[sha256sum] = "3a1d2ff12467c0154df6637e889fbba59cbf9e942d2c4042172d85dd3e4f7a17"

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
