SUMMARY = "Dinobot partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCDATE = "20190517"
PR = "${SRCDATE}"

S = "${WORKDIR}"

SRC_URI = "http://source.mynonpublic.com/dinobot/${SOC_FAMILY}-partitions-${SRCDATE}.zip"

do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 ${S}/apploader.bin ${D}/usr/share/apploader.bin
}

FILES_${PN} = "/usr/share"

SRC_URI[md5sum] = "45a57b4d2f53d85561d619b7d1cf0526"
SRC_URI[sha256sum] = "d310c29548b450b76d39dca8a624c697d4252b10ef43b039f3a1c7a5841fcb2e"

INSANE_SKIP_${PN} += "already-stripped"
