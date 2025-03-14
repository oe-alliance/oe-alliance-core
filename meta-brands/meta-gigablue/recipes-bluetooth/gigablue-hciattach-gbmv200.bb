SUMMARY = "hciattach sprd for Gigablue Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit update-rc.d
INITSCRIPT_NAME = "BTInit.sh"
INITSCRIPT_PARAMS = "defaults 60 "

SRCDATE = "20230530"

PV = "${SRCDATE}"
PR = "r2"

SRC_URI  = "https://source.mynonpublic.com/gigablue/mv200/${MACHINE}-hciattach-${SRCDATE}.tar.gz \
            file://${INITSCRIPT_NAME} \
"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hciattach_sprd ${D}/${bindir}
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/${INITSCRIPT_NAME} ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/hciattach_sprd ${sysconfdir}"

SRC_URI[md5sum] = "6b517b54e0abf3a769bef300e50bd00a"
SRC_URI[sha256sum] = "e59dbe3c2f5fe06b044b3393cbc4810394a408ebaf7b4390520e72c3110a5c0c"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
