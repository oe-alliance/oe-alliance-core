SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY_${PN} = "1"

PV = "7.0"
PR = "r0"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    enigma2-pliplugins \
    enigma2-display-skins \
    openspa-skins \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-crossepg \
    curlftpfs \
    cdfs \
    openssl-old \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "", "exteplayer3 enigma2-plugin-extensions-serviceapp", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "dvb-apps" , "", d)} \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-blurayplayer \
    ${@bb.utils.contains("MACHINE_BRAND", "AZBOX", "enigma2-plugin-extensions-azplay enigma2-plugin-extensions-aziptv", "", d)} \
    "
