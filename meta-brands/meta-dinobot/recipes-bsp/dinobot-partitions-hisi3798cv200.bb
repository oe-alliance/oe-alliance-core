SUMMARY = "Dinobot partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCDATE = "20190716"
PR = "${SRCDATE}"

S = "${WORKDIR}"

SRC_URI = "http://source.mynonpublic.com/dinobot/${SOC_FAMILY}-partitions-${SRCDATE}.zip"

do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 ${S}/apploader.bin ${D}/usr/share/apploader.bin
}

FILES_${PN} = "/usr/share"

SRC_URI[md5sum] = "0cf44e0fc8dec29539f1a40af3a8a96f"
SRC_URI[sha256sum] = "a55964f609c6ef521f5992ad8ace2d788d970c1df01f12dde36ab20729477eb1"

INSANE_SKIP_${PN} += "already-stripped"
