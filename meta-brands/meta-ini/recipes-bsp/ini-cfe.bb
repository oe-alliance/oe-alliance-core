SUMMARY = "INI flashing bootlogo"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "INI"

require conf/license/license-gplv2.inc
inherit deploy

PV = "1.0"
PR = "r3"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

SRC_URI = "file://cfe.bmp file://finished.bmp file://imageversion.bmp file://kernel.bmp file://rootfs.bmp file://splash.bmp"

ALLOW_EMPTY:${PN} = "1"

do_deploy() {
    if [ -e cfe.bmp ]; then
        install -m 0644 cfe.bmp ${DEPLOYDIR}/cfe.bmp
    fi
    if [ -e finished.bmp ]; then
        install -m 0644 finished.bmp ${DEPLOYDIR}/finished.bmp
    fi
    if [ -e imageversion.bmp ]; then
        install -m 0644 imageversion.bmp ${DEPLOYDIR}/imageversion.bmp
    fi
    if [ -e kernel.bmp ]; then
        install -m 0644 kernel.bmp ${DEPLOYDIR}/kernel.bmp
    fi
    if [ -e rootfs.bmp ]; then
        install -m 0644 rootfs.bmp ${DEPLOYDIR}/rootfs.bmp
    fi
    if [ -e splash.bmp ]; then
        install -m 0644 splash.bmp ${DEPLOYDIR}/splash.bmp
    fi
}

addtask deploy before do_package after do_install
