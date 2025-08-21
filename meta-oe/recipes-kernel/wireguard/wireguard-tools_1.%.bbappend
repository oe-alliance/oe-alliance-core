PR = "r3"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RRECOMMENDS:${PN} += "${@bb.utils.contains_any("MACHINE", "osmini4k osmio4k osmio4kplus u5pvr", "kernel-module-wireguard", "wireguard-module", d)} \
                      openresolv \
"
