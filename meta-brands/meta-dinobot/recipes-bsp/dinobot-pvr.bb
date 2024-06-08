require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    echo "#!/bin/sh" > ${UNPACKDIR}/dinobot-pvr.sh
    echo "echo 1 > /proc/stb/parameters/ts_tap" >> ${UNPACKDIR}/dinobot-pvr.sh
    install -m 0755 ${UNPACKDIR}/dinobot-pvr.sh ${D}/etc/init.d/dinobot-pvr.sh
    ln -sf ../init.d/dinobot-pvr.sh ${D}${sysconfdir}/rcS.d/S39dinobot-pvr
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
