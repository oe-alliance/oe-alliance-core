PACKAGES += " \
            ${@base_contains("DISTRO_FEATURES", "appletalk", "packagegroup-base-appletalk", "", d)} \
            ${@base_contains("DISTRO_FEATURES", "smbfs", "packagegroup-base-smbfs-client", "", d)} \
            "

RDEPENDS_packagegroup-base := "${@oe_filter_out('packagegroup-base-nfs', '${RDEPENDS_packagegroup-base}', d)}"
RDEPENDS_packagegroup-base := "${@oe_filter_out('packagegroup-base-smbfs', '${RDEPENDS_packagegroup-base}', d)}"

RDEPENDS_packagegroup-base-smbfs += "\
    cifs \
    sambaserver"

RDEPENDS_packagegroup-base-smbfs-client = "samba"

RRRECOMMENDS_packagegroup-base-appletalk = "\
    kernel-module-appletalk \
    kernel-module-llc \
    kernel-module-psnap"

RDEPENDS_packagegroup-base-appletalk = "\
    netatalk"

RDEPENDS_packagegroup-base-nfs += "\
    nfs-utils \
    nfs-utils-client \
    portmap \
    portmap-utils"
