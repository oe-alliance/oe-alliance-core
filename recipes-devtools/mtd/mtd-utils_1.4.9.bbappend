PRINC = "3"

SRC_URI += "\
		${@base_contains("MACHINE_FEATURES", "kernel26-legacy", "file://disable-ubi.patch", \
		"file://mkfs.ubifs-allow-output-file-creation-on-different-device.patch", d)} \
		"


FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}_${PV}:"
