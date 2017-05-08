SUMMARY = "Command-line tools to flash and recover your Dreambox"
HOMEPAGE = "http://dreamboxupdate.com/"
LICENSE = "CLOSED"
DEPENDS = "coreutils-native udev"

inherit pkgconfig update-rc.d

SRC_URI_append = ";branch=${BRANCH}"

do_install() {
    oe_runmake install DESTDIR=${D}
}

PACKAGES =+ "flash-scripts"

PACKAGE_ARCH = "${MACHINE_ARCH}"

EMMCRDEPENDS_recovery = " \
    e2fsprogs-mke2fs \
    gptfdisk \
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
RDEPENDS_${PN}_append_bcm7252s = " \
    ${EMMCRDEPENDS_recovery} \
"
RDEPENDS_${PN}_append_bcm73625 = " \
    ${NANDRDEPENDS_recovery} \
"

RDEPENDS_flash-scripts = " \
    coreutils-realpath \
    tpm-ca \
"
RDEPENDS_flash-scripts_append_bcm7435 = " \
    ${EMMCRDEPENDS_flash-scripts} \
    ${FASTBOOTRDEPENDS_flash-scripts} \
"
RDEPENDS_flash-scripts_append_bcm7252s = " \
    ${EMMCRDEPENDS_flash-scripts} \
"
RDEPENDS_flash-scripts_append_bcm73625 = " \
    ${NANDRDEPENDS_flash-scripts} \
"

FILES_flash-scripts = " \
    ${sbindir}/flash-fsbl \
    ${sbindir}/flash-rescue \
    ${sbindir}/flash-kernel \
    ${sbindir}/flash-ssbl \
    ${sbindir}/librecovery \
    ${sbindir}/readspi \
    ${sbindir}/select-boot-source \
    ${sbindir}/to-the-rescue \
    ${sbindir}/update-rescue \
    ${sbindir}/writespi \
"

INITSCRIPT_NAME = "autoflashd"
