SUMMARY = "WireGuard is an extremely simple yet fast and modern VPN"
DESCRIPTION="WireGuard is a secure network tunnel, operating at layer 3, \
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
"

SRC_URI:append:meson64 = " file://version.patch"

SRC_URI[md5sum] = "a407b380b6e4cee3be9a92cdcf5c4a2f"
SRC_URI[sha256sum] = "19b181e5c7d2260c23ecad4ad324425fd8e88200d97a885a2c7d4bd26cd61461"

S = "${WORKDIR}/wireguard-linux-compat-${PV}/src"

inherit module kernel-module-split

DEPENDS = "virtual/kernel libmnl"

# This module requires Linux 3.10 higher and several networking related
# configuration options. For exact kernel requirements visit:
# https://www.wireguard.io/install/#kernel-requirements

EXTRA_OEMAKE:append = " \
    KERNELDIR=${STAGING_KERNEL_DIR} \
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
RRECOMMENDS:append:kernel-module-${MODULE_NAME}-${KERNEL_VERSION} = "kernel-module-ip6-udp-tunnel-${KERNEL_VERSION} kernel-module-udp-tunnel-${KERNEL_VERSION}"


module_do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/${MODULE_NAME}
    install -m 0644 ${MODULE_NAME}.ko \
    ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/${MODULE_NAME}/${MODULE_NAME}.ko
}
