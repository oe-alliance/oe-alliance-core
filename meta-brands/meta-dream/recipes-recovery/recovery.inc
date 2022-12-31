SUMMARY = "Command-line tools to flash and recover your Dreambox"
HOMEPAGE = "http://dreamboxupdate.com/"
LICENSE = "CLOSED"
DEPENDS = "coreutils-native udev"

inherit pkgconfig update-rc.d

SRC_URI:append = ";branch=${BRANCH}"

do_install() {
    oe_runmake install DESTDIR=${D}
}

PACKAGES =+ "flash-scripts ${PN}-autoflashd ${PN}-fbscaled"

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

RDEPENDS:${PN} = " \
    ${PN}-autoflashd \
    flash-scripts \
    gpgv \
    pigz \
    util-linux-blkid \
    util-linux-mount \
"
RDEPENDS:${PN}:append:bcm7435 = " \
    ${EMMCRDEPENDS_recovery} \
"
RDEPENDS:${PN}:append:bcm7252s = " \
    ${EMMCRDEPENDS_recovery} \
"
RDEPENDS:${PN}:append:bcm73625 = " \
    ${NANDRDEPENDS_recovery} \
"
RDEPENDS:${PN}:append:meson64 = " \
    ${EMMCRDEPENDS_recovery} \
    ${PN}-fbscaled \
"
#RRECOMMENDS:${PN} = " \
#    recovery-ui \
#"

RDEPENDS:flash-scripts = " \
    coreutils-realpath \
    curl \
    tpm-ca \
"
RDEPENDS:flash-scripts:append:bcm7435 = " \
    ${EMMCRDEPENDS_flash-scripts} \
    ${FASTBOOTRDEPENDS_flash-scripts} \
"
RDEPENDS:flash-scripts:append:bcm7252s = " \
    ${EMMCRDEPENDS_flash-scripts} \
"
RDEPENDS:flash-scripts:append:bcm73625 = " \
    ${NANDRDEPENDS_flash-scripts} \
"
RDEPENDS:flash-scripts:append:meson64 = " \
    ${EMMCRDEPENDS_flash-scripts} \
    mkbootimg \
    u-boot-mkimage \
"
RRECOMMENDS:flash-scripts:meson64 = " \
    dreambox-dvb-modules-meson64-stb-core \
"
RRECOMMENDS:flash-scripts = " \
    dreambox-dvb-modules-${MACHINE}-stb-core \
"

FILES:${PN}-autoflashd = " \
    ${sysconfdir}/init.d/autoflashd \
    ${sbindir}/autoflashd \
"
FILES:${PN}-fbscaled = " \
    ${sysconfdir}/init.d/fbscaled \
    ${sbindir}/fbscaled \
"
FILES:flash-scripts = " \
    ${sbindir}/flash-fsbl \
    ${sbindir}/flash-rescue \
    ${sbindir}/flash-kernel \
    ${sbindir}/flash-ssbl \
    ${sbindir}/librecovery \
    ${sbindir}/readspi \
    ${sbindir}/select-boot-source \
    ${sbindir}/to-the-rescue \
    ${sbindir}/update-autoexec \
    ${sbindir}/update-rescue \
    ${sbindir}/writespi \
"

INITSCRIPT_PACKAGES = "${PN}-autoflashd ${PN}-fbscaled"
INITSCRIPT_NAME = ""
INITSCRIPT_NAME:${PN}-autoflashd = "autoflashd"
INITSCRIPT_NAME:${PN}-fbscaled = "fbscaled"
