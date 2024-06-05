SUMMARY = "showiframe for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
RDEPENDS:${PN} = "libjpeg-turbo"

COMPATIBLE_MACHINE = "^(u5pvr)$"

SRCDATE = "20180511"

PV = "${SRCDATE}"
PR = "r0"

RPROVIDES:${PN}  = "showiframe"
RREPLACES:${PN}  = "showiframe"
RCONFLICTS:${PN} = "showiframe"

SRC_URI = "https://source.mynonpublic.com/dinobot/${MACHINE}-showiframe-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/showiframe ${D}/${bindir}
    install -d ${D}${libdir}
    install -m 0755 ${S}/libcrypto.so.1.1 ${D}${libdir}/
    install -m 0755 ${S}/libssl.so.1.1 ${D}${libdir}/
    install -m 0755 ${S}/libcurl.so.3 ${D}${libdir}/
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/showiframe ${libdir}"

SRC_URI[md5sum] = "c09f0a16c8406b9e31c27e1379d524ae"
SRC_URI[sha256sum] = "2a57e8ebb1d903d1c6f9888d921a02f3ac1e37a567ae95695de53089902ad846"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP:${PN} += "already-stripped dev-so ldflags"
