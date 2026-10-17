FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR .= ".4"
PACKAGE_NO_LOCALE = "1"

# These receivers need tighter reclaim limits without discarding warm caches
# before every Enigma2 start. Machines can override either value if required.
PROCPS_DIRTY_RATIO ?= "${@bb.utils.contains_any('MACHINE_FEATURES', 'singlecore smallflash', '20', '60', d)}"
PROCPS_VFS_CACHE_PRESSURE ?= "${@bb.utils.contains_any('MACHINE_FEATURES', 'singlecore smallflash', '200', '100', d)}"

# sysctl.conf now depends on MACHINE_FEATURES, including within one CPU tune.
PACKAGE_ARCH = "${MACHINE_ARCH}"
CONFFILES:${PN}-sysctl += "${sysconfdir}/sysctl.conf"

do_install:append() {
    sed -i \
        -e 's/@PROCPS_DIRTY_RATIO@/${PROCPS_DIRTY_RATIO}/g' \
        -e 's/@PROCPS_VFS_CACHE_PRESSURE@/${PROCPS_VFS_CACHE_PRESSURE}/g' \
        ${D}${sysconfdir}/sysctl.conf
}
