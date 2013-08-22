PRINC = "6"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}_${PV}:"

SRC_URI += " \
	file://mkfs.ubifs-allow-output-file-creation-on-different-device.patch \
	"

SRC_URI_append_dm800= " \
	file://disable-ubi.patch \
"
