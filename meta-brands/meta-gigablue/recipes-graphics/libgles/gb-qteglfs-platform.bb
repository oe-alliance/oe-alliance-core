DESCRIPTION = "QT EGLFS for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r2"

SRCDATE = "20180904_r0"

QEGLFS = "qt5/plugins/egldeviceintegrations"

SRC_URI = "http://source.mynonpublic.com/gigablue/v3ddriver/gb-qteglfs-platform-${SRCDATE}.tar.gz"

SRC_URI[md5sum] = "8191d39d5b1113fe6d2b4e1ea43e7ab6"
SRC_URI[sha256sum] = "3df834a4e38de510d349d290bfa143b2f7071185f1f2ca3eafc40e18a24843f7"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_compile() {
}

do_install() {
	install -d ${D}${libdir}/${QEGLFS}
	install -m 0755 libqeglfs-brcm-nx-integration.so ${D}${libdir}/${QEGLFS}/
}

do_package_qa() {
}

FILES_${PN} = "/usr/lib/qt5/plugins/egldeviceintegrations/libqeglfs-brcm-nx-integration.so"

INSANE_SKIP_${PN} += "already-stripped dev-so"
