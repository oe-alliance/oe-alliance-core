SUMMARY = "Merge machine and distro options to create a oe-allinace enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r65"

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
    \
    enigma2-plugins \
    enigma2-plugin-drivers-ntfs-3g \
    enigma2-plugin-drivers-exfat \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-tuxcom \
    enigma2-plugin-security-firewall \
    enigma2-plugin-extensions-openairplay \
    ${@base_contains("TARGET_ARCH", "mipsel", "enigma2-plugin-extensions-et-livestream" , "", d)} \
    enigma2-plugin-extensions-mediatomb \
    enigma2-plugin-extensions-dreamplex \
    enigma2-plugin-extensions-iptvlistupdater \
    enigma2-plugin-extensions-et-portal \
    enigma2-plugin-extensions-moviearchiver \
    enigma2-plugin-extensions-yahooweather \
    enigma2-plugin-extensions-youtube \
    ${@base_contains("MACHINE_FEATURES", "omb", "enigma2-plugin-extensions-openmultiboot", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "omb", "openmultiboot", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "xbmc", "enigma2-plugin-extensions-xbmc", "", d)} \
    \
    ${@base_contains("MACHINE_FEATURES", "fullgraphiclcd", "lcdpicons-enigma2-meta" , "", d)} \
    \
    ${@base_contains("MACHINE_FEATURES", "sdl", "snes9x-sdl" , "", d)} \
    \
    autofs \
    autossh \
    avahi-ui \
    binutils \
    ctorrent \
    cups \
    djmount \
    dosfstools \
    dvbsnoop \
    dvdfs \
    ${@base_contains("MACHINE_FEATURES", "legacykernel", "" , "evtest strace", d)} \
    exfat-utils \
    fuse-exfat \
    ${@base_contains("TARGET_ARCH", "sh4", "" , "gdb", d)} \
    hddtemp \
    hdparm \
    htop \
    inadyn-mt \
    iperf \
    joe \
    mc \
    minidlna \
    mpd \
    mtd-utils \
    nano \
    net-snmp \
    ntfs-3g \
    ntp \
    openresolv \
    openssh \
    openvpn \
    parted \
    procps \
    pyload \
    python-circuits python-circuits-bricks python-cocy python-mechanize python-requests livestreamer livestreamersrv \
    rsync \
    rtorrent \
    sabnzbd \
    samba \
    smartmontools \
    smbnetfs \
    sshpass \
    tcpdump \
    transmission \
    ushare \
    vim \
    wakelan \
    xfsprogs \
    zeroconf \
    ofgwrite \
    idle3-tools \
    pngquant \
    streamproxy \
    ${@base_contains("GST_VERSION", "1.0", "eplayer5", "eplayer4", d)} \
    "

RDEPENDS_${PN}_remove_xc7362 = "network-usb-drivers-meta"
RDEPENDS_${PN}_remove_wetekplay = "network-usb-drivers-meta"

RRECOMMENDS_${PN}_append_vuuno = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuultimo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vusolo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vusolo2 = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuduo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuduo2 = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuzero = "enigma2-plugin-extensions-hbbtv"
