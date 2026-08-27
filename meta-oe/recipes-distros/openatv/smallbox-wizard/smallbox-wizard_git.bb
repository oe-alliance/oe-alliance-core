SUMMARY = "Native framebuffer first-boot wizard for OE-Alliance SmallBox receivers"
DESCRIPTION = "Creates either a USB /usr FlashExpander or a Chkroot Multiboot rootfs, exact 512 MiB swap, wired networking and a complete Enigma2 installation before Enigma2 starts."
HOMEPAGE = "https://github.com/oe-alliance/SmallBoxWizard"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a067a4afd955d5a9dd14fbc7bc14e4b4"

COMPATIBLE_MACHINE = "${@'.*' if d.getVar('DISTRO') == 'openatv' else '^$'}"

inherit gitpkgv update-rc.d

SRCREV = "${AUTOREV}"
PV = "0.2.0+git"
PKGV = "0.2.0+git${GITPKGV}"

SRC_URI = " \
    git://github.com/oe-alliance/SmallBoxWizard.git;protocol=https;branch=main \
    file://smallbox-wizard.init \
"

INITSCRIPT_NAME = "smallbox-wizard"
INITSCRIPT_PARAMS = "start 99 3 ."

# FLASHSIZE is an image budget on several old receivers, not necessarily the
# physical NAND size. Only machines explicitly marked Chkroot-only suppress
# FlashExpander. All other SmallBox receivers with validated chkrootmb kernel
# support may choose Chkroot up to 256 MiB; 512 MiB and larger disables it.
SMALLBOX_CHKROOT_POLICY = "${@'required' if bb.utils.contains('MACHINE_FEATURES', 'smallbox-chkroot-required', True, False, d) else ('optional' if bb.utils.contains('MACHINE_FEATURES', 'smallflash', True, False, d) and bb.utils.contains('MACHINE_FEATURES', 'chkrootmb', True, False, d) and 0 < int(d.getVar('FLASHSIZE') or '0') <= 256 else 'disabled')}"
SMALLBOX_CHKROOT_ENABLED = "${@'1' if d.getVar('SMALLBOX_CHKROOT_POLICY') != 'disabled' else '0'}"
SMALLBOX_WIZARD_IMAGE_FEED ?= "https://images.mynonpublic.com/openatv/wizard.php?open=${MACHINEBUILD}"
SMALLBOX_WIZARD_IMAGE_URL ?= ""
SMALLBOX_WIZARD_IMAGE_MATCH ?= "_multiboot.zip"
SMALLBOX_WIZARD_MAXIMUM_SLOTS ?= "4"

RDEPENDS:${PN} = " \
    chrony \
    chronyc \
    e2fsprogs-mke2fs \
    init-ifupdown \
    oe-alliance-feeds-configs \
    opkg \
    util-linux-blkid \
    util-linux-mkswap \
    util-linux-sfdisk \
    ${@'dosfstools ofgwrite unzip wget' if d.getVar('SMALLBOX_CHKROOT_ENABLED') == '1' else ''} \
"

do_compile() {
    oe_runmake \
        'CC=${CC}' \
        'CPPFLAGS=${CPPFLAGS} -I${S}/include' \
        'CFLAGS=${CFLAGS}' \
        'LDFLAGS=${LDFLAGS}'
}

do_install() {
    install -d ${D}${sbindir} ${D}${sysconfdir}/init.d ${D}${sysconfdir}
    install -m 0755 ${S}/smallbox-wizard ${D}${sbindir}/smallbox-wizard
    install -m 0755 ${UNPACKDIR}/smallbox-wizard.init \
        ${D}${sysconfdir}/init.d/smallbox-wizard
    printf '%s\n' \
        'chkroot=${SMALLBOX_CHKROOT_POLICY}' \
        'machine=${MACHINE}' \
        'machine_build=${MACHINEBUILD}' \
        'mtd_kernel=${MTD_KERNEL}' \
        'image_feed=${SMALLBOX_WIZARD_IMAGE_FEED}' \
        'image_url=${SMALLBOX_WIZARD_IMAGE_URL}' \
        'image_match=${SMALLBOX_WIZARD_IMAGE_MATCH}' \
        'maximum_slots=${SMALLBOX_WIZARD_MAXIMUM_SLOTS}' \
        'single_core=${@bb.utils.contains("MACHINE_FEATURES", "singlecore", "1", "0", d)}' \
        > ${D}${sysconfdir}/smallbox-wizard.conf
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
