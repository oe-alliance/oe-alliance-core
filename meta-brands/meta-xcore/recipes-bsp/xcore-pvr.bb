require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"

pkg_postinst_${PN}() {
#!/bin/sh
rm -rf /etc/init.d/xcore-zapper.sh > /dev/null 2>&1
rm -rf /etc/rcS.d/S39xcore-zapper > /dev/null 2>&1
echo 1 > /sys/module/brcmstb_${MACHINEBUILD}/parameters/ts_tap
exit 0
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
