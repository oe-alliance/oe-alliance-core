FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "2"

SRC_URI += "file://minidlna.conf file://init"

INITSCRIPT_NAME = "minidlna"
INITSCRIPT_PARAMS = "defaults 20"

inherit update-rc.d
