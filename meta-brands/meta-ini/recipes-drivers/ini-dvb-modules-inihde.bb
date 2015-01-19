SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

KV = "3.12.1"
SRCDATE = "20150115"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "d17f69c1d4ae9f2f17fe464a2b47cccf"
SRC_URI[sha256sum] = "6eea9ef1eb8c893bd3b5415d25471c9516039983cb6516f78e6f4983e84fe5fb"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in dvb; do
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
