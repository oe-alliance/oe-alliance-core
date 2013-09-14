DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.9.2-opensat"


SRCDATE = "20130914"
SRCGET = "14092013"

PV = "${KV}+${SRCDATE}"
PR = "r5"

SRC_URI = "http://azbox-enigma2-project.googlecode.com/files/${MACHINE}-dvb-modules-${KV}-oe-core-${SRCGET}.tar.gz;name=azbox-dvb-modules-${MACHINE}"

SRC_URI[azbox-dvb-modules-azboxhd.md5sum] = "f9c610a1791a19b1a159a28b1c6af6dc"
SRC_URI[azbox-dvb-modules-azboxhd.sha256sum] = "83053fc1be966fed6d4c2649889298263ed8c8b8542cd8692bc4698b119047b5"
SRC_URI[azbox-dvb-modules-azboxme.md5sum] = "6ed35e7fa6a05ef2a5a0ec41ed44498c"
SRC_URI[azbox-dvb-modules-azboxme.sha256sum] = "e1a8057ddb39639272ac09b8c55392003bedc94ff2f9ceb2acc220b8d800a1a0"
SRC_URI[azbox-dvb-modules-azboxminime.md5sum] = "f607641b2335f2b3d0ab5f921bce0f63"
SRC_URI[azbox-dvb-modules-azboxminime.sha256sum] = "6ec019edda91012e3b74436b01393f7cd0c7deccbaeb9056657ae1f562084504"

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
