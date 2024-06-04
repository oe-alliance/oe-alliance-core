SUMMARY = "halt for Uclan Model ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "harfbuzz"

SRCDATE = "20220326"

PV = "${SRCDATE}"
PR = "r1"

INITSCRIPT_NAME = "suspend"
INITSCRIPT_PARAMS = "start 89 0 ."
inherit update-rc.d

SRC_URI  = "https://source.mynonpublic.com/uclan/${SOC_FAMILY}-hihalt-${SRCDATE}.tar.gz \
    file://suspend.sh \
"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${UNPACKDIR}/hihalt ${D}/${bindir}
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/suspend.sh ${D}${sysconfdir}/init.d/suspend
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/hihalt ${sysconfdir}/init.d"

SRC_URI[md5sum] = "a6c47491393917654c43188d4831e7fc"
SRC_URI[sha256sum] = "74e98134d4adff0f0bf942d66f1933333df33e96892b9dd88660361ef77f5090"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
