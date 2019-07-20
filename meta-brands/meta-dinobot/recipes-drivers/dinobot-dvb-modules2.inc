SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI = "http://source.mynonpublic.com/dinobot/${MACHINE}-drivers-${KV}-${SRCDATE}.tar.gz \
    file://loadmodules.sh \
"

S = "${WORKDIR}/hiko"

INITSCRIPT_NAME = "loadmodules"
INITSCRIPT_PARAMS = "start 01 S ."

inherit update-rc.d

INHIBIT_PACKAGE_STRIP = "1"

FILES_${PN} += "${nonarch_base_libdir}/modules/${KV}/extra"
FILES_${PN} += "/usr/bin/komession"
FILES_${PN} += "${sysconfdir}/init.d"
FILES_${PN} += "${sysconfdir}/modules-load.d"

do_compile() {
}
do_populate_sysroot() {
}

do_package_qa() {
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KV}/extra
    install -d ${D}/usr/bin
    install -m 0644 ${S}/*.ko ${D}${nonarch_base_libdir}/modules/${KV}/extra
    install -m 0644 ${S}/dvbmodule ${D}${nonarch_base_libdir}/modules/${KV}/extra
    install -m 0755 ${S}/komession ${D}/usr/bin
    install -m 0755  ${S}/load ${D}${nonarch_base_libdir}/modules/${KV}/extra
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/loadmodules.sh ${D}${sysconfdir}/init.d/loadmodules
}

INSANE_SKIP_${PN} += "already-stripped ldflags"
