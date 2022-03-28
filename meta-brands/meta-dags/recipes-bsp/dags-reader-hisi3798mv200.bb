SUMMARY = "libreader for Dags ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(force5|dual)$"

SRCDATE = "20220328"

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

SRC_URI[md5sum] = "48c97eb96a679588f73fef7f1b89e892"
SRC_URI[sha256sum] = "d865cfacb41724fc674c4a555c0679067933161b901ec5e3efe2de736ac4299d"

INSANE_SKIP_${PN} += "already-stripped"
