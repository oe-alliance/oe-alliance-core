SUMMARY = "halt for beyonwiz Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "harfbuzz"

SRCDATE = "20190220"

PV = "${SRCDATE}"
PR = "r1"

INITSCRIPT_NAME = "suspend"
INITSCRIPT_PARAMS = "start 89 0 ."
inherit update-rc.d

SRC_URI  = "http://source.mynonpublic.com/beyonwiz/${MACHINE}-hihalt-${SRCDATE}.tar.gz \
    file://suspend.sh \
"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hihalt ${D}/${bindir}
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/suspend.sh ${D}${sysconfdir}/init.d/suspend
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/hihalt ${sysconfdir}/init.d"

SRC_URI[md5sum] = "5ab3a1a043d0c477f65d189436e0de8f"
SRC_URI[sha256sum] = "47a9575741f03b19c6543d2c4da99fb90a00fb489f3081dc9492e4e1454ffcd9"


