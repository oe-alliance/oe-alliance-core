SUMMARY = "ddbootup for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE}"

PV = "1.0"

S = "${UNPACKDIR}"

do_compile() {
}

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    echo '#! /bin/sh' > ${UNPACKDIR}/ddbootup
    echo 'echo 1 > /proc/stb/lcd/mode' >> ${UNPACKDIR}/ddbootup
    echo 'touch /dev/dbox/lcd0' >> ${UNPACKDIR}/ddbootup
    echo 'echo "${MACHINE}"  > /proc/stb/info/vumodel' >> ${UNPACKDIR}/ddbootup
    echo 'echo "dm8000\n" > /proc/stb/info/model' >> ${UNPACKDIR}/ddbootup
    echo 'echo "vuplus" > /proc/stb/info/boxtype' >> ${UNPACKDIR}/ddbootup
    echo 'sleep 1' >> ${UNPACKDIR}/ddbootup
    echo 'echo 50 > /proc/progress' >> ${UNPACKDIR}/ddbootup
    install -m 0755 ${UNPACKDIR}/ddbootup ${D}${sysconfdir}/init.d
    ln -sf ../init.d/ddbootup ${D}${sysconfdir}/rcS.d/S66ddbootup
}

FILES:${PN} += "${sysconfdir}"
