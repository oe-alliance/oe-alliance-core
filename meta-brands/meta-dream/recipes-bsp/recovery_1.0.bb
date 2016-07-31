SUMMARY = "Command-line tools to flash and recover your Dreambox"
HOMEPAGE = "http://dreamboxupdate.com/"
LICENSE = "CLOSED"
SRCREV = "f6d9de6dc38272d83f19f63afe874e1cb58a8c61"
SRCREV_dm520 = "465ab0601a290079fbd3ecc3cabd25f84b5efb06"
SRCREV_dm820 = "9919a65aeb1c7e6f54f6b6df21cb94354f611901"
SRCREV_dm7080 = "d04ef152c73dac9460a56503d81708eb4f6dcff2"

SRC_URI_append = ";branch=${BRANCH}"

inherit opendreambox-git

do_install() {
    oe_runmake install DESTDIR=${D}
}

PACKAGES =+ "flash-scripts"

PACKAGE_ARCH = "${MACHINE_ARCH}"

EMMCRDEPENDS_recovery = " \
    e2fsprogs-mke2fs \
    parted \
"
EMMCRDEPENDS_flash-scripts = " \
    util-linux-sfdisk \
"
NANDRDEPENDS_recovery = " \
    mtd-utils-ubifs \
"
NANDRDEPENDS_flash-scripts = " \
    mtd-utils \
"
FASTBOOTRDEPENDS_flash-scripts = " \
    fastboot \
    mkbootblob \
"
RDEPENDS_${PN} = " \
    flash-scripts \
    gpgv \
    pigz \
    util-linux-mount \
"
RDEPENDS_${PN}_append_bcm7435 = " \
    ${EMMCRDEPENDS_recovery} \
"
RDEPENDS_${PN}_append_bcm73625 = " \
    ${NANDRDEPENDS_recovery} \
"

RDEPENDS_flash-scripts = " \
    coreutils-realpath \
"
RDEPENDS_flash-scripts_append_bcm7435 = " \
    ${EMMCRDEPENDS_flash-scripts} \
    ${FASTBOOTRDEPENDS_flash-scripts} \
"
RDEPENDS_flash-scripts_append_bcm73625 = " \
    ${NANDRDEPENDS_flash-scripts} \
"

FILES_flash-scripts = " \
    ${sbindir}/flash-rescue \
    ${sbindir}/flash-kernel \
    ${sbindir}/flash-ssbl \
    ${sbindir}/librecovery \
    ${sbindir}/select-boot-source \
    ${sbindir}/to-the-rescue \
    ${sbindir}/writespi \
"

BRANCH = "master"
BRANCH_dm520 = "dm520"
BRANCH_dm820 = "dm820"
BRANCH_dm7080 = "dm7080"

COMPATIBLE_MACHINE = "^(dm520|dm820|dm7080)$"
