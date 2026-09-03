PR = "r3"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RRECOMMENDS:${PN} += "${@bb.utils.contains_any("MACHINE", "ed72604 u5pvr", "kernel-module-wireguard", "wireguard-module", d)} \
                      openresolv \
"
