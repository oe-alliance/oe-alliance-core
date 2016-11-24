FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_vuduo2 := "${@base_contains("MACHINE_FEATURES", "vukodi", "${THISDIR}/${PN}/${MACHINE}:", "${THISDIR}/${PN}:", d)}"
FILESEXTRAPATHS_prepend_vusolo2 := "${@base_contains("MACHINE_FEATURES", "vukodi", "${THISDIR}/${PN}/${MACHINE}:", "${THISDIR}/${PN}:", d)}"
FILESEXTRAPATHS_prepend_vusolo4k := "${@base_contains("MACHINE_FEATURES", "vukodi", "${THISDIR}/${PN}/${MACHINE}:", "${THISDIR}/${PN}:", d)}"
FILESEXTRAPATHS_prepend_vusolose := "${@base_contains("MACHINE_FEATURES", "vukodi", "${THISDIR}/${PN}/${MACHINE}:", "${THISDIR}/${PN}:", d)}"
FILESEXTRAPATHS_prepend_vuuno4k := "${@base_contains("MACHINE_FEATURES", "vukodi", "${THISDIR}/${PN}/${MACHINE}:", "${THISDIR}/${PN}:", d)}"
FILESEXTRAPATHS_prepend_vuultimo4k := "${@base_contains("MACHINE_FEATURES", "vukodi", "${THISDIR}/${PN}/${MACHINE}:", "${THISDIR}/${PN}:", d)}"


PR_append = ".1"
