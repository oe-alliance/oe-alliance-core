SUMMARY = "libreader for Dags ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(force5|dual)$"

SRCDATE = "20210209"

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

SRC_URI[md5sum] = "e19aa269008615c812432cbe75e047fd"
SRC_URI[sha256sum] = "917c31048334098f6adce7c0f8791aa745baba515d77f44c6e21dad873f77b24"

INSANE_SKIP_${PN} += "already-stripped"
