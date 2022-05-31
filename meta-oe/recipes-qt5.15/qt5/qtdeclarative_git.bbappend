# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

INSANE_SKIP:${PN} += "file-rdeps"
INSANE_SKIP:${PN}-tools += "file-rdeps"
INSANE_SKIP:${PN}-qmlplugins += "file-rdeps"

#### sets PYTHON_BASEVERSION to "2.7"
inherit ${PYTHON_PN}-dir ${PYTHON_PN}native
