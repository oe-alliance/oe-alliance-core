PRINC = "3"

SRC_URI += "\
		${@base_contains("MACHINE_FEATURES", "kernel26-legacy", "file://disable-ubi.patch", "", d)} \
		"


FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}_${PV}:"
