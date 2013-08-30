DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.9.2-opensat"

SRCDATE = "28082013"

PV = "${KV}+${SRCDATE}"
PR = "r4"

SRC_URI = "http://azbox-enigma2-project.googlecode.com/files/${MACHINE}-dvb-modules-${KV}-oe-core-${SRCDATE}.tar.gz;name=azbox-dvb-modules-${MACHINE}"

SRC_URI[azbox-dvb-modules-azboxhd.md5sum] = "7117133c259cf8a0e3e83203472c3eda"
SRC_URI[azbox-dvb-modules-azboxhd.sha256sum] = "cef6c288ec0f2c7ce96aade182fb105cd3d3fb5caf95a38156b2720f5292b6e0"
SRC_URI[azbox-dvb-modules-azboxme.md5sum] = "5dd63b2fe92d82258691c5a1765cb4dc"
SRC_URI[azbox-dvb-modules-azboxme.sha256sum] = "9067d11342b03fe72c8d82736fc0dd366a72662d76e0b7dfc5abef399ba8fc20"
SRC_URI[azbox-dvb-modules-azboxminime.md5sum] = "ff4d9ce4b60929e504ae26fe2f2a72b9"
SRC_URI[azbox-dvb-modules-azboxminime.sha256sum] = "532e9e8e806f3457714d288473cc96f3de41c11fa7eab8ff17504ca652fecb73"

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
