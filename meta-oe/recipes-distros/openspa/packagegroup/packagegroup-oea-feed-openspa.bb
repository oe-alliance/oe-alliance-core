SUMMARY = "OE-Alliance Feed OpenSPA - distro-specific feed packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r0"

inherit packagegroup

DEPENDS += "packagegroup-oea-feed-core"

RRECOMMENDS:${PN} = "\
    chrony \
    fpclock \
    openspa-display-skins \
    openspa-skins \
    enigma2-skins \
    enigma2-plugin-extensions-atilehd \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-extensions-filecommander \
    curlftpfs-ng \
    cdfs \
    tvheadend \
    dnscrypt-proxy \
    openspa-softcams-meta \
    openssl-old \
    ${@bb.utils.contains_any('TARGET_ARCH', 'arm aarch64', 'sysbench', '', d)} \
    udpxy \
    zerotier \
    ${@bb.utils.contains("MACHINE_FEATURES", "no8188eu", "", "enigma2-plugin-drivers-network-usb-8188eu", d)} \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-openweather \
    enigma2-plugin-extensions-subssupport \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    enigma2-plugin-extensions-openwebif-vxg \
    enigma2-plugin-extensions-bitrateviewer \
    enigma2-plugin-extensions-ardmediathek \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-birthdayreminder \
    enigma2-plugin-extensions-epgrefresh \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-fritzcall \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-netzkino \
    enigma2-plugin-extensions-schiffe \
    enigma2-plugin-extensions-shoutcast \
    enigma2-plugin-extensions-simplerss \
    enigma2-plugin-extensions-srfmediathek \
    enigma2-plugin-extensions-sudoku \
    enigma2-plugin-extensions-tetris \
    enigma2-plugin-extensions-werbezapper \
    enigma2-plugin-extensions-vierg \
    enigma2-plugin-extensions-zdfmediathek \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-extensions-audiorestart \
    enigma2-plugin-systemplugins-networkbrowser \
    enigma2-plugin-extensions-audiosync \
    enigma2-plugin-extensions-babelzapper \
    enigma2-plugin-extensions-cdinfo \
    enigma2-plugin-extensions-epgbackup \
    enigma2-plugin-extensions-epgexport \
    enigma2-plugin-extensions-esame \
    enigma2-plugin-extensions-partnerbox \
    enigma2-plugin-systemplugins-aspectratioswitch \
    enigma2-plugin-systemplugins-automaticcleanup \
    enigma2-plugin-systemplugins-automatictimerlistcleanup \
    enigma2-plugin-systemplugins-autoresolution \
    enigma2-plugin-systemplugins-mphelp \
    enigma2-plugin-systemplugins-toolkit \
    enigma2-plugin-extensions-moviecut \
    enigma2-plugin-extensions-fstabeditor \
    enigma2-plugin-extensions-ftpbrowser \
    enigma2-plugin-extensions-spdyn \
    enigma2-plugin-extensions-infobartunerstate \
    enigma2-plugin-extensions-kiddytimer \
    enigma2-plugin-extensions-letterbox \
    enigma2-plugin-extensions-logomanager \
    enigma2-plugin-extensions-mediadownloader \
    enigma2-plugin-extensions-movietagger \
    enigma2-plugin-extensions-namezap \
    enigma2-plugin-extensions-permanentclock \
    enigma2-plugin-extensions-multirc \
    enigma2-plugin-extensions-reconstructapsc \
    enigma2-plugin-extensions-remotetimer \
    enigma2-plugin-extensions-dvdbackup \
    enigma2-plugin-extensions-seriesplugin \
    enigma2-plugin-extensions-series2folder \
    enigma2-plugin-extensions-showclock \
    enigma2-plugin-extensions-zaphistorybrowser \
    enigma2-plugin-extensions-zapstatistic \
    enigma2-plugin-extensions-webinterface \
    enigma2-plugin-extensions-webadmin \
    enigma2-plugin-extensions-webbouqueteditor \
    enigma2-plugin-extensions-ushare \
    enigma2-plugin-extensions-mediathekviewweb \
    enigma2-plugin-extensions-dreamexplorer \
    enigma2-plugin-extensions-oaweather \
    enigma2-plugin-extensions-footonsat-spa \
    enigma2-plugin-extensions-iptosat \
    enigma2-plugin-extensions-mytube-spa \
    enigma2-plugin-extensions-openspanettest-spa \
    enigma2-plugin-extensions-permanentevent-spa \
    enigma2-plugin-extensions-plutotv-spa \
    enigma2-plugin-extensions-sinriconnect \
    enigma2-plugin-extensions-spzcamd \
    enigma2-plugin-extensions-spzremotechannels \
    enigma2-plugin-extensions-tailscale \
    enigma2-plugin-extensions-weatherplugin-spa \
    enigma2-plugin-extensions-xtraevent-spa \
    enigma2-plugin-systemplugins-spzaddiptv \
    ncdu \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv2", "enigma2-plugin-extensions-dbackup", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "docker", "docker docker-portainer docker-transmission docker-pihole docker-homeassistant", "", d)} \
    "

RRECOMMENDS:${PN}:append:gb800solo = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7325 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7358 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7362 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb73625 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7356 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7252 = "enigma2-plugin-extensions-gbipboxclient"
