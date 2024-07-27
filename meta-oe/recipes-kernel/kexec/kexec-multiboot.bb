SUMMARY = "kexec-multiboot"
PRIORITY = "required"
MAINTAINER = "Eddi openBH"
require conf/license/license-gplv2.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gittag

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/oe-alliance/kexec-multiboot.git;protocol=https;branch=main"
S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${bindir}
    ${@bb.utils.contains_any("DISTRO_NAME", "openvix openbh", "install -d -m 0755 ${D}/etc/init.d", "", d)}    
    install -m 0755 ${S}/${MACHINE}/kernel_auto.bin ${D}${bindir}/kernel_auto.bin
    install -m 0755 ${S}/${MACHINE}/STARTUP_cpio.bin ${D}${bindir}/STARTUP.cpio.gz
    ${@bb.utils.contains_any("DISTRO_NAME", "openvix openbh", "install -m 0755 ${S}/kexec-multiboot-recovery.sh ${D}/etc/init.d/kexec-multiboot-recovery", "", d)}     

    #Other distro might want, openvix and openbh prefers to start "/etc/init.d/kexec-multiboot-recovery start" from inside enigma2
    #update-rc.d -r ${D} kexec-multiboot-recovery start 99 S .
}

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
INSANE_SKIP:${PN} = "already-stripped ldflags"
