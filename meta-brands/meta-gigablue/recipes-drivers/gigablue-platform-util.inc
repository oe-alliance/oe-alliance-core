SUMMARY = "Platform Util drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

PV = "${KV}+${SRCDATE}"
PR = "r7"

# V3D owns the Nexus libraries copied to /usr/lib by the platform init script.
# Keep the matching provider for Quad/UE 4K and X3 4K when optional EGL
# applications are removed, including framebuffer-only Enigma2 images.
RDEPENDS:${PN} += "gb-v3ddriver-${MACHINE_DRIVER}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

SRC_URI = " \
    https://source.mynonpublic.com/gigablue/drivers/platform-util-${MACHINE}-${KV}-${SRCDATE}.zip \
    file://${INITSCRIPT_NAME}.sysvinit \
"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}/home/root/platform
    install -m 0755 ${UNPACKDIR}/platform/* ${D}/home/root/platform
    install -d ${D}/etc/init.d
    install -m 0755 ${UNPACKDIR}/${INITSCRIPT_NAME}.sysvinit ${D}/etc/init.d/${INITSCRIPT_NAME}
}

do_package_qa() {
}
do_populate_sysroot() {
}

FILES:${PN} = "/home/root/platform /etc/init.d"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 65 S . stop 90 0 ."
INITSCRIPT_NAME = "gigablue-platform-util"

INSANE_SKIP:${PN} += "already-stripped"
