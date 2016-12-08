SUMMARY = "Linux driver for 802.11a/b/g universal NIC cards using Atheros chip sets"
LICENSE = "BSD-3-Clause | GPLv2"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=58383ec8217e551da24db177e27a6ff2"
# versions of OpenWrt backfire (10.03)
SRCREV = "30414"
HAL_VERSION = "20090508"

SRC_URI = "http://snapshots.madwifi-project.org/madwifi-trunk/${BP}.tar.gz \
           svn://svn.openwrt.org/openwrt/trunk/package/madwifi;module=patches \
           http://mirror2.openwrt.org/sources/ath_hal-${HAL_VERSION}.tgz;name=hal \
           file://fix-target-mips32.patch \
           file://remove-wprobe.patch;apply=no \
           file://fix-module-autoload.patch;apply=no \
           file://fix-build-3.1.patch;apply=no \
           file://fix-build-3.2.patch;apply=no \
           file://ath-rate-ctlname.patch;apply=no \
           file://set-affinity-hint.patch;apply=no \
           file://workaround-high-interrupt-latency.patch;apply=no \
           file://dm8000-nand-error-hack.patch;apply=no \
           file://madwifi-smp-affinity"
SRC_URI[md5sum] = "2c7352cbbdac995de8c3bce5b80db5f2"
SRC_URI[sha256sum] = "0599c75b95ba63bdc554cb8124192e62c75fbeb71b9e8a5a7bc351c8e0666758"
SRC_URI[hal.md5sum] = "4ab7ae8bdb96c0be388c98bf8f92d5ca"
SRC_URI[hal.sha256sum] = "ced93d25aea7ee43807147a0269e69a072e718d59e7dab904bbe48b900409483"

S = "${WORKDIR}/${BP}"

inherit module

addtask postpatch after do_patch before do_configure

do_postpatch() {
        rm -rf hal
        cp -a ${WORKDIR}/ath_hal-${HAL_VERSION} hal
        rm -f ${WORKDIR}/patches/104-autocreate_none.patch
        rm -f ${WORKDIR}/patches/446-single_module.patch
        rm -f ${WORKDIR}/patches/470-mac_addresss_from_ath5k_platform_data.patch
        for i in ${WORKDIR}/patches/*.patch; do
                patch -p1 -i $i
        done
        patch -p1 -i ${WORKDIR}/remove-wprobe.patch
        patch -p1 -i ${WORKDIR}/fix-module-autoload.patch
        patch -p1 -i ${WORKDIR}/fix-build-3.1.patch
        patch -p1 -i ${WORKDIR}/fix-build-3.2.patch
        patch -p1 -i ${WORKDIR}/ath-rate-ctlname.patch
        patch -p1 -i ${WORKDIR}/set-affinity-hint.patch
        patch -p1 -i ${WORKDIR}/workaround-high-interrupt-latency.patch
        patch -p1 -i ${WORKDIR}/dm8000-nand-error-hack.patch
}

EXTRA_OEMAKE = "KERNELCONF=${STAGING_KERNEL_BUILDDIR}/.config \
                KERNELPATH=${STAGING_KERNEL_DIR} \
                KERNELRELEASE=${KERNEL_VERSION} \
                TOOLPREFIX=${TARGET_PREFIX}"

do_compile() {
        oe_runmake CC="${CC}" CFLAGS="${CFLAGS}" LD="${LD}" LDFLAGS="${LDFLAGS}" svnversion.h
        oe_runmake CC="${CC}" CFLAGS="${CFLAGS}" LD="${LD}" LDFLAGS="${LDFLAGS}" tools
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake -C ${STAGING_KERNEL_DIR} M="${S}" \
                   CC="${KERNEL_CC}" LD="${KERNEL_LD}" \
                   AR="${KERNEL_AR}" \
                   O="${STAGING_KERNEL_BUILDDIR}" \
                   modules
}
do_install() {
        oe_runmake DESTDIR=${D} BINDIR=${sbindir} MANDIR=${mandir} install-tools
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake -C ${STAGING_KERNEL_DIR} M="${S}" \
                   DEPMOD="echo" INSTALL_MOD_PATH="${D}" \
                   CC="${KERNEL_CC}" LD="${KERNEL_LD}" \
                   O="${STAGING_KERNEL_BUILDDIR}" \
                   modules_install
        install -d ${D}${includedir}/${BPN}/include
        install -m 644 include/compat.h ${D}${includedir}/${BPN}/include
        install -d ${D}${includedir}/${BPN}/net80211
        install -m 644 net80211/*.h ${D}${includedir}/${BPN}/net80211
        install -d ${D}${sysconfdir}/network/if-pre-up.d
        install -m 0755 ${WORKDIR}/madwifi-smp-affinity ${D}${sysconfdir}/network/if-pre-up.d
        install -d ${D}${sysconfdir}/network/if-post-down.d
        ln -sf ../if-pre-up.d/madwifi-smp-affinity ${D}${sysconfdir}/network/if-post-down.d
}

PACKAGES =+ "${PN}-modules ${PN}-tools"

RRECOMMENDS_${PN}-modules = "kernel-module-aes-generic"

FILES_${PN}-modules = "${sysconfdir}/network"
FILES_${PN}-tools = "${sbindir}/*"

KERNEL_MODULES_META_PACKAGE = "${PN}-modules"

CLEANBROKEN = "1"
