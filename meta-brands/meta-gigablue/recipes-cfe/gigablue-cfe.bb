SUMMARY = "GigaBlue GB800XX CFE AddOn"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openMips"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc
inherit deploy

PR = "r28"

S = "${WORKDIR}"

SRC_URI_gb800se = " file://burn.bat"
SRC_URI_gb800solo = " file://burn.bat"
SRC_URI_gb800ue = " file://gb800ue/lcdwaitkey.bin file://gb800ue/lcdwarning.bin"
SRC_URI_gbquad = " file://gbquad/lcdwaitkey.bin file://gbquad/lcdwarning.bin"
SRC_URI_gbquadplus = " file://gbquadplus/lcdwaitkey.bin file://gbquadplus/lcdwarning.bin"
SRC_URI_gb800ueplus = " file://gb800ueplus/lcdwaitkey.bin file://gb800ueplus/lcdwarning.bin"

ALLOW_EMPTY_${PN} = "1"

do_deploy() {
    if [ -e burn.bat ]; then
        install -m 0644 burn.bat ${DEPLOYDIR}/burn.bat
    fi
    if [ -e ${MACHINE}/lcdwaitkey.bin ]; then
        install -m 0644 ${MACHINE}/lcdwaitkey.bin ${DEPLOYDIR}/lcdwaitkey.bin
        install -m 0644 ${MACHINE}/lcdwarning.bin ${DEPLOYDIR}/lcdwarning.bin
    fi
}

addtask deploy before do_build after do_install
