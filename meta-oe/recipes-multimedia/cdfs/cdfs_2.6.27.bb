SUMMARY = "CDfs filesystem"
HOMEPAGE = "http://users.elis.ugent.be/~mronsse/cdfs/"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

MACHINE_KERNEL_PR:append = ".7"

SRC_URI = " \
        https://fossies.org/linux/misc/old/cdfs-2.6.27.tar.bz2 \
        file://0001-Fix-compile-error-with-linux-2.6.32.patch \
        file://0002-Fix-compile-error-with-linux-2.6.37.patch \
        file://fix-strange-errors.patch \
        file://compile-fix-linux-3.2.patch \
        file://add-3.4-support.patch \
        file://add-3.8-support.patch \
        file://0001-support-up-to-3.12.patch \
        file://add-3.14-support.patch \
        file://0001-add-3.16-support.patch \
        file://add-4.0-support.patch \
        file://add-4.2-support.patch \
        file://add-4.6-support.patch \
        file://add-4.11-support.patch \
        file://add-4.12-support.patch \
        file://add-5.0-support.patch \
        file://add-5.4-support.patch \
        file://add-5.6-support.patch \
        file://add-5.8-support.patch \
        file://add-5.9-support.patch \
        file://add-5.11-support.patch \
        file://add-5.14-support.patch \
        file://add-5.15-support.patch \
"
SRC_URI[md5sum] = "ac64c014a90e3c488394832ea29605b3"
SRC_URI[sha256sum] = "d034f6c6d9578fe2addfaeceaa101584a4a1fc9f27d825c340baebd345d8d724"

inherit module machine_kernel_pr

# need only for dreambox linux-meson64 4.9
export KCFLAGS += " -Wno-error=misleading-indentation \
                    -Wno-error=aggressive-loop-optimizations \
                    -Wno-error=int-to-pointer-cast \
                    -Wno-error=restrict \
                    -Wno-error=int-conversion \
                    -Wno-error=maybe-uninitialized \
                    -Wno-error=discarded-qualifiers \
                    -Wno-error=switch-unreachable \
                    -Wno-error=bool-operation \
                    -Wno-error=declaration-after-statement \
                    -Wno-error=incompatible-pointer-types \
                    -Wno-error \
"

do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake -C "${STAGING_KERNEL_BUILDDIR}" M="${S}" modules
}

do_install() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake -C "${STAGING_KERNEL_BUILDDIR}" M="${S}" DEPMOD=echo INSTALL_MOD_PATH="${D}" modules_install
}


