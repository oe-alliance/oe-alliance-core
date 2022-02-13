require wireguard.inc

inherit bash-completion systemd pkgconfig

DEPENDS = "libmnl"
SRC_URI = "https://git.zx2c4.com/wireguard-tools/snapshot/wireguard-tools-${PV}.tar.xz"
SRC_URI[md5sum] = "1d98fb1623817721466152365aec8c45"
SRC_URI[sha256sum] = "97ff31489217bb265b7ae850d3d0f335ab07d2652ba1feec88b734bc96bd05ac"

S = "${WORKDIR}/wireguard-tools-${PV}/src/"

do_install () {
    oe_runmake DESTDIR="${D}" PREFIX="${prefix}" SYSCONFDIR="${sysconfdir}" \
        SYSTEMDUNITDIR="${systemd_unitdir}" \
        WITH_SYSTEMDUNITS=${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'yes', '', d)} \
        WITH_BASHCOMPLETION=yes \
        WITH_WGQUICK=yes \
        install
}

FILES:${PN} = " \
    ${sysconfdir} \
    ${systemd_unitdir} \
    ${bindir} \
"
# Get the kernel version for this image, we need it to build conditionally on kernel version
export KERNEL_VERSION = "${@oe.utils.read_file('${PKGDATA_DIR}/kernel-depmod/kernel-abiversion')}"

RDEPENDS:${PN} = "${@ 'wireguard-module' if ("${KERNEL_VERSION}" and bb.utils.vercmp_string("${KERNEL_VERSION}", '5.6') < 0) else 'kernel-module-wireguard' } bash"
