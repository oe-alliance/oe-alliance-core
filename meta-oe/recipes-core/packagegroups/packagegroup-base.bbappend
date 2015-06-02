PR .= ".2"

PACKAGES += " \
            ${@base_contains("DISTRO_FEATURES", "appletalk", "packagegroup-base-appletalk", "", d)} \
            ${@base_contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-smbfs-client", "", d)} \
            ${@base_contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-smbfs-server", "", d)} \
            ${@base_contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-smbfs-utils", "", d)} \
            ${@base_contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-samba", "", d)} \
            packagegroup-base-vfat \
            "

RDEPENDS_packagegroup-base := "${@oe_filter_out('packagegroup-base-nfs', '${RDEPENDS_packagegroup-base}', d)}"
RDEPENDS_packagegroup-base := "${@oe_filter_out('packagegroup-base-smbfs', '${RDEPENDS_packagegroup-base}', d)}"

RRECOMMENDS_packagegroup-base-vfat += "\
    kernel-module-fat \
    kernel-module-msdos \
    kernel-module-vfat \
    kernel-module-nls-cp437 \
    kernel-module-nls-iso8859-1 \
    kernel-module-nls-iso8859-15 \
    "

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
    kernel-module-psnap"

RDEPENDS_packagegroup-base-appletalk = "\
    netatalk"

RDEPENDS_packagegroup-base-nfs += "\
    nfs-utils \
    nfs-utils-client"
