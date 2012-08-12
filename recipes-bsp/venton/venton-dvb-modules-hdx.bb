DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.2.2"
SRCDATE = "20120810"
PV = "${KV}+${SRCDATE}"
PR = "r4"

SRC_URI[md5sum] = "56fbb3c95b620cf34aec1fa5eb4fbbb4"
SRC_URI[sha256sum] = "065d8ef6a8e293a587fa4ec88bc49f6afeabc33b148082c8f3d75f5e5e132a70"

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
