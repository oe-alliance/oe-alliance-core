# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase-git:"

SET_QT_QPA_DEFAULT_PLATFORM ?= "linuxfb"
SET_QT_QPA_EGLFS_INTEGRATION ?= "eglfs_emu"

SRC_URI += " \
	${@bb.utils.contains('MACHINE_FEATURES', 'mali', 'file://0001-eglfs-mali-platform.patch' , '', d)} \
	${@bb.utils.contains('MACHINE_FEATURES', 'v3d-nxpl', 'file://0002-eglfs-brcm-nexus-platform.patch' , '', d)} \
	${@bb.utils.contains('MACHINE_FEATURES', 'v3d-eglfs', 'file://0001-Add-eglfs-brcmstb-support-for-INTEGRITY.patch' , '', d)} \
	${@bb.utils.contains('MACHINE_FEATURES', 'vu-eglfs', 'file://0001-Add-eglfs-libvupl-support-for-INTEGRITY.patch' , '', d)} \
"

DEPENDS_append_class-target = "${@bb.utils.contains('MACHINE_FEATURES', 'vu-eglfs', 'libvupl libgles' , '', d)}"
RDEPENDS_${PN}_append_class-target = "${@bb.utils.contains('MACHINE_FEATURES', 'vu-eglfs', 'libvupl libgles' , '', d)}"

PACKAGECONFIG_GL = " "
PACKAGECONFIG_OPENSSL = "openssl"
PACKAGECONFIG_remove = "tests ${@bb.utils.contains('MACHINE_FEATURES', 'vu-eglfs', 'gl' , '', d)}"
PACKAGECONFIG_append += " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'noopengl', '', ' gles2 eglfs ', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'vu-eglfs', ' accessibility examples ' , '', d)} \
    linuxfb \
"

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
