SUMMARY = "GigaBlue CFE AddOn"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openMips"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc
inherit deploy

PR = "r32"

S = "${WORKDIR}"

SRC_URI_gb800solo = " file://burn.bat"
SRC_URI_gb800ue = " file://gb800ue/lcdwaitkey.bin file://gb800ue/lcdwarning.bin"
SRC_URI_gbquad = " file://gbquad/lcdwaitkey.bin file://gbquad/lcdwarning.bin file://gbquad/warning.bin"
SRC_URI_gbquadplus = " file://gbquadplus/lcdwaitkey.bin file://gbquadplus/lcdwarning.bin file://gbquadplus/warning.bin"
SRC_URI_gb800ueplus = " file://gb800ueplus/lcdwaitkey.bin file://gb800ueplus/lcdwarning.bin file://gb800ueplus/warning.bin"
SRC_URI_gb800seplus = " file://gb800seplus/warning.bin"
SRC_URI_gbipbox = " file://gbipbox/warning.bin"
SRC_URI_gbultraue = " file://gbultraue/lcdwaitkey.bin file://gbultraue/lcdwarning.bin file://gbultraue/warning.bin"
SRC_URI_gbultrase = " file://gbultrase/warning.bin"
SRC_URI_gbx1 = " file://gbx1/warning.bin"
SRC_URI_gbx3 = " file://gbx3/warning.bin"

ALLOW_EMPTY_${PN} = "1"

do_deploy() {
    if [ -e burn.bat ]; then
        install -m 0644 burn.bat ${DEPLOYDIR}/burn.bat
    fi
    if [ -e ${MACHINE}/lcdwaitkey.bin ]; then
        install -m 0644 ${MACHINE}/lcdwaitkey.bin ${DEPLOYDIR}/lcdwaitkey.bin
        install -m 0644 ${MACHINE}/lcdwarning.bin ${DEPLOYDIR}/lcdwarning.bin
    fi
    if [ -e ${MACHINE}/warning.bin ]; then
        install -m 0644 ${MACHINE}/warning.bin ${DEPLOYDIR}/warning.bin
    fi
}

addtask deploy before do_build after do_install
