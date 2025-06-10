FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://0001-build-fallthrough-issue-fixed.patch \
    file://version.patch \
"

PR = "r1"

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
