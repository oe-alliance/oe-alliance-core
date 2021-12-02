# Stuff applying to all mgcamd on image
SUMMARY:${PN} = "mgcamd ${PKGV}"
DESCRIPTION:${PN} = "An outdated binary only softcam"

PR="r4.0"

LICENSE = "CLOSED"
require conf/license/license-close.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/enigma2-plugin-softcams-mgcamd:"

SRC_URI = " \
            file://mgcamd_versions/ \
            file://init.mgcamd \
"

require softcam.inc
require softcam-binary-32bit.inc

#CONFFILES:${PN} ?= "/etc/CCcam.cfg"

PACKAGE_ARCH = "${DEFAULTTUNE}"
CAM_ARCH="arm"

do_compile:prepend() {
    cp ${WORKDIR}/init.${CAMNAME} ${S}/init.${CAMNAME}
}

do_install() {
    install -d ${D}/${bindir}
    cp ${S}/../init.mgcamd ${S}/init.mgcamd
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/../mgcamd_versions/mgcamd_${PV}_${CAM_ARCH} ${D}/usr/softcams/${CAMNAME}
}

pkg_postinst:${PN}:prepend () {
	if [ ! -e "$D/lib/ld-linux.so.3" ] && [ -e "$D/lib/ld-linux-armhf.so.3" ]; then
		ln -s ld-linux-armhf.so.3 $D/lib/ld-linux.so.3
	fi
}

RDEPENDS:${PN}_aarch64 = "lib32-glibc lib32-libxcrypt lib32-libcrypto1.0.0 lib32-libgcc lib32-zlib softcam-support"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped arch build-deps ldflags file-rdeps"
