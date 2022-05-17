
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "file://modutils.sh;destsuffix=src\
           file://0001-ignore_modversions.patch \
           "

S = "${WORKDIR}/src"

INITSCRIPT_NAME = "modutils.sh"
INITSCRIPT_PARAMS = "start 04 S ."
