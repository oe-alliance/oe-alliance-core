require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r2"

SRC_URI = "file://osmini-zapper.sh"


do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    install -m 0755 ${WORKDIR}/osmini-zapper.sh ${D}/etc/init.d/osmini-zapper
    ln -sf ../init.d/osmini-zapper ${D}${sysconfdir}/rcS.d/S39osmini-zapper
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
