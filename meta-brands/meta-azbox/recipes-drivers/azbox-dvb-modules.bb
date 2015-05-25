SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.9.2-opensat"


SRCDATE = "20130917"
SRCGET = "17092013"

SRCDATE_azboxhd = "20131022"
SRCGET_azboxhd = "22102013"


PV = "${KV}+${SRCDATE}"
PR = "r7"

SRC_URI = "http://azbox-enigma2-project.googlecode.com/files/${MACHINE}-dvb-modules-${KV}-oe-core-${SRCGET}.tar.gz;name=azbox-dvb-modules-${MACHINE}"
SRC_URI_azboxhd = "http://source.mynonpublic.com/${MACHINE}-dvb-modules-${KV}-oe-core-${SRCGET}.tar.gz;name=azbox-dvb-modules-${MACHINE}"

SRC_URI[azbox-dvb-modules-azboxhd.md5sum] = "03f439093ec5ce00e82a7ccd38fd3323"
SRC_URI[azbox-dvb-modules-azboxhd.sha256sum] = "75f1bd99868e30bf53ade89376284e5bcc7f57228344d726a5136a2241102d60"
SRC_URI[azbox-dvb-modules-azboxme.md5sum] = "3d7b8d240626a08f16c170e5832be618"
SRC_URI[azbox-dvb-modules-azboxme.sha256sum] = "48b48a94094ecce34398efcee7e17e780d9cce0ecf1510758078ed4e18f9ce6d"
SRC_URI[azbox-dvb-modules-azboxminime.md5sum] = "2c037462af10a7909f4c803a90b82a1d"
SRC_URI[azbox-dvb-modules-azboxminime.sha256sum] = "7ac3c8ac567ffe627850fda0c6713dce71305886af6e3cc286800fbfc394709a"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install_azboxhd() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in llad em8xxx 863xi2c az_cx24116 az_mxl201rf az_mxl5007t az_stv6110x az_stv090x az_tda10023 az_zl10353 nimdetect sci 863xdvb; do
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
    install -d ${D}/lib/firmware
    install -m 0644 ${WORKDIR}/dvb-fe-cx24116.fw ${D}/lib/firmware/dvb-fe-cx24116.fw
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in llad em8xxx 865xi2c avl6211 avl2108 mxl241sf nimdetect sci 865xdvb; do
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
    install -d ${D}/lib/firmware
    install -m 0644 ${WORKDIR}/dvb-fe-avl2108.fw ${D}/lib/firmware/dvb-fe-avl2108.fw
    install -m 0644 ${WORKDIR}/dvb-fe-avl2108-blind.fw ${D}/lib/firmware/dvb-fe-avl2108-blind.fw
    install -m 0644 ${WORKDIR}/dvb-fe-avl6211.fw ${D}/lib/firmware/dvb-fe-avl6211.fw
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf /lib/firmware/*"
