require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"

pkg_postinst_${PN}() {
#!/bin/sh
rm -rf /etc/init.d/spycat-zapper > /dev/null 2>&1
rm -rf /etc/rcS.d/S39spycat-zapper > /dev/null 2>&1
echo 1 > /sys/module/brcmstb_spycat/parameters/ts_tap
exit 0
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
