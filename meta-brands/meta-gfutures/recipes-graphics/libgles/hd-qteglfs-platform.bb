DESCRIPTION = "QT EGLFS for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r1"

QEGLFS = "qt5/plugins/egldeviceintegrations"

SRC_URI = "file://libqeglfs-nxpl-integration.so"

SRC_URI[md5sum] = "10d17175af9fe9f7d79a15c3da1ea830"
SRC_URI[sha256sum] = "3672ae49c8e19a5e6f552851acb0d54376b9c445be2c92808b2c4b2274c86678"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_compile() {
}

do_install() {
	install -d ${D}${libdir}/${QEGLFS}
	install -m 0755 libqeglfs-nxpl-integration.so ${D}${libdir}/${QEGLFS}/
}

do_package_qa() {
}

FILES_${PN} = "/usr/lib/qt5/plugins/egldeviceintegrations/libqeglfs-nxpl-integration.so"

INSANE_SKIP_${PN} += "already-stripped dev-so"