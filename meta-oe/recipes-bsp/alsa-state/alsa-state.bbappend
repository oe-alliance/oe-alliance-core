FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_vuduo2 := "${@bb.utils.contains("MACHINE_FEATURES", "vukodi", "${THISDIR}/${PN}/${MACHINE}:", "${THISDIR}/${PN}:", d)}"
FILESEXTRAPATHS_prepend_vusolo2 := "${@bb.utils.contains("MACHINE_FEATURES", "vukodi", "${THISDIR}/${PN}/${MACHINE}:", "${THISDIR}/${PN}:", d)}"
FILESEXTRAPATHS_prepend_vusolo4k := "${@bb.utils.contains("MACHINE_FEATURES", "vukodi", "${THISDIR}/${PN}/${MACHINE}:", "${THISDIR}/${PN}:", d)}"
FILESEXTRAPATHS_prepend_vusolose := "${@bb.utils.contains("MACHINE_FEATURES", "vukodi", "${THISDIR}/${PN}/${MACHINE}:", "${THISDIR}/${PN}:", d)}"

PR_append = ".1"
