FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "${@bb.utils.contains("MACHINE_FEATURES", "modversion", "file://0001-ignore_modversions.patch", "", d)} \
           "

INITSCRIPT_PARAMS = "start 04 S"

do_install_append() {
        if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
                rm ${D}${sysconfdir}/init.d/modutils.sh
        fi
}
