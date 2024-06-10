require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = " \
    file://ceryon-shutdown.sh "

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

INITSCRIPT_NAME = "ceryon-shutdown"
INITSCRIPT_PARAMS = "start 31 0 ."

inherit pkgconfig update-rc.d

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${UNPACKDIR}/ceryon-shutdown.sh ${D}/etc/init.d/ceryon-shutdown
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
