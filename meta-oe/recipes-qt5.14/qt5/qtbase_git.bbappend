# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase-git:"

SET_QT_QPA_DEFAULT_PLATFORM ?= "linuxfb"
SET_QT_QPA_EGLFS_INTEGRATION ?= "eglfs_emu"

SRC_URI += " \
	${@bb.utils.contains('MACHINE_FEATURES', 'mali', 'file://0001-eglfs-mali-platform.patch' , '', d)} \
	${@bb.utils.contains('MACHINE_FEATURES', 'v3d-nxpl', 'file://0002-eglfs-brcm-nexus-platform.patch' , '', d)} \
	${@bb.utils.contains('MACHINE_FEATURES', 'v3d-eglfs', 'file://0001-Add-eglfs-brcmstb-support-for-INTEGRITY.patch' , '', d)} \
"

PACKAGECONFIG_GL = " "
PACKAGECONFIG_OPENSSL = "openssl"
PACKAGECONFIG_remove = "tests"
PACKAGECONFIG_append = " eglfs gles2 linuxfb"

SET_QT_QPA_DEFAULT_PLATFORM = "${@bb.utils.contains('MACHINE_FEATURES', 'qteglfs', 'eglfs', '', d)}"
SET_QT_QPA_EGLFS_INTEGRATION = "${@bb.utils.contains('MACHINE_FEATURES', 'mali', 'eglfs_mali', '', d)}"
SET_QT_QPA_EGLFS_INTEGRATION = "${@bb.utils.contains('MACHINE_FEATURES', 'v3d-nxpl', 'eglfs_nxpl', '', d)}"

do_configure_prepend() {
cat >> ${S}/mkspecs/oe-device-extra.pri <<EOF
QT_QPA_DEFAULT_PLATFORM = ${SET_QT_QPA_DEFAULT_PLATFORM}
EGLFS_DEVICE_INTEGRATION = ${SET_QT_QPA_EGLFS_INTEGRATION}
QT_QPA_EGLFS_INTEGRATION = ${SET_QT_QPA_EGLFS_INTEGRATION}
EOF
}

INSANE_SKIP_${PN} += "file-rdeps"
INSANE_SKIP_${PN}-plugins += "file-rdeps"
