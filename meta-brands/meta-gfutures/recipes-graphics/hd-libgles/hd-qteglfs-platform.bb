DESCRIPTION = "QT EGLFS for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r0"

QTEGLFS = "qt5/plugins/egldeviceintegrations"

SRC_URI = "file://libqeglfs-nxpl-integration.so"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_compile() {
}

do_install() {
	install -d ${D}${libdir}/${QEGLFS}
	install -m 0755 libqeglfs-nxpl-integration.so ${D}${libdir}/${QTEGLFS}/
}

do_package_qa() {
}

FILES_${PN} = "/usr/lib/qt5/plugins/egldeviceintegrations/libqeglfs-nxpl-integration.so"

INSANE_SKIP_${PN} += "already-stripped dev-so"