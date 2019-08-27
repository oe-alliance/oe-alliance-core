require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"


do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    echo "#!/bin/sh" > ${WORKDIR}/xcore-zapper.sh
    echo "echo 0 > /sys/module/brcmstb_${MACHINEBUILD}/parameters/ts_tap" >> ${WORKDIR}/xcore-zapper.sh
    install -m 0755 ${WORKDIR}/xcore-zapper.sh ${D}/etc/init.d/xcore-zapper.sh
    ln -sf ../init.d/xcore-zapper.sh ${D}${sysconfdir}/rcS.d/S39xcore-zapper
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
