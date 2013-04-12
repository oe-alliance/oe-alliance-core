DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.3.1-opensat"

SRCDATE_azboxme = "20130315"
SRCDATE_azboxminime = "20130315"
SRCDATE_azboxhd = "20130315"


PV = "${KV}+${SRCDATE}"
MACHINE_KERNEL_PR_append = ".17"


SRC_URI = "http://azbox-enigma2-project.googlecode.com/files/${MACHINE}-dvb-modules-${KV}-${SRCDATE}.tar.gz;name=azbox-dvb-modules-${MACHINE}"

SRC_URI[azbox-dvb-modules-azboxhd.md5sum] = "7fc3a0876eea4717b382d01ac7e68e10"
SRC_URI[azbox-dvb-modules-azboxhd.sha256sum] = "20b0416c2f7ace18cd30268f114eb99e3b64fa93f0cc2d9be030e7104ae085c3"
SRC_URI[azbox-dvb-modules-azboxme.md5sum] = "92182199673eba33ea6a73f3f12fa183"
SRC_URI[azbox-dvb-modules-azboxme.sha256sum] = "842311f377cff7b0f86750e2e2b5ed16b52dc56f6f9707fc7c3153e6a7c0e81e"
SRC_URI[azbox-dvb-modules-azboxminime.md5sum] = "bdddccfa79157c3d6e58f7918f156afc"
SRC_URI[azbox-dvb-modules-azboxminime.sha256sum] = "7b8810692699386826a57f9368294d2bd1ebf1b5243538bfc20fa5a0dfc33b6f"

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

do_install_azboxme() {
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

do_install_azboxminime() {
 do_install_azboxme
}

FILES_${PN} = "/"


