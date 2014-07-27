SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r11"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    oe-alliance-skins \
    enigma2-skins \
    enigma2-plugin-extensions-gb-multiquickbutton \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-systemplugins-autobouquetsmaker\
    "

RRECOMMENDS_${PN}_append_gbipbox = "enigma2-plugin-systemplugins-gbipboxclient"
RRECOMMENDS_${PN}_append_gb800seplus = "enigma2-plugin-systemplugins-gbipboxclient"
RRECOMMENDS_${PN}_append_gb800ueplus = "enigma2-plugin-systemplugins-gbipboxclient"
RRECOMMENDS_${PN}_append_gbquad = "gbipboxserver"
RRECOMMENDS_${PN}_append_gbquadplus = "gbipboxserver"
