SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "8.3"
PR = "r0"

inherit packagegroup

DEPENDS += "oe-alliance-feeds enigma2-3rdparty-plugins"

RRECOMMENDS:${PN} = "\
    chrony \
    fpclock \
    openspa-skins \
    openspa-display-skins \
    enigma2-skins \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-extensions-filecommander \
    curlftpfs \
    cdfs \
    tvheadend \
    openssl-old \
    ${@bb.utils.contains_any('TARGET_ARCH', 'arm aarch64', 'sysbench', '', d)} \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-specialjump \
    enigma2-plugin-extensions-gbipboxclient \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-sdgradio \
    enigma2-plugin-extensions-subssupport \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    mediainfo \
    ncdu \
    "

RRECOMMENDS:${PN}:append:gb800solo = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7325 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7358 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7362 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb73625 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7356 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7252 = " enigma2-plugin-extensions-gbipboxclient"
