SUMMARY = "libreader for Dags ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(force5|dual)$"

SRCDATE = "20210804"

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

SRC_URI[md5sum] = "bf641b6a1befc6233e9ea436022b552e"
SRC_URI[sha256sum] = "edbbb48749d72c28be168d2d878134290ef61e3704e2468bc78bada030b37508"

INSANE_SKIP:${PN} += "already-stripped"
