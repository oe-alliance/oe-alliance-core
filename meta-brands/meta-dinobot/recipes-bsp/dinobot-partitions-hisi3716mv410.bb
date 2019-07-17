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

SRC_URI[md5sum] = "f9575bf7494a3ac33459ddd2bc2b9f5f"
SRC_URI[sha256sum] = "6885bde3a1e8612df2408aabfb9f97a2f2da200ea01494a0bc46ac9a30cf8fdd"

INSANE_SKIP_${PN} += "already-stripped"
