SUMMARY = "WireGuard is an extremely simple yet fast and modern VPN"
DESCRIPTION="WireGuard is a secure network tunnel, operating at layer 3, \
implemented as a kernel virtual network interface for Linux, which aims to \
replace both IPsec for most use cases, as well as popular user space and/or \
TLS-based solutions like OpenVPN, while being more secure, more performant, \
and easier to use."
SECTION = "networking"
HOMEPAGE = "https://www.wireguard.io/"
LICENSE = "GPL-2.0-only"

LIC_FILES_CHKSUM = "file://../COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "https://git.zx2c4.com/wireguard-tools/snapshot/wireguard-tools-${PV}.tar.xz"
SRC_URI[md5sum] = "1d98fb1623817721466152365aec8c45"
SRC_URI[sha256sum] = "97ff31489217bb265b7ae850d3d0f335ab07d2652ba1feec88b734bc96bd05ac"

S = "${WORKDIR}/wireguard-tools-${PV}/src"

inherit bash-completion systemd pkgconfig

DEPENDS = "libmnl"

PACKAGE_ARCH = "${MACHINE_ARCH}"

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

RRECOMMENDS:${PN} = "kernel-module-wireguard wireguard-module bash"

INSANE_SKIP:${PN} = "build-deps"
