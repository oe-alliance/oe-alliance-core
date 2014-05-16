SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PV = "${KV}+${SRCDATE}"
PR = "r11"

RDEPENDS_${PN}_et5x00 += "et-fpupdate-${MACHINE}"
RDEPENDS_${PN}_et6x00 += "et-fpupdate-${MACHINE}"
RDEPENDS_${PN}_et9x00 += "et-fpupdate-${MACHINE}"

DOWNLOADURL = "http://source.mynonpublic.com/download/drivers"
DOWNLOADURL_et8000 = "http://xtrendet.com"
DOWNLOADURL_et10000 = "http://xtrendet.com"

#SRC_URI = "http://www.et-view.com/download/drivers/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"
SRC_URI = "${DOWNLOADURL}/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in tpm modloader modloader2 dvb; do
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
