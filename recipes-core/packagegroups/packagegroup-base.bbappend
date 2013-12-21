PRINC = "4"

PACKAGES += " \
            ${@base_contains("DISTRO_FEATURES", "appletalk", "packagegroup-appletalk", "", d)} \
            ${@base_contains("DISTRO_FEATURES", "smbfs", "packagegroup-smbfs-client", "", d)} \
            "

RDEPENDS_packagegroup-base := "${@oe_filter_out('packagegroup-base-nfs', '${RDEPENDS_packagegroup-base}', d)}"
RDEPENDS_packagegroup-base := "${@oe_filter_out('packagegroup-base-smbfs', '${RDEPENDS_packagegroup-base}', d)}"

RDEPENDS_packagegroup-smbfs += "\
    cifs \
    sambaserver"

RDEPENDS_packagegroup-smbfs-client = "samba"

RRRECOMMENDS_packagegroup-appletalk = "\
    kernel-module-appletalk \
    kernel-module-llc \
    kernel-module-psnap"

RDEPENDS_packagegroup-appletalk = "\
    netatalk"

RDEPENDS_packagegroup-nfs += "\
    nfs-utils \
    nfs-utils-client \
    portmap-utils"

