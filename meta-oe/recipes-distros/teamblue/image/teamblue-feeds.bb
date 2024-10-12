SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"

PV = "${IMAGE_VERSION}"
PR = "r5"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

DEPENDS += "oe-alliance-feeds enigma2-3rdparty-plugins"

RRECOMMENDS:${PN} = "\
    astra-sm \
    curlftpfs \
    dvblast \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-gbipboxclient \
    enigma2-plugin-extensions-oscamsmartcard \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-skins-gb-fhd \
    enigma2-plugin-skins-gbuniverse \
    enigma2-plugin-skins-pax-fhd \
    enigma2-plugin-skins-pax \
    enigma2-plugin-skins-teambluehd \
    enigma2-plugin-skins-pli-hd \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    enigma2-plugin-systemplugins-terrestrialscan \
    enigma2-skins \
    libbluray \
    libudfread \
    oe-alliance-skins \
    openssl-old \
    v4l-utils \
    "
