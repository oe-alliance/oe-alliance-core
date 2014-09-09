SUMMARY = "mgcamd ${PV} softcam"
LICENSE = "CLOSED"

RDEPENDS_${PN} = "libcrypto-compat-0.9.7"

PR = "r2"

RREPLACES_${PN} += "enigma2-plugin-softcams-mgcamd"
RCONFLICTS_${PN} += "enigma2-plugin-softcams-mgcamd"

PACKAGES = "enigma2-plugin-softcams-mgcamd135a"

PROVIDES += "openvixhd-softcams-mgcamd135a"
RPROVIDES_enigma2-plugin-softcams-mgcamd135a += "openvixhd-softcams-mgcamd135a"

SRC_URI = "http://enigma2.world-of-satellite.com/git-extras/mgcamd-1.35a.zip"

S = "${WORKDIR}/mgcamd-1.35a"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/mgcamd.mipsel ${D}/usr/softcams/mgcamd.${PV}
}

SRC_URI[md5sum] = "c23a69c16fa79cd5a284da7f531585d6"
SRC_URI[sha256sum] = "339e564db48b701be002d047189ac658d2021673bbc235005dec24f26abac39e"

FILES_enigma2-plugin-softcams-mgcamd135a = "/usr"