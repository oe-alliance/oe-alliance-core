SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "7.5"
PR = "r0"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    openspa-skins \
    openspa-display-skins \
    enigma2-skins \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-devicemanager \
    enigma2-plugin-extensions-filecommander \
    curlftpfs \
    cdfs \
    openssl-old \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-subssupport \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    ${@bb.utils.contains("MACHINE_BRAND", "AZBOX", "enigma2-plugin-extensions-azplay enigma2-plugin-extensions-aziptv", "", d)} \
    "

RRECOMMENDS_${PN}_append_gb800solo = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7325 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7358 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7362 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb73625 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7356 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7252 = "enigma2-plugin-extensions-gbipboxclient"
