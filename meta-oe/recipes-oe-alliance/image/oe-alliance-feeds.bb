SUMMARY = "Merge machine and distro options to create a oe-alliance enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r16"

DEPENDS = "enigma2-plugin-drivers-usbserial enigma2-plugin-systemplugins-radiotimesemulator enigma2-plugin-systemplugins-hrtunerproxy"

#not python3 ready
#    ${PYTHON_PN}-cocy
#    enigma2-plugin-extensions-moviearchiver


RDEPENDS:${PN} = " \
    packagegroup-openplugins \
    bootlogos-enigma2-meta \
    dvb-usb-drivers-meta \
    network-usb-drivers-meta \
    extrahw-drivers-meta \
    picons-enigma2-meta \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "dreamplex-skins-meta", "", d)} \
    meta-enigma2-dvdburn \
    enigma2-plugins \
    enigma2-plugin-drivers-ntfs-3g \
    enigma2-plugin-drivers-exfat \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-tuxcom \
    enigma2-plugin-drivers-iptables \
    enigma2-plugin-extensions-enigmalight \
    enigma2-plugin-extensions-mediatomb \
    enigma2-plugin-extensions-dreamplex \
    enigma2-plugin-extensions-youtube \
    enigma2-plugin-extensions-autobouquets \
    enigma2-plugin-extensions-e2m3u2bouquet \
    enigma2-plugin-extensions-e2piconizer \
    enigma2-plugin-extensions-et-portal \
    enigma2-plugin-extensions-jedimakerxtream \
    enigma2-plugin-extensions-jediepgxtream \
    enigma2-plugin-extensions-lamedbmerger \
    enigma2-plugin-extensions-serienrecorder \
    enigma2-plugin-systemplugins-misplslcnscan \
    enigma2-plugin-systemplugins-radiotimesemulator \
    enigma2-plugin-extensions-subssupport \
    enigma2-plugin-extensions-xstreamity \
    enigma2-plugin-extensions-yahooweather \
    enigma2-plugin-systemplugins-terrestrialscan \
    enigma2-plugin-systemplugins-satscanlcn \
    exteplayer3 \
    eplayer5 \
    enigma2-plugin-systemplugins-serviceapp \
    enigma2-plugin-extensions-moviemanager \
    enigma2-plugin-systemplugins-hrtunerproxy  \
    enigma2-plugin-extensions-xmodem  \
    enigma2-plugin-extensions-e2iplayer \
    enigma2-plugin-extensions-e2iplayer-deps \
    ${@bb.utils.contains("MACHINE_FEATURES", "legacykernel", "" , "gdb v4l-utils evtest strace", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "omb", "enigma2-plugin-extensions-openmultiboot openmultiboot", "", d)} \
    ${@bb.utils.contains_any("MACHINE_FEATURES", "kodi20 kodi19", "kodi-addons-meta enigma2-plugin-extensions-kodi", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "qthbbtv", "enigma2-plugin-extensions-hbbtv-qt", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "qtstalker", "enigma2-plugin-extensions-stalker-qt", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "hbbtv-browser-webkit", " enigma2-plugin-extensions-hbbtv-webkit", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gb-qthbbtv", "enigma2-plugin-extensions-hbbtv-gb", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "chromiumos", "enigma2-plugin-extensions-chromium", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "vuglesdemo", "enigma2-plugin-extensions-libvupldemo", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "vustalker", "enigma2-plugin-extensions-stalkerclient", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "wifi-direct", "wds", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvd", "bdremux replex mjpegtools dvdauthor dvd+rw-tools genisoimage cdfs cdtextinfo enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdburn enigma2-plugin-extensions-dvdplayer ${GST_BASE_DVD}", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "openhbbtv", "enigma2-plugin-extensions-openhbbtvbrowser", "", d)} \
    autofs \
    autossh \
    ${@bb.utils.contains("DISTRO_FEATURES", "directfb", "avahi-ui", "", d)} \
    astra-sm \
    binutils \
    chrpath \
    ctorrent \
    cups \
    davfs2 \
    djmount \
    dosfstools \
    dvblast \
    dvbsnoop \
    dvdfs \
    ${@bb.utils.contains('MACHINE', 'cube', '' , 'edid-decode', d)} \
    exfat-utils \
    fuse-exfat \
    gptfdisk \
    hddtemp \
    hdparm \
    htop \
    idle3-tools \
    inadyn-mt \
    iperf3 \
    joe \
    lighttpd \
    livestreamersrv \
    streamlinksrv \
    streamlink \
    llmnr-query \
    mc \
    mergerfs \
    minidlna \
    minisatip \
    mpd \
    mtd-utils \
    nano \
    net-snmp \
    nlohmann-json \
    nss \
    ntfs-3g \
    ntp \
    ${@bb.utils.contains("DEFAULTTUNE", "sh4", "" , "nodejs dvb-apps satpi", d)} \
    odhcp6c \
    ofgwrite \
    openresolv \
    openssh \
    openvpn \
    easy-rsa \
    p7zip \
    packagegroup-base-samba \
    parted \
    patchelf \
    pngquant \
    pcsc-lite \
    pcsc-tools \
    procps \
    pyload \
    ${PYTHON_PN}-aiohttp \
    ${PYTHON_PN}-attr \
    ${PYTHON_PN}-attrs \
    ${PYTHON_PN}-cattrs \
    ${PYTHON_PN}-autobahn \
    ${PYTHON_PN}-bluetool \
    ${PYTHON_PN}-circuits \
    ${PYTHON_PN}-circuits-bricks \
    ${PYTHON_PN}-cfscrape \
    ${PYTHON_PN}-future \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-futures", "${PYTHON_PN}-futures3", d)} \
    ${PYTHON_PN}-fuzzywuzzy \
    ${PYTHON_PN}-ipaddress \
    ${PYTHON_PN}-js2py \
    ${PYTHON_PN}-mechanize \
    ${PYTHON_PN}-netifaces \
    ${PYTHON_PN}-pexpect \
    ${PYTHON_PN}-psutil \
    ${PYTHON_PN}-pyasn1-modules \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-requests-cache \
    ${PYTHON_PN}-service-identity \
    ${PYTHON_PN}-ujson \
    ${PYTHON_PN}-url-normalize \
    ${PYTHON_PN}-singledispatch \
    ${PYTHON_PN}-levenshtein \
    ${PYTHON_PN}-soco \
    ${PYTHON_PN}-pyexecjs \
    ${PYTHON_PN}-scrapy \
    ${@bb.utils.contains("DEFAULTTUNE", "sh4", "" , "rclone zerotier", d)} \
    rapidxml \
    rsync \
    rtorrent \
    ${@bb.utils.contains("PYTHON_PN", "python", "sabnzbd", "sabnzbd3", d)} \
    screen \
    smartmontools \
    smbnetfs \
    sshpass \
    streamproxy \
    strongswan \
    tcpdump \
    tmux \
    transmission \
    ushare \
    vim \
    wakelan \
    ${@bb.utils.contains_any("MACHINE", "cube spark spark7162 dm900 dm920 vuduo2 vusolose vusolo2 vuzero vuuno vuduo vuultimo vusolo inihde2 jj7362 odinm9 et9x00 et6x00 et5x00 dags7356 dags7335 inihdx inihde inihdp vg5000 vg2000 vg1000 ew7356 ew7358 ew7362 ixussone ixusszero blackbox7405 dm520 dm8000 dm7020hd dm7020hdv2 dm800sev2 dm500hdv2 dm7080 dm820 yh7362 yh62tc gb800solo gb7325 ch62lc", "" , "wireguard-tools", d)} \
    wireless-tools \
    zeroconf \
    "

GST_BASE_DVD = "\
    gstreamer1.0-plugins-bad-videoparsersbad \
    gstreamer1.0-plugins-bad-mpegtsmux \
"

RRECOMMENDS:${PN}:append:vuuno = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS:${PN}:append:vuultimo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS:${PN}:append:vusolo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS:${PN}:append:vusolo2 = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS:${PN}:append:vuduo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS:${PN}:append:vuduo2 = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS:${PN}:append:vuzero = "enigma2-plugin-extensions-hbbtv"

