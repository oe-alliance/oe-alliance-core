SUMMARY = "libreader for Dags ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(force5|dual)$"

SRCDATE = "20211230"

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

SRC_URI[md5sum] = "d5ed4536a6cb6aa2d50dd452a0361760"
SRC_URI[sha256sum] = "f39797bd63d3119f3f35f10d950e39f5846618e63779f888dd7943b40ee8a4f7"

INSANE_SKIP:${PN} += "already-stripped"
