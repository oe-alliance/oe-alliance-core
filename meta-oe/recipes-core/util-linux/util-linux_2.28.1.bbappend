PR .= ".1"

PACKAGES =+ "util-linux-flock"
FILES_util-linux-flock = "${base_sbindir}/flock.${BPN}"

ALTERNATIVE_util-linux-flock = "flock"
ALTERNATIVE_LINK_NAME[flock] = "${base_sbindir}/flock"

# Lower the priorities of util-linux-(u)mount, so that if they happen to
# become installed, they won't replace the working busybox commands.
ALTERNATIVE_PRIORITY[mount] = "10"
ALTERNATIVE_PRIORITY[umount] = "10"

SSTATE_DUPWHITELIST += "${STAGING_DIR_NATIVE}/bin/login"

do_install_append () {
    if [ "${base_sbindir}" != "${sbindir}" ]; then
        mkdir -p ${D}${base_sbindir}
        if [ -f "${D}${bindir}/flock" ]; then
            mv "${D}${bindir}/flock" "${D}${base_sbindir}/flock"
        fi
    fi
}
