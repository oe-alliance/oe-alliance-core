FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR_append = ".1"

INITSCRIPT_PARAMS_${PN}_openbh = "stop 20 ."
