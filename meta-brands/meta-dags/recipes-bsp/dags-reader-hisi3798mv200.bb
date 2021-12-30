SUMMARY = "libreader for Dags ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(force5|dual)$"

SRCDATE = "20211229"

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

SRC_URI[md5sum] = "3c89db0f372a26eb1228dd3432237a12"
SRC_URI[sha256sum] = "459fc825fa589bada00efab7b357de61b1ab32574f5c8445c5d4a4d216af198a"

INSANE_SKIP_${PN} += "already-stripped"
