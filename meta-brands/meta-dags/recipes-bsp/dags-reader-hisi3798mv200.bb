SUMMARY = "libreader for Dags ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(force5|dual)$"

SRCDATE = "20210421"

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

SRC_URI[md5sum] = "8a4bb434537eca188de80ea91fda1dc9"
SRC_URI[sha256sum] = "7faec6a456c63ffb5012215207de5d56cfe2800cccdcabc928a1183f36e850e0"

INSANE_SKIP_${PN} += "already-stripped"
