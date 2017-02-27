PR .= ".1"

PACKAGES += " \
            ${@bb.utils.contains("DISTRO_FEATURES", "appletalk", "packagegroup-base-appletalk", "", d)} \
            ${@bb.utils.contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-smbfs-client", "", d)} \
            ${@bb.utils.contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-smbfs-server", "", d)} \
            ${@bb.utils.contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-smbfs-utils", "", d)} \
            ${@bb.utils.contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-samba", "", d)} \
            "

RDEPENDS_packagegroup-base := "${@oe_filter_out('packagegroup-base-nfs', '${RDEPENDS_packagegroup-base}', d)}"
RDEPENDS_packagegroup-base := "${@oe_filter_out('packagegroup-base-smbfs', '${RDEPENDS_packagegroup-base}', d)}"

RDEPENDS_packagegroup-base-smbfs += "\
    cifs \
    "

RDEPENDS_packagegroup-base-smbfs-client = "\
    packagegroup-base-smbfs \
    smbclient \
    "

RDEPENDS_packagegroup-base-smbfs-server = "\
    packagegroup-base-smbfs \
    samba-base \
    "

RDEPENDS_packagegroup-base-smbfs-utils = "\
    samba \
    "

RDEPENDS_packagegroup-base-samba = "\
    packagegroup-base-smbfs \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-smbfs-utils \
    "

RRRECOMMENDS_packagegroup-base-appletalk = "\
    kernel-module-appletalk \
    kernel-module-llc \
    kernel-module-psnap"

RDEPENDS_packagegroup-base-appletalk = "\
    netatalk"

RDEPENDS_packagegroup-base-nfs += "\
    nfs-utils \
    nfs-utils-client"
