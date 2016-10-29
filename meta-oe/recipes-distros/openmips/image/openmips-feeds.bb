SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "${IMAGE_VERSION}"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    astra-sm \
    curlftpfs \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "dvb-apps" , "", d)} \
    dvblast \
    enigma2-plugin-skins-gb-fhd \
    enigma2-plugin-skins-pli-hd \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-gbipboxclient \
    enigma2-plugin-extensions-oscamsmartcard \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-autobouquetsmaker\
    enigma2-skins \
    enigma2-plugin-skins-pax-fhd \
    libbluray \
    libudfread \
    monit \
    oe-alliance-skins \
    openssl-old \
    v4l-utils \
    "
