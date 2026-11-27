SUMMARY = "Graphical recovery and support files for the GTMedia GTX Combo"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/orm/LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit gitpkgv deploy
DEPENDS = "gettext-native"

SRC_URI = "git://github.com/oe-alliance/gtx-recovery.git;protocol=https;branch=master;name=gtx;destsuffix=gtx \
    gitsm://github.com/oe-alliance/OpenRecoveryManager.git;protocol=https;nobranch=1;name=orm;destsuffix=orm"
SRCREV_gtx = "${AUTOREV}"
SRCREV_orm = "fa5c8b14f5fff3e409ffa768b2763db3c6688197"
SRCREV_FORMAT = "gtx+orm"
PV = "git"
PKGV = "git${GITPKGV}"
PR = "r1"
S = "${UNPACKDIR}/gtx"
B = "${UNPACKDIR}/orm"

do_compile() {
    install -m 0644 ${S}/orm-ui.c ${B}/src/gtx-ui.c
    oe_runmake PROGRAM=libgtx-recovery-ui.so SOURCES="src/gtx-ui.c src/ui.c src/i18n.c src/process.c" \
        CFLAGS="${CFLAGS} -Os -fPIC -fvisibility=hidden -std=c11" LIBRARY_CFLAGS="${CFLAGS} -Os -fPIC -fvisibility=hidden -std=gnu11" \
        LDFLAGS="${LDFLAGS} -shared -Wl,-z,defs,-soname,libgtx-recovery-ui.so" libgtx-recovery-ui.so
}
do_install() {
    install -Dm0755 ${B}/libgtx-recovery-ui.so ${D}${libdir}/libgtx-recovery-ui.so
}
FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/libgtx-recovery-ui.so"
do_deploy() {
    install -d ${DEPLOYDIR}/gtx-recovery
    # Image composition and ZIP creation are defined in gtxcombo-images.bbclass.
    install -m 0644 ${S}/*.py ${S}/full-usb-init.sh ${S}/init.sh \
        ${S}/recovery-network.sh ${S}/slot-modules.sh ${S}/python-runtime.list \
        ${S}/emmc-layout.sfdisk ${S}/EMMC-INSTALL.txt ${DEPLOYDIR}/gtx-recovery/
}
addtask deploy after do_compile before do_build
