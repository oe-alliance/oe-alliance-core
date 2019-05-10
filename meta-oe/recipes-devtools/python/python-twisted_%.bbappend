RDEPENDS_${PN}-core += "${PYTHON_PN}-contextlib  ${PYTHON_PN}-service-identity"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-writing-after-channel-is-closed.patch"

PR = "r2"

FILES_${PN}-dbg += " \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*.egg-info \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*/*/test \
"
