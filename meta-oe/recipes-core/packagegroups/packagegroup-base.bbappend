
PACKAGES += " \
            ${@bb.utils.contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-smbfs-client", "", d)} \
            ${@bb.utils.contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-smbfs-server", "", d)} \
            ${@bb.utils.contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-smbfs-utils", "", d)} \
            ${@bb.utils.contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-samba", "", d)} \
            "

RDEPENDS:packagegroup-base := "${@oe.utils.str_filter_out('packagegroup-base-nfs', '${RDEPENDS:packagegroup-base}', d)}"
RDEPENDS:packagegroup-base := "${@oe.utils.str_filter_out('packagegroup-base-smbfs', '${RDEPENDS:packagegroup-base}', d)}"

RDEPENDS:packagegroup-base-smbfs += "\
    cifs-utils \
    "

RDEPENDS:packagegroup-base-smbfs-client = "\
    packagegroup-base-smbfs \
    smbclient \
    "

RDEPENDS:packagegroup-base-smbfs-server = "\
    samba \
    "

RDEPENDS:packagegroup-base-smbfs-utils = "\
    samba-admin \
    samba-utils \
    "

RRECOMMENDS:packagegroup-base-samba = "\
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-smbfs-utils \
    "

RDEPENDS:packagegroup-base-nfs += "\
    nfs-utils \
    nfs-utils-client"

RDEPENDS:packagegroup-base-zeroconf += "\
    libnss-llmnr"

RDEPENDS:packagegroup-base += "\
    coreutils-truefalse \
    stb-hwclock \
    fake-hwclock"
