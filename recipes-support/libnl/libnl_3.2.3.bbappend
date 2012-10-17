PRINC = "2"

SRC_URI_append_dm800 = " file://tc_ematch-header-files.patch"
SRC_URI_append_ebox5000 = " file://tc_ematch-header-files.patch"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}_${PV}:"


