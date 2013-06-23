DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.3.1-opensat"

SRCDATE = "20130514"
SRCGET = "14052013"

PV = "${KV}+${SRCDATE}"
PR = "r1"


SRC_URI = "http://azbox-enigma2-project.googlecode.com/files/${MACHINE}-dvb-modules-${KV}-oe-core-${SRCGET}.tar.gz;name=azbox-dvb-modules-${MACHINE}"

SRC_URI[azbox-dvb-modules-azboxhd.md5sum] = "cb3b81f2d1a8327e2290345645c58d46"
SRC_URI[azbox-dvb-modules-azboxhd.sha256sum] = "e085c1384abb5e80f733404d1f6652ad603928014f1f3f7c700b653b59e26fb3"
SRC_URI[azbox-dvb-modules-azboxme.md5sum] = "7b880746eb2adcc5381cca10dae8f90c"
SRC_URI[azbox-dvb-modules-azboxme.sha256sum] = "19cffebe543a284166002230910185375f0bd233d8847498fcfb8fb06391a85c"
SRC_URI[azbox-dvb-modules-azboxminime.md5sum] = "a168e4e50d911185b90da45ba627d233"
SRC_URI[azbox-dvb-modules-azboxminime.sha256sum] = "8740632dfe6764c2465a748310549d775ef20648f62bc1e50dbfe46db2062a12"

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
