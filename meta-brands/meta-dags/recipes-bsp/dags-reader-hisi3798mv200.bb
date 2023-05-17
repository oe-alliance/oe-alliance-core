SUMMARY = "libreader for Dags ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(force5|dual)$"

SRCDATE = "20230516"

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

SRC_URI[md5sum] = "cb47b4cdd148a598002f60643df99af1"
SRC_URI[sha256sum] = "df066a6e7b1b38c6f254257a2d4834a1f8977a5938762e24bb133c2f371da6e4"

INSANE_SKIP:${PN} += "already-stripped"
