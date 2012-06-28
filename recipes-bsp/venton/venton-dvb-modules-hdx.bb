DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.2.2"
SRCDATE = "20120627"

SRC_URI[md5sum] = "ce0e5fbfa3d8744aa28293285cb192c1"
SRC_URI[sha256sum] = "02ad793872f6a80f19950dbd601759e49d0038bfbf17528764a82dd3123d7702"

PV = "${KV}+${SRCDATE}"

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
	install -d ${D}/${sysconfdir}/modutils
	for i in `ls | grep \\.ko | sed -e 's/.ko//g'`; do
		echo $i >> ${D}/${sysconfdir}/modutils/_venton
	done
}
