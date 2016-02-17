require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI = "file://spycat-zapper.sh"


do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    install -m 0755 ${WORKDIR}/spycat-zapper.sh ${D}/etc/init.d/spycat-zapper
    ln -sf ../init.d/spycat-zapper ${D}${sysconfdir}/rcS.d/S39spycat-zapper
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
