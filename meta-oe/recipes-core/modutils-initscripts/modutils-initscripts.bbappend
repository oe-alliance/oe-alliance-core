
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://0001-ignore_modversions.patch \
           "

S = "${WORKDIR}/src"

python do_unpack:append() {
    import shutil
    shutil.copy("modutils.sh", "src/modutils.sh")
}

INITSCRIPT_NAME = "modutils.sh"
INITSCRIPT_PARAMS = "start 04 S ."
