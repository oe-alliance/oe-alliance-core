SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r4"

inherit packagegroup

RCONFLICTS_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
RREPLACES_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

DEPENDS = "openview-feeds"

RDEPENDS_${PN} = "\
    enigma2-skindefault \
    openview-spinner \
    openview-version-info \
    "

RRECOMMENDS_${PN} = "\
    enigma2-plugin-skins-ru_infinity_hd-ov \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-pli-softcamsetup \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-extensions-zaphistorybrowser \
    enigma2-plugin-extensions-gbaspectratioswitch \
    enigma2-plugin-drivers-network-usb-rt3573 \
    enigma2-plugin-drivers-network-usb-rt5572 \
    "

