SUMMARY = "Platform Util drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE}"
require conf/license/license-close.inc

COMPATIBLE_MACHINE = "^(force4|lunix4k)$"

PV="17.1"

S="${WORKDIR}/platform-util-${MACHINE}"

SRC_URI[md5sum] = "d0bd0250b563b65178809f0ab196b01f"
SRC_URI[sha256sum] = "6b72a7d9930ebb45886b51ca81fca1374acdef27e1c842934ccebf86bac7878c"

SRC_URI = " \
    https://source.mynonpublic.com/dags/platform-util-${MACHINE}.zip \
    file://${INITSCRIPT_NAME}.sysvinit \
"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${UNPACKDIR}/* ${D}${bindir}
    install -d ${D}/etc/init.d
    install -m 0755 ${S}/${INITSCRIPT_NAME}.sysvinit ${D}/etc/init.d/${INITSCRIPT_NAME}
}

do_package_qa() {
}

FILES:${PN}="/"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 65 S . stop 90 0 ."
INITSCRIPT_NAME = "dags-platform-util"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped"
