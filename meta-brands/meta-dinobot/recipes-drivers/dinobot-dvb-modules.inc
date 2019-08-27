SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI = "http://source.mynonpublic.com/dinobot/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INITSCRIPT_NAME = "zload-dinobot"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

INHIBIT_PACKAGE_STRIP = "1"

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
FILES_${PN} += "/lib/modules/${KV}/extra"
FILES_${PN} += "/usr/bin/komession"
FILES_${PN} += "${sysconfdir}/init.d"

do_compile() {
}

do_populate_sysroot() {
}

do_package_qa() {
}

do_install() {
	install -d ${D}/lib/modules/${KV}/extra
	install -d ${D}/usr/bin
	install -m 0644 ${S}/dvbmodule ${D}/lib/modules/${KV}/extra
	install -m 0755 ${S}/komession ${D}/usr/bin
	install -d ${D}/${sysconfdir}/modules-load.d
	for i in ${KOFILES}; do
		install -m 0644 ${S}/$i.ko ${D}/lib/modules/${KV}/extra
		echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
	done
	install -d ${D}/etc/init.d/
	echo "#!/bin/sh" > ${S}/zload-dinobot
	echo "komession" >> ${S}/zload-dinobot
	echo "echo 2097152 > /proc/sys/net/core/rmem_max" >> ${S}/zload-dinobot
	echo "echo 6 > /proc/sys/vm/dirty_ratio" >> ${S}/zload-dinobot
	echo "echo 1 > /proc/sys/vm/overcommit_memory" >> ${S}/zload-dinobot
	echo "echo 8192 > /proc/sys/vm/min_free_kbytes" >> ${S}/zload-dinobot
	echo "echo interactive > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor" >> ${S}/zload-dinobot
	echo "echo 200000 > /sys/devices/system/cpu/cpufreq/interactive/timer_rate" >> ${S}/zload-dinobot
	install -m 0755 ${S}/zload-dinobot ${D}${sysconfdir}/init.d
}

INSANE_SKIP_${PN} += "already-stripped ldflags"