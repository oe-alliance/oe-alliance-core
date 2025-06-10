SUMMARY = "WireGuard is an extremely simple yet fast and modern VPN"
DESCRIPTION = "WireGuard is a secure network tunnel, operating at layer 3, \
implemented as a kernel virtual network interface for Linux, which aims to \
replace both IPsec for most use cases, as well as popular user space and/or \
TLS-based solutions like OpenVPN, while being more secure, more performant, \
and easier to use."
SECTION = "kernel"
HOMEPAGE = "https://www.wireguard.io/"
LICENSE = "GPL-2.0-only"

LIC_FILES_CHKSUM = "file://../COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "https://git.zx2c4.com/wireguard-linux-compat/snapshot/wireguard-linux-compat-${PV}.tar.xz \
    file://0001-build-fallthrough-issue-fixed.patch \
    file://version.patch \
"

SRC_URI[md5sum] = "0499a3315b7013e65a07234dc83dec39"
SRC_URI[sha256sum] = "362d412693c8fe82de00283435818d5c5def7f15e2433a07a9fe99d0518f63c0"

S = "${WORKDIR}/wireguard-linux-compat-${PV}/src"

inherit module kernel-module-split

DEPENDS = "virtual/kernel libmnl"

# This module requires Linux 3.10 higher and several networking related
# configuration options. For exact kernel requirements visit:
# https://www.wireguard.io/install/#kernel-requirements

EXTRA_OEMAKE:append = " \
    KERNELDIR=${STAGING_KERNEL_DIR} \
    "

export KCFLAGS += "-std=gnu17 \
                  -Wno-error=misleading-indentation \
                  -Wno-error=parentheses \
                  -Wno-error=shift-overflow \
                  -Wno-error=array-bounds \
                  -Wno-error=array-compare \
                  -Wno-error=sizeof-array-div \
                  -Wno-error=bool-compare \
                  -Wno-error=maybe-uninitialized \
                  -Wno-error=unused-variable \
                  -Wno-error=stringop-overflow \
                  -Wno-error=stringop-overread \
                  -Wno-error=zero-length-bounds \
                  -Wno-error=builtin-declaration-mismatch \
                  -Wno-error=address \
                  -Wno-error=unused-const-variable \
                  -Wno-error=enum-int-mismatch \
                  -Wno-error=dangling-pointer \   
                  -Wno-error=format \   
"

MAKE_TARGETS = "module"

RRECOMMENDS:${PN} = "kernel-module-xt-hashlimit"
MODULE_NAME = "wireguard"

# Kernel module packages MUST begin with 'kernel-module-', otherwise
# multilib image generation can fail.
#
# The following line is only necessary if the recipe name does not begin
# with kernel-module-.
PKG:${PN} = "kernel-module-${MODULE_NAME}"
RDEPENDS:remove:kernel-module-${MODULE_NAME}-${KERNEL_VERSION} = "kernel-module-ip6-udp-tunnel-${KERNEL_VERSION} kernel-module-udp-tunnel-${KERNEL_VERSION}"
# The kernel modules in RRECOMMENDS are only available in kernel versions 3.18+. In versions before 3.18, wireguard-linux-compat uses its own compatibility layer.
RRECOMMENDS:append:kernel-module-${MODULE_NAME}-${KERNEL_VERSION} = "${@"" if bb.utils.vercmp_string_op('${PREFERRED_VERSION_${PREFERRED_PROVIDER_virtual/kernel}}', '3.18', '<') \
                                                                           else "kernel-module-ip6-udp-tunnel-${KERNEL_VERSION} kernel-module-udp-tunnel-${KERNEL_VERSION}"}"


module_do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/${MODULE_NAME}
    install -m 0644 ${MODULE_NAME}.ko \
    ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/${MODULE_NAME}/${MODULE_NAME}.ko
}
