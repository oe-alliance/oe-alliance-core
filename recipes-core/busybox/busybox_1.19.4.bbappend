PRINC = "18"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	file://mount_single_uuid.patch \
	"