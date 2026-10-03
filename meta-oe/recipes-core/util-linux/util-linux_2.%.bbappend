FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# util-linux 2.41 switched `flock --fcntl` to open-file-description locks,
# which require Linux 3.15 or newer.  OE-Alliance still supports receivers
# with older vendor kernels, so retain an OPKG-compatible POSIX fcntl fallback
# for every distribution.
SRC_URI:append = " file://0001-flock-fallback-to-posix-locks-on-old-kernels.patch"

PACKAGES =+ "util-linux-flock"
FILES:util-linux-flock = "${base_sbindir}/flock.${BPN}"

ALTERNATIVE:util-linux-flock = "flock"
ALTERNATIVE_LINK_NAME[flock] = "${base_sbindir}/flock"

# Lower the priorities of util-linux-(u)mount, so that if they happen to
# become installed, they won't replace the working busybox commands.
ALTERNATIVE_PRIORITY[mount] = "10"
ALTERNATIVE_PRIORITY[umount] = "10"

SSTATE_ALLOW_OVERLAP_FILES += "${STAGING_DIR_NATIVE}/bin/login"

do_install:append () {
    if [ "${base_sbindir}" != "${sbindir}" ]; then
        mkdir -p ${D}${base_sbindir}
        if [ -f "${D}${bindir}/flock" ]; then
            mv "${D}${bindir}/flock" "${D}${base_sbindir}/flock"
        fi
    fi
}

PACKAGE_NO_LOCALE = "1"
