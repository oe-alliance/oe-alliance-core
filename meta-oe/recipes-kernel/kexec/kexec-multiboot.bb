SUMMARY = "kexec-multiboot"
PRIORITY = "required"
MAINTAINER = "Eddi openBH"
require conf/license/license-gplv2.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI="git://github.com/oe-alliance/kexec-multiboot.git;protocol=https;branch=main"

S = "${WORKDIR}/git/${MACHINE}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/kernel_auto.bin ${D}${bindir}/kernel_auto.bin
    install -m 0755 ${S}/STARTUP_cpio.bin ${D}${bindir}/STARTUP.cpio.gz    
}

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
INSANE_SKIP:${PN} = "already-stripped ldflags"
