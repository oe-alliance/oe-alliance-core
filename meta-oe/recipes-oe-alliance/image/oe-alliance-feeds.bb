SUMMARY = "Merge machine and distro options to create a oe-alliance enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r33"

DEPENDS = "enigma2-plugin-drivers-usbserial enigma2-plugin-systemplugins-hrtunerproxy"
RECOMMENDS = "enigma2-plugin-extensions-et-livestream"


RDEPENDS_${PN} = " \
    bootlogos-enigma2-meta \
    dvb-usb-drivers-meta \
    network-usb-drivers-meta \
    channelsettings-enigma2-meta \
    picons-enigma2-meta \
    packagegroup-openplugins \
    meta-enigma2-dvdburn \
    enigma2-plugins \
    enigma2-plugin-drivers-ntfs-3g \
    enigma2-plugin-drivers-exfat \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-tuxcom \
    enigma2-plugin-security-firewall \
    enigma2-plugin-extensions-enigmalight \
    enigma2-plugin-extensions-mediatomb \
    enigma2-plugin-extensions-dreamplex \
    enigma2-plugin-extensions-et-portal \
    enigma2-plugin-extensions-moviearchiver \
    enigma2-plugin-extensions-moviemanager \
    enigma2-plugin-extensions-yahooweather \
    enigma2-plugin-extensions-youtube \
    enigma2-plugin-extensions-autobouquets \
    enigma2-plugin-extensions-iptvplayer \
    enigma2-plugin-extensions-iptvplayer-deps \
    enigma2-plugin-systemplugins-hrtunerproxy \
    oe-alliance-branding-remote \
    ${@bb.utils.contains("GST_VERSION", "1.0", "eplayer5", "eplayer4", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "exteplayer3 enigma2-plugin-systemplugins-serviceapp" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "exteplayer3 enigma2-plugin-systemplugins-serviceapp" , "", d)} \
    ${@bb.utils.contains("DEFAULTTUNE", "sh4", "exteplayer3 enigma2-plugin-systemplugins-serviceapp" , "gdb v4l-utils", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "legacykernel", "" , "evtest strace", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "omb", "enigma2-plugin-extensions-openmultiboot openmultiboot", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "kodi", "kodi-addons-meta enigma2-plugin-extensions-kodi", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "qthbbtv", "enigma2-plugin-extensions-hbbtv-qt", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "qtstalker", "enigma2-plugin-extensions-stalker-qt", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "hbbtv-browser-webkit", " enigma2-plugin-extensions-hbbtv-webkit", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "chromiumos", "enigma2-plugin-extensions-chromium", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "vuglesdemo", "enigma2-plugin-extensions-libvupldemo", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvd", "bdremux replex mjpegtools dvdauthor dvd+rw-tools cdrkit cdfs cdtextinfo enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdburn enigma2-plugin-extensions-dvdplayer ${GST_BASE_DVD}", "", d)} \
    autofs \
    autossh \
    ${@bb.utils.contains("DISTRO_FEATURES", "directfb", "avahi-ui", "", d)} \
    astra-sm \
    binutils \
    ctorrent \
    cups \
    davfs2 \
    djmount \
    dosfstools \
    dvblast \
    dvbsnoop \
    dvdfs \
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
    ntfs-3g \
    ntp \
    ${@bb.utils.contains("DEFAULTTUNE", "sh4", "" , "nodejs", d)} \
    odhcp6c \
    ofgwrite \
    openresolv \
    openssh \
    openvpn \
    easy-rsa \
    p7zip \
    packagegroup-base-samba \
    parted \
    pngquant \
    pcsc-lite \
    procps \
    pyload \
    python-attr \
    python-attrs \
    python-bluetool \
    python-circuits \
    python-circuits-bricks \
    python-cfscrape \
    python-cocy \
    python-futures \
    python-fuzzywuzzy \
    python-ipaddress \
    python-js2py \
    python-mechanize \
    python-netifaces \
    python-pyasn1-modules \
    python-requests \
    python-service-identity \
    python-ujson \
    python-singledispatch \
    python-levenshtein \
    python-soco \
    python-pyexecjs \
    ${@bb.utils.contains("DEFAULTTUNE", "sh4", "" , "rclone", d)} \
    rsync \
    rtorrent \
    sabnzbd \
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
    wget \
    zeroconf \
    "

GST_BASE_DVD = "${@bb.utils.contains('GST_VERSION', '1.0', ' \
    gstreamer1.0-plugins-bad-videoparsersbad \
    gstreamer1.0-plugins-bad-mpegtsmux \
    ', ' \
    gst-plugins-bad-videoparsersbad \
    gst-plugins-bad-mpegtsmux \
    ', d)}"

RRECOMMENDS_${PN}_append_vuuno = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuultimo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vusolo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vusolo2 = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuduo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuduo2 = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuzero = "enigma2-plugin-extensions-hbbtv"

