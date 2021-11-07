SUMMARY = "libreader for Dags ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(force5|dual)$"

SRCDATE = "20211105"

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

FILES_${PN}  = "${bindir}/app_init"

SRC_URI[md5sum] = "27cb9347da6893bfa0cff5fa1be2cb8e"
SRC_URI[sha256sum] = "de09d230087bfe4ff247d969cd9c4037d9956d81d9a4a1a85254c27b6726eccd"

INSANE_SKIP_${PN} += "already-stripped"
