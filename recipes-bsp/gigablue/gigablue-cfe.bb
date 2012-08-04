DESCRIPTION = "GigaBlue GB800XX CFE AddOn"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openMips"

require conf/license/license-gplv2.inc

PR = "r9"

S = "${WORKDIR}"

SRC_URI_gb800se = " file://burn.bat"
SRC_URI_gb800ue = " file://prog.bmp file://conf.bmp"
SRC_URI_gb800solo = " file://burn.bat"
SRC_URI_gbquad = " file://imageversion.bin file://lcdimageversion.bin file://lcdwarning.bin"

ALLOW_EMPTY_${PN} = "1"

do_install_append_gb800se() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/burn.bat ${DEPLOY_DIR_IMAGE}/burn.bat
}
do_install_append_gb800ue() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/prog.bmp ${DEPLOY_DIR_IMAGE}/prog.bmp
	install -m 0755 ${S}/conf.bmp ${DEPLOY_DIR_IMAGE}/conf.bmp
}
do_install_append_gb800solo() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/burn.bat ${DEPLOY_DIR_IMAGE}/burn.bat
}

do_install_append_gbquad() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/prog.bmp ${DEPLOY_DIR_IMAGE}/imageversion.bin
	install -m 0755 ${S}/conf.bmp ${DEPLOY_DIR_IMAGE}/lcdimageversion.bin
	install -m 0755 ${S}/conf.bmp ${DEPLOY_DIR_IMAGE}/lcdwarning.bin
}
