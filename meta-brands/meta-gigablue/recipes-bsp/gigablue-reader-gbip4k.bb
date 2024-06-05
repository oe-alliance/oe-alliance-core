SUMMARY = "libreader for Gigablue Model ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(gbmv200)$"

SRCDATE = "20221220"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/gigablue/mv200/${MACHINEBUILD}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "a294e4449a156499731fec29ad7e0ef8"
SRC_URI[sha256sum] = "834bc85e7409ae1d24800d1c1762b581cddea47d2c4429a4feab108adf107b9a"

INSANE_SKIP:${PN} += "already-stripped"
