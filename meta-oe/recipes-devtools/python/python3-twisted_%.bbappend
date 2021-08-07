RDEPENDS:${PN}-core += "${PYTHON_PN}-service-identity ${PYTHON_PN}-typing-extensions"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
           file://fix-writing-after-channel-is-closed.patch \
"

PR = "r6"

FILES:${PN}-dbg += " \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*.egg-info \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*/*/test \
"

FILES:${PN}-src = ""
