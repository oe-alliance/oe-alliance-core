require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    echo "#!/bin/sh" > ${WORKDIR}/lib-helper.sh
    echo "ln -sf libjpeg.so.62.3.0 /usr/lib/libjpeg.so.62" >> ${WORKDIR}/lib-helper.sh
    install -m 0755 ${WORKDIR}/lib-helper.sh ${D}/etc/init.d/lib-helper.sh
    ln -sf ../init.d/lib-helper.sh ${D}${sysconfdir}/rcS.d/S99lib-helper
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
