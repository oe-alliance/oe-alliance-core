SUMMARY = "Merge machine and distro options to create a oe-alliance enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r22"

DEPENDS = "enigma2-plugin-drivers-usbserial enigma2-plugin-systemplugins-radiotimesemulator enigma2-plugin-systemplugins-hrtunerproxy"

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
    enigma2-plugin-extensions-enigmawelt \
    enigma2-plugin-extensions-mediatomb \
    enigma2-plugin-extensions-dreamplex \
    enigma2-plugin-extensions-youtube \
    enigma2-plugin-extensions-autobouquets \
    enigma2-plugin-extensions-bouquetcleanup \
    enigma2-plugin-extensions-e2m3u2bouquet \
    enigma2-plugin-extensions-e2piconizer \
    enigma2-plugin-extensions-et-portal \
    enigma2-plugin-extensions-gerbera \
    enigma2-plugin-extensions-bouquetmakerxtream \
    enigma2-plugin-extensions-jediepgxtream \
    enigma2-plugin-extensions-lamedbmerger \
    enigma2-plugin-extensions-mediaplayer2 \
    enigma2-plugin-extensions-serienrecorder \
    enigma2-plugin-systemplugins-misplslcnscan \
    enigma2-plugin-extensions-planerfs \
    enigma2-plugin-extensions-picturecenterfs \
    enigma2-plugin-systemplugins-radiotimesemulator \
    enigma2-plugin-extensions-subssupport \
    enigma2-plugin-extensions-xklass \
    enigma2-plugin-extensions-xstreamity \
    enigma2-plugin-extensions-yampmusicplayer \
    enigma2-plugin-systemplugins-satscanlcn \
    exteplayer3 \
    eplayer5 \
    enigma2-plugin-systemplugins-serviceapp \
    enigma2-plugin-extensions-moviemanager \
    enigma2-plugin-systemplugins-hrtunerproxy  \
    enigma2-plugin-extensions-xmodem  \
    enigma2-plugin-extensions-e2iplayer \
    enigma2-plugin-extensions-e2iplayer-deps \
    enigma2-plugin-extensions-estalker \
    ${@bb.utils.contains("MACHINE_FEATURES", "legacykernel", "" , "gdb v4l-utils evtest strace", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "omb", "enigma2-plugin-extensions-openmultiboot openmultiboot", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "hbbtv-browser-webkit", " enigma2-plugin-extensions-hbbtv-webkit", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "chromiumos", "enigma2-plugin-extensions-chromium", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "vuglesdemo", "enigma2-plugin-extensions-libvupldemo", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "wifi-direct", "wds", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvd", "bdremux replex mjpegtools dvdauthor dvd+rw-tools genisoimage cdfs enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdburn enigma2-plugin-extensions-dvdplayer ${GST_BASE_DVD}", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "vubluetooth", "enigma2-plugin-systemplugins-bluetoothsetup enigma2-plugin-extensions-witaispeechtotext", "", d)} \
    ${@bb.utils.contains("STATIC_FEED", "0", "${STATIC_FEED_BUILD}", "${STATIC_FEED_DEPENDS}", d)} \
    autofs \
    autossh \
    ${@bb.utils.contains("DISTRO_FEATURES", "directfb", "avahi-ui", "", d)} \
    astra-sm \
    binutils \
    btrfs-tools \
    chrpath \
    ctorrent \
    cups \
    davfs2 \
    cdtextinfo \
    dabstreamer \
    djmount \
    dosfstools \
    dvb-apps \
    dvblast \
    dvbsnoop \
    dvdfs \
    edid-decode \
    eti-tools \
    extundelete \
    exfatprogs \
    ${@bb.utils.contains_any("MACHINEBUILD", "gbquad4k gbquad4kpro gbue4k galaxy4k lunix34k", "enigma2-plugin-extensions-forcefbclnbpower" , "", d)} \
    f2fs-tools \
    fio \
    fuse-exfat \
    gptfdisk \
    hddtemp \
    hdparm \
    htop \
    idle3-tools \
    inadyn-mt \
    iperf3 \
    ipkg-tools \
    joe \
    liba52 \
    lighttpd \
    livestreamersrv \
    streamlinksrv \
    streamlink \
    llmnr-query \
    mc \
    mergerfs \
    minidlna \
    minilocale \
    minisatip \
    mpd \
    mtd-utils \
    nano \
    net-snmp \
    nlohmann-json \
    nss \
    ntfs-3g \
    ntp \
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
    libpcsc-perl \
    procps \
    pyload \
    ${PYTHON_PN}-aiohttp \
    ${PYTHON_PN}-attr \
    ${PYTHON_PN}-attrs \
    ${PYTHON_PN}-aws-iot-device-sdk-python \
    ${PYTHON_PN}-cattrs \
    ${PYTHON_PN}-autobahn \
    ${PYTHON_PN}-bluetool \
    ${PYTHON_PN}-circuits \
    ${PYTHON_PN}-circuits-bricks \
    ${PYTHON_PN}-cfscrape \
    ${PYTHON_PN}-evdev \
    ${PYTHON_PN}-future \
    ${PYTHON_PN}-futures3 \
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
    ${PYTHON_PN}-tinytag \
    ${PYTHON_PN}-trio \
    ${PYTHON_PN}-tmdbsimple \
    ${PYTHON_PN}-tvdbsimple \
    ${PYTHON_PN}-tmdbv3api \
    ${PYTHON_PN}-transmission-rpc \
    ${PYTHON_PN}-ujson \
    ${PYTHON_PN}-url-normalize \
    ${PYTHON_PN}-singledispatch \
    ${PYTHON_PN}-levenshtein \
    ${PYTHON_PN}-soco \
    ${PYTHON_PN}-tqdm \
    ${PYTHON_PN}-pyexecjs \
    ${PYTHON_PN}-scrapy \
    ${PYTHON_PN}-timeout-decorator \
    rapidxml \
    rclone \
    rsync \
    rtorrent \
    sabnzbd3 \
    satpi \
    screen \
    smartmontools \
    smbnetfs \
    sshpass \
    streamproxy \
    strongswan \
    tsniv2ni \
    tailscale \
    tcpdump \
    tmux \
    transmission \
    uhubctl \
    ushare \
    vim \
    wakelan \
    ${@"" if bb.utils.vercmp_string_op('${PREFERRED_VERSION_${PREFERRED_PROVIDER_virtual/kernel}}', '3.10', '<') else "wireguard-tools ${WIREGUARD_MODULE}"} \
    wireless-tools \
    zeroconf \
    zerotier \
    "

WIREGUARD_MODULE = "${@bb.utils.contains_any("MACHINE", "osmini4k osmio4k osmio4kplus", "", "wireguard-module", d)}"

GST_BASE_DVD = "\
    gstreamer1.0-plugins-bad-videoparsersbad \
    gstreamer1.0-plugins-bad-mpegtsmux \
"

STATIC_FEED_BUILD = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "openhbbtv", "enigma2-plugin-extensions-openhbbtvbrowser", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "qthbbtv", "enigma2-plugin-extensions-hbbtv-qt", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "qtstalker", "enigma2-plugin-extensions-stalker-qt", "", d)} \
    ${@bb.utils.contains_any("MACHINE_FEATURES", "kodi21", "kodi-addons-meta enigma2-plugin-extensions-kodi", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gb-qthbbtv", "enigma2-plugin-extensions-hbbtv-gb", "", d)} \
    nodejs \
"

STATIC_FEED_DEPENDS = "\
"

RRECOMMENDS:${PN} = "${@bb.utils.contains("MACHINE_FEATURES", "operahbbtv", "enigma2-plugin-extensions-hbbtv" , "", d)}"
 
