require conf/license/openpli-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI = " \
	file://turnoff_power \
	file://vuplus-shutdown.sh "

INITSCRIPT_NAME = "vuplus-shutdown"
INITSCRIPT_PARAMS = "start 89 0 ."

inherit autotools pkgconfig update-rc.d

do_install() {
	install -d ${D}/etc/init.d/
	install -m 0755 ${WORKDIR}/vuplus-shutdown.sh ${D}/etc/init.d/vuplus-shutdown
	install -d ${D}/usr/bin
	install -m 0755 ${WORKDIR}/turnoff_power ${D}/usr/bin
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

