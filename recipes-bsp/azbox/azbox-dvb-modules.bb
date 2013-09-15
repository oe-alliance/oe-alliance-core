DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.9.2-opensat"


SRCDATE = "20130914"
SRCGET = "14092013"
SRCDATE_azboxme = "20130915"
SRCGET_azboxme = "15092013"
SRCDATE_azboxminime = "20130915"
SRCGET_azboxminime = "15092013"

PV = "${KV}+${SRCDATE}"
PR = "r5"

SRC_URI = "http://azbox-enigma2-project.googlecode.com/files/${MACHINE}-dvb-modules-${KV}-oe-core-${SRCGET}.tar.gz;name=azbox-dvb-modules-${MACHINE}"
SRC_URI_azboxme = "http://source.mynonpublic.com/${MACHINE}-dvb-modules-${KV}-oe-core-${SRCGET}.tar.gz;name=azbox-dvb-modules-${MACHINE}"
SRC_URI_azboxminime = "http://source.mynonpublic.com/${MACHINE}-dvb-modules-${KV}-oe-core-${SRCGET}.tar.gz;name=azbox-dvb-modules-${MACHINE}"

SRC_URI[azbox-dvb-modules-azboxhd.md5sum] = "f9c610a1791a19b1a159a28b1c6af6dc"
SRC_URI[azbox-dvb-modules-azboxhd.sha256sum] = "83053fc1be966fed6d4c2649889298263ed8c8b8542cd8692bc4698b119047b5"
SRC_URI[azbox-dvb-modules-azboxme.md5sum] = "8cae581232b0f7e4fbfeefd69975a5f5"
SRC_URI[azbox-dvb-modules-azboxme.sha256sum] = "1d8eb914872edeadc1e675fce24875920a791b4306505a1ef2e8f5af430e174a"
SRC_URI[azbox-dvb-modules-azboxminime.md5sum] = "64d585ac9091b816b321daa45b1d5c8e"
SRC_URI[azbox-dvb-modules-azboxminime.sha256sum] = "486554d9c1cadb56ab0c310a5ff375cae02b7b21d8050beba45446d6a0d2ca24"

S = "${WORKDIR}"

PACKAGE_STRIP = "no"

inherit module

do_compile() {
}

do_install_azboxhd() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modutils

    for f in llad em8xxx 863xi2c az_cx24116 az_mxl201rf az_mxl5007t az_stv6110x az_stv090x az_tda10023 az_zl10353 nimdetect sci 863xdvb; do
	install -m 0644 ${WORKDIR}/$f.ko ${D}/lib/modules/${KV}/extra/$f.ko
	echo $f >> ${D}/${sysconfdir}/modutils/_${MACHINE}
    done

    install -d ${D}/lib/firmware
    install -m 0644 ${WORKDIR}/dvb-fe-cx24116.fw ${D}/lib/firmware/dvb-fe-cx24116.fw

}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modutils

    for f in llad em8xxx 865xi2c avl6211 avl2108 mxl241sf nimdetect sci 865xdvb; do
	install -m 0644 ${WORKDIR}/$f.ko ${D}/lib/modules/${KV}/extra/$f.ko
	echo $f >> ${D}/${sysconfdir}/modutils/_${MACHINE}
    done

    install -d ${D}/lib/firmware
    install -m 0644 ${WORKDIR}/dvb-fe-avl2108.fw ${D}/lib/firmware/dvb-fe-avl2108.fw
    install -m 0644 ${WORKDIR}/dvb-fe-avl2108-blind.fw ${D}/lib/firmware/dvb-fe-avl2108-blind.fw
    install -m 0644 ${WORKDIR}/dvb-fe-avl6211.fw ${D}/lib/firmware/dvb-fe-avl6211.fw

}

FILES_${PN} = "/"
