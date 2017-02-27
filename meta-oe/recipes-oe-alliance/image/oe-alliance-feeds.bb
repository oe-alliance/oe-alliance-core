SUMMARY = "Merge machine and distro options to create a oe-alliance enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r15"

DEPENDS = "enigma2-plugin-drivers-usbserial"
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
    enigma2-plugin-extensions-openairplay \
    enigma2-plugin-extensions-mediatomb \
    enigma2-plugin-extensions-dreamplex \
    enigma2-plugin-extensions-et-portal \
    enigma2-plugin-extensions-moviearchiver \
    enigma2-plugin-extensions-yahooweather \
    enigma2-plugin-extensions-youtube \
    enigma2-plugin-extensions-autobouquets \
    enigma2-plugin-extensions-iptvplayer \
    ${@bb.utils.contains("GST_VERSION", "1.0", "eplayer5", "eplayer4", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "exteplayer3 enigma2-plugin-systemplugins-serviceapp" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "exteplayer3 enigma2-plugin-systemplugins-serviceapp" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "sh4", "exteplayer3 enigma2-plugin-systemplugins-serviceapp" , "gdb v4l-utils", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "legacykernel", "" , "evtest strace", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "omb", "enigma2-plugin-extensions-openmultiboot openmultiboot", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "vukodi", "enigma2-plugin-extensions-vuplus-kodi", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "xcorekodi", "enigma2-plugin-extensions-mx3l-kodi", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "nextv-hbbtv-browser", " enigma2-plugin-extensions-hbbtv-nextv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "chromiumos", "enigma2-plugin-extensions-chromium", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gles", "libmicrohttpd libnfs libshairport libtinyxml mysql5 yajl" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvd", "bdremux replex mjpegtools dvdauthor dvd+rw-tools cdrkit cdfs cdtextinfo enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdburn enigma2-plugin-extensions-dvdplayer ${GST_BASE_DVD}", "", d)} \
    autofs \
    autossh \
    avahi-ui \
    astra-sm \
    binutils \
    ctorrent \
    cups \
    djmount \
    dosfstools \
    dvblast \
    dvbsnoop \
    dvdfs \
    exfat-utils \
    fuse-exfat \
    hddtemp \
    hdparm \
    htop \
    idle3-tools \
    inadyn-mt \
    iperf \
    joe \
    livestreamer \
    livestreamersrv \
    llmnr-query \
    mc \
    minidlna \
    mpd \
    mtd-utils \
    nano \
    net-snmp \
    ntfs-3g \
    ntp \
    odhcp6c \
    ofgwrite \
    openresolv \
    openssh \
    openvpn \
    easy-rsa \
    packagegroup-base-samba \
    parted \
    pngquant \
    pcsc-lite \
    procps \
    pyload \
    python-attr \
    python-attrs \
    python-circuits \
    python-circuits-bricks \
    python-cfscrape \
    python-cocy \
    python-futures \
    python-ipaddress \
    python-js2py \
    python-mechanize \
    python-pyasn1-modules \
    python-requests \
    python-service-identity \
    python-ujson \
    python-singledispatch \
    rsync \
    rtorrent \
    sabnzbd \
    smartmontools \
    smbnetfs \
    sshpass \
    streamproxy \
    tcpdump \
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

RDEPENDS_${PN}_remove_wetekplay = "network-usb-drivers-meta"
RDEPENDS_${PN}_remove_wetekplay2 = "network-usb-drivers-meta"
RDEPENDS_${PN}_remove_odroidc2 = "network-usb-drivers-meta"

RRECOMMENDS_${PN}_append_vuuno = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuultimo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vusolo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vusolo2 = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuduo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuduo2 = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuzero = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_hd2400 = "opengl-hd"
RRECOMMENDS_${PN}_append_formuler1 = "opengl-hd"
