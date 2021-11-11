SUMMARY = "libreader for Dags ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(force5|dual)$"

SRCDATE = "20211110"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "http://en3homeftp.net/pub/down/hisi3798mv200-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_compile() {
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/app_init ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/app_init"

SRC_URI[md5sum] = "b23cbe9c1b3fa05edfa7b6cfd555c2d0"
SRC_URI[sha256sum] = "161e244cffa541736658450c24bbca3b263f76d5b24a29dbbf7e9a88d8d86273"

INSANE_SKIP:${PN} += "already-stripped"
