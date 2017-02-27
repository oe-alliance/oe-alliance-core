require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r2"

SRC_URI = " file://wetek-shutdown.sh \
            file://wetek2-shutdown.sh \
"

S = "${WORKDIR}"

INITSCRIPT_NAME = "wetek-shutdown"
INITSCRIPT_PARAMS = "start 39 0 ."

inherit pkgconfig update-rc.d

do_install() {
    install -d ${D}/etc/init.d/
    if [ "${MACHINE}" = "wetekplay2" ]; then
        install -m 0755 ${WORKDIR}/wetek2-shutdown.sh ${D}/etc/init.d/wetek-shutdown
    else
        install -m 0755 ${WORKDIR}/wetek-shutdown.sh ${D}/etc/init.d/wetek-shutdown
    fi
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
