SUMMARY = "ddbootup for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINEBUILD}"

PV = "1.0"
PR = "r0"

S = "${WORKDIR}"

inherit update-rc.d

INITSCRIPT_NAME = "ddbootup"

do_compile() {
}

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    echo '#!/bin/sh' > ${WORKDIR}/ddbootup
    echo '### BEGIN INIT INFO' >> ${WORKDIR}/ddbootup
    echo '# Provides:             ddbootup' >> ${WORKDIR}/ddbootup
    echo '# Required-Start:       $local_fs' >> ${WORKDIR}/ddbootup
    echo '# Required-Stop:        ' >> ${WORKDIR}/ddbootup
    echo '# Should-Start:         dmesg volatile' >> ${WORKDIR}/ddbootup
    echo '# Should-Stop:          ' >> ${WORKDIR}/ddbootup
    echo '# Default-Start:        S' >> ${WORKDIR}/ddbootup
    echo '# Default-Stop:         ' >> ${WORKDIR}/ddbootup
    echo '### END INIT INFO' >> ${WORKDIR}/ddbootup
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd", "echo 'echo 1 > /proc/stb/lcd/mode' >> ${WORKDIR}/ddbootup" , "", d)}
    echo 'touch /dev/dbox/lcd0' >> ${WORKDIR}/ddbootup
    echo 'echo "${MACHINEBUILD}" > /proc/stb/info/gbmodel' >> ${WORKDIR}/ddbootup
    install -m 0755 ${WORKDIR}/ddbootup ${D}${sysconfdir}/init.d
    #ln -sf ../init.d/ddbootup ${D}${sysconfdir}/rcS.d/S39ddbootup
}

FILES_${PN} += "${sysconfdir}"
