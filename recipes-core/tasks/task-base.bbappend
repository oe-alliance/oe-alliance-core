PRINC = "7"

PACKAGES += " \
			${@base_contains("DISTRO_FEATURES", "appletalk", "task-base-appletalk", "", d)} \
			"

RDEPENDS_task-base := "${@oe_filter_out('task-base-nfs', '${RDEPENDS_task-base}', d)}"
RDEPENDS_task-base := "${@oe_filter_out('task-base-smbfs', '${RDEPENDS_task-base}', d)}"

RDEPENDS_task-base-smbfs += "\
	samba \
	cifs \
	sambaserver"

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

