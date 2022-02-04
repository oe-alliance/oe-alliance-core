SUMMARY = "libreader for Dags ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(force5|dual)$"

SRCDATE = "20220204"

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

SRC_URI[md5sum] = "05de5a83da6f8b09da8d00246fd111d6"
SRC_URI[sha256sum] = "dcdf5b0acda8f9972fbf07d49402d8830faaec5b0831cd9b3f000dc2c769ae98"

INSANE_SKIP:${PN} += "already-stripped"
