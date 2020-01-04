RDEPENDS_${PN}-core += "${PYTHON_PN}-contextlib  ${PYTHON_PN}-service-identity"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
           file://fix-writing-after-channel-is-closed.patch \
           file://twisted-17.9.0-python-27-utf-8-fix.patch \
           file://0001-Revert-Prevent-CRLF-injections-described-in-CVE-2019.patch \
"

PR = "r3"

FILES_${PN}-dbg += " \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*.egg-info \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*/*/test \
"
