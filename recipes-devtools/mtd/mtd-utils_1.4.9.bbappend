PRINC = "3"

SRC_URI_append_dm800= " \
	file://disable-ubi.patch \
"


FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}_${PV}:"
