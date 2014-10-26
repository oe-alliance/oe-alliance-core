SUMMARY = "Command-line tools to flash and recover your Dreambox"
HOMEPAGE = "http://dreamboxupdate.com/"
LICENSE = "CLOSED"
SRCREV = "f6d9de6dc38272d83f19f63afe874e1cb58a8c61"
SRCREV_dm7080 = "54707ebcd84f7c9bfefc534e92ab242b5b4b432e"

SRC_URI_append = ";branch=${BRANCH}"

inherit opendreambox-git

do_install() {
    oe_runmake install DESTDIR=${D}
}

PACKAGES =+ "flash-scripts"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = " \
    e2fsprogs-mke2fs \
    flash-scripts \
    gpgv \
    parted \
    pigz \
    util-linux-mount \
"
RDEPENDS_flash-scripts = " \
    coreutils-realpath \
    fastboot \
    mkbootblob \
    util-linux-sfdisk \
"

FILES_flash-scripts = " \
    ${sbindir}/flash-rescue \
    ${sbindir}/flash-kernel \
    ${sbindir}/flash-ssbl \
    ${sbindir}/librecovery \
    ${sbindir}/select-boot-source \
    ${sbindir}/to-the-rescue \
"

BRANCH = "master"
BRANCH_dm7080 = "dm7080"

COMPATIBLE_MACHINE = "^(dm7080)$"
