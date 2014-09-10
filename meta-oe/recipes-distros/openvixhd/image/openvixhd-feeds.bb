SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "2.3"
PR = "r4"

RRECOMMENDS_${PN} = "\
    oe-alliance-skins \
    enigma2-display-skins \
    openvixhd-softcams-meta \
	openvixhd-picons-meta \
    "

RRECOMMENDS_${PN}_append_gbipbox = "enigma2-plugin-systemplugins-gbipboxclient"
RRECOMMENDS_${PN}_append_gb800seplus = "enigma2-plugin-systemplugins-gbipboxclient"
RRECOMMENDS_${PN}_append_gb800ueplus = "enigma2-plugin-systemplugins-gbipboxclient"
RRECOMMENDS_${PN}_append_gbquad = "gbipboxserver"
RRECOMMENDS_${PN}_append_gbquadplus = "gbipboxserver"