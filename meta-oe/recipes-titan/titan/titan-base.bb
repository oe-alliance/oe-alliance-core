SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES:CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

#PV = "1.0"
#PR = "r16"

inherit packagegroup


DEPENDS = "\
	titan-gmediarender \
	titan-netsurf \
   	titan-plugins \
   	titan-skins \
    titan-screensaver \
    titan-picons \
    titan-settings \
    titan-bootlogos \
    titan-fonts \
    titan-lcdsamsungskins \
    titan-lcdpearlskins \
    titan-player \
    titan-keymaps \
    titan-emus \
    "
