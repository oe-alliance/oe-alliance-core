FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

inherit insserv

INITSCRIPT_NAMES_${PN} = "networking"
INITSCRIPT_PARAMS = ""
