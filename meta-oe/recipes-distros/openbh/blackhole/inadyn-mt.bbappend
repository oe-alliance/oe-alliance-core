FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR_append = ".2"

INITSCRIPT_PARAMS_${PN}_openbh = "disable"
