SUMMARY = "pau"
PRIORITY = "required"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r0"

SRC_URI = " \
    file://pau \
    file://pau.sh \
"

do_install() {
    install -d ${D}${sysconfdir}/init.d/
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/pau ${D}${bindir}
    install -m 0755 ${WORKDIR}/pau.sh ${D}${sysconfdir}/init.d/
}

inherit update-rc.d

INITSCRIPT_NAME = "pau.sh"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 ."

PACKAGE_ARCH = "${MACHINE_ARCH}"
