DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.2.2"
SRCDATE = "20120831"
PV = "${KV}+${SRCDATE}"
PR = "r4"

SRC_URI[md5sum] = "407bed7913b7bb2d87a2a982bb27743e"
SRC_URI[sha256sum] = "9fc069c72d93b477cbcbdfd764779d782796415317c97ba47d72d31c7adaa96b"

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
