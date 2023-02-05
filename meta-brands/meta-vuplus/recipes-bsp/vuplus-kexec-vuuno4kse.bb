SUMMARY = "kexec-uno4kse"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r1"

SRC_URI = " \
    file://kernel_auto.bin \
    file://STARTUP_cpio.bin \        
"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/kernel_auto.bin ${D}${bindir}/kernel_auto.bin
    install -m 0755 ${WORKDIR}/STARTUP_cpio.bin ${D}${bindir}/STARTUP.cpio.gz    
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} = "already-stripped ldflags"

COMPATIBLE_MACHINE = "^(vuuno4kse)$"
