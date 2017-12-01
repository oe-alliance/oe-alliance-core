FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "${@bb.utils.contains("MACHINE_FEATURES", "modversion", "file://0001-ignore_modversions.patch", "", d)} \
           "

INITSCRIPT_NAME = "modutils"
INITSCRIPT_PARAMS = "${@bb.utils.contains('DISTRO_FEATURES','systemd', 'start 01 3 .', 'start 04 S .', d)}"

do_install_append() {
        mv ${D}${sysconfdir}/init.d/modutils.sh ${D}${sysconfdir}/init.d/modutils
        if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
                if ${@bb.utils.contains('MACHINE_FEATURES','modversion','true','false',d)}; then
                        :
                else
                        rm ${D}${sysconfdir}/init.d/modutils
                fi
        fi
}

pkg_postinst_${PN} () {
}
