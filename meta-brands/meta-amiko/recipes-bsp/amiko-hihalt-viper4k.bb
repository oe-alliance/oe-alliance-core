SUMMARY = "halt for AMIKO Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "harfbuzz"

SRCDATE = "20200415"

PV = "${SRCDATE}"
PR = "r1"

INITSCRIPT_NAME = "suspend"
INITSCRIPT_PARAMS = "start 89 0 ."
inherit update-rc.d

SRC_URI  = "https://source.mynonpublic.com/amiko/${MACHINE}-hihalt-${SRCDATE}.tar.gz \
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

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
FILES:${PN}  = "${bindir}/hihalt ${sysconfdir}/init.d"

SRC_URI[md5sum] = "92fb56f8f44e2d3004415377caba6851"
SRC_URI[sha256sum] = "83d248a8474f9725c4f9a0383613156f07b654718fce2033e6d1177e7262b0c2"
