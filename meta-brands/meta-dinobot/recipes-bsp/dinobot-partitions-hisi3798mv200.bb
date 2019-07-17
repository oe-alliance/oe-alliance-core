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

SRC_URI[md5sum] = "20363ec258cd283cb89908cbd132809e"
SRC_URI[sha256sum] = "0f68f71dd3abbc2df8169900bc87165b768bd17bbf2b9871de76d534b2d5752b"

INSANE_SKIP_${PN} += "already-stripped"
