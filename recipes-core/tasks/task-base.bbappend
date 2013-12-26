PRINC = "27"
PRINC_dreambox = "3"

PACKAGES += " \
			${@base_contains("DISTRO_FEATURES", "appletalk", "task-base-appletalk", "", d)} \
			${@base_contains("DISTRO_FEATURES", "smbfs", "task-base-smbfs-client", "", d)} \
			"

RDEPENDS_task-base := "${@oe_filter_out('task-base-nfs', '${RDEPENDS_task-base}', d)}"
RDEPENDS_task-base := "${@oe_filter_out('task-base-smbfs', '${RDEPENDS_task-base}', d)}"

RDEPENDS_task-base-smbfs += "\
	cifs \
	sambaserver"

RDEPENDS_task-base-smbfs-client = "samba"

RRRECOMMENDS_task-base-appletalk = "\
	kernel-module-appletalk \
	kernel-module-llc \
	kernel-module-psnap"

RDEPENDS_task-base-appletalk = "\
	netatalk"

RDEPENDS_task-base-nfs += "\
	nfs-utils \
	nfs-utils-client \
	portmap-utils"

