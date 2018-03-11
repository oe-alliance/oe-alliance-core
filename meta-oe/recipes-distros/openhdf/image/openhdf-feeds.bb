SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r30"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    enigma2-skins \
    oe-alliance-skins \
    cdfs \
    enigma2-display-skins \
    enigma2-plugin-extensions-project-valerie \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-skins-xionhdf \
    enigma2-plugin-skins-army-mod \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    enigma2-plugin-extensions-subssupport \
    enigma2-plugin-skins-nblack51-hdfmod \
    enigma2-plugin-skins-ax-blue-fhd-4hdf \
    enigma2-plugin-skins-blue-line-oct-4hdf \
    "

RRECOMMENDS_${PN}_append_gb800solo = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7325 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7358 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7362 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb73625 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7356 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7252 = "enigma2-plugin-extensions-gbipboxclient"