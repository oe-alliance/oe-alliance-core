SUMMARY = "Linux driver for 802.11a/b/g universal NIC cards using Atheros chip sets"
SECTION = "base"
LICENSE = "BSD-3-Clause | GPLv2"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=58383ec8217e551da24db177e27a6ff2"
INC_PR = "2"

SRC_URI = "http://snapshots.madwifi-project.org/madwifi-trunk/${BP}.tar.gz"

S = "${WORKDIR}/${BP}"

inherit module machine_kernel_pr

EXTRA_OEMAKE = "KERNELPATH=${STAGING_KERNEL_DIR} KERNELRELEASE=${KERNEL_VERSION} TOOLPREFIX=${TARGET_PREFIX}"

do_compile() {
        oe_runmake tools
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake modules
}
do_install() {
        oe_runmake DESTDIR=${D} BINDIR=${sbindir} MANDIR=${mandir} install-tools
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake DESTDIR=${D} BINDIR=${sbindir} MANDIR=${mandir} install-modules
        install -d ${D}${includedir}/${BPN}/include
        install -m 644 include/compat.h ${D}${includedir}/${BPN}/include
        install -d ${D}${includedir}/${BPN}/net80211
        install -m 644 net80211/*.h ${D}${includedir}/${BPN}/net80211
}

PACKAGES =+ "${PN}-modules ${PN}-tools"

RDEPENDS_${PN}-modules = "kernel-module-aes-generic"

FILES_${PN}-modules = "/lib/modules/"
FILES_${PN}-tools = "${sbindir}/*"
