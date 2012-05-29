PRINC = "2"

SRC_URI += "file://mkfs.ubifs-allow-output-file-creation-on-different-device.patch"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}_${PV}:"
