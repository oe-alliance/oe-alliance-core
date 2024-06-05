require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"


do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    echo "#!/bin/sh" > ${WORKDIR}/cpu-helper.sh
    echo "echo userspace > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor" >> ${WORKDIR}/cpu-helper.sh
    echo "echo 1000000 > /sys/devices/system/cpu/cpu0/cpufreq/scaling_setspeed" >> ${WORKDIR}/cpu-helper.sh
    install -m 0755 ${S}/cpu-helper.sh ${D}/etc/init.d/cpu-helper.sh
    ln -sf ../init.d/cpu-helper.sh ${D}${sysconfdir}/rcS.d/S99cpu-helper
}

PACKAGE_ARCH = "${MACHINE_ARCH}"