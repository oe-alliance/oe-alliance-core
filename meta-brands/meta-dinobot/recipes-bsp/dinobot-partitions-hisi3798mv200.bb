SUMMARY = "Dinobot partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCDATE = "20190711"
PR = "${SRCDATE}"

S = "${WORKDIR}"

SRC_URI = "http://source.mynonpublic.com/dinobot/${SOC_FAMILY}-partitions-${SRCDATE}.zip"

do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 ${S}/apploader.bin ${D}/usr/share/apploader.bin
}

FILES_${PN} = "/usr/share"

SRC_URI[md5sum] = "dae32c99577f84967cbccbb618ce317b"
SRC_URI[sha256sum] = "498ee6356a3a46ff5a34fae8594e84d9477a1d7e3ba391f05a6e67a3f8f2ddae"

INSANE_SKIP_${PN} += "already-stripped"
