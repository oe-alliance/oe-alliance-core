require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"

pkg_postinst_${PN}() {
#!/bin/sh
rm -rf /etc/init.d/dinobot-pvr.sh > /dev/null 2>&1
rm -rf /etc/rcS.d/S39dinobot-pvr > /dev/null 2>&1
echo 0 > /proc/stb/parameters/ts_tap
exit 0
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
