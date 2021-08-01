FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR:append = ".2"

INITSCRIPT_PARAMS:${PN}_openbh = "disable"
