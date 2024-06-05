SUMMARY = "halt for qviart Model ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "harfbuzz"

SRCDATE = "20230712"

PV = "${SRCDATE}"
PR = "r1"

INITSCRIPT_NAME = "suspend"
INITSCRIPT_PARAMS = "start 89 0 ."
inherit update-rc.d

SRC_URI  = "https://source.mynonpublic.com/qviart/${SOC_FAMILY}-hihalt-${SRCDATE}.tar.gz \
    file://suspend.sh \
"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hihalt ${D}/${bindir}
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/suspend.sh ${D}${sysconfdir}/init.d/suspend
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/hihalt ${sysconfdir}/init.d"

SRC_URI[md5sum] = "c82d6a4787a7683af947735aead468c9"
SRC_URI[sha256sum] = "f3a97688080d288fd0066fd0b36f79868c467c2c58a77fe5ab22a9a847469f35"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
