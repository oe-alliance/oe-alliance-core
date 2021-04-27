require wireguard.inc

inherit bash-completion systemd pkgconfig

DEPENDS = "libmnl"

do_compile_prepend () {
    cd ${S}/tools
}

do_install () {
    cd ${S}/tools
    oe_runmake DESTDIR="${D}" PREFIX="${prefix}" SYSCONFDIR="${sysconfdir}" \
        SYSTEMDUNITDIR="${systemd_unitdir}" \
        WITH_SYSTEMDUNITS=${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'yes', '', d)} \
        WITH_BASHCOMPLETION=yes \
        WITH_WGQUICK=yes \
        install
}

FILES_${PN} = " \
    ${sysconfdir} \
    ${systemd_unitdir} \
    ${bindir} \
"
# Get the kernel version for this image, we need it to build conditionally on kernel version
export KERNEL_VERSION = "${@oe.utils.read_file('${PKGDATA_DIR}/kernel-depmod/kernel-abiversion')}"

RDEPENDS_${PN} = "${@ 'wireguard-module' if ("${KERNEL_VERSION}" and bb.utils.vercmp_string("${KERNEL_VERSION}", '5.6') < 0) else 'kernel-module-wireguard' } bash"
