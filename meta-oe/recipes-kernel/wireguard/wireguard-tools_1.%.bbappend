PR = "r2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RRECOMMENDS:${PN} += "${@bb.utils.contains_any("MACHINE", "osmini4k osmio4k osmio4kplus", "kernel-module-wireguard", "wireguard-module", d)} \
                      openresolv \
"
