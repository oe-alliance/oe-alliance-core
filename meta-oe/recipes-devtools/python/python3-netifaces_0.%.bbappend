FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
        ${@bb.utils.contains("MACHINE_FEATURES", "legacykernel", "file://define-RTNL_FAMILY_MAX.patch", "", d)} \
"

include ${PYTHON_PN}-package-split.inc
