SUMMARY = "Additional plugins for Enigma2"
MAINTAINER = "oe-alliance team"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit bluetooth

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

PACKAGES_DYNAMIC = "enigma2-plugin-(?!oealliance-).*"

PACKAGES += " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'operahbbtv', 'enigma2-plugin-extensions-hbbtv ' , ' ', d)} \
    enigma2-plugin-extensions-lcd4linux \
    "

PROVIDES += " \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-btdevicesmanager \
    enigma2-plugin-systemplugins-blindscan \
    enigma2-plugin-systemplugins-channelsimporter \
    enigma2-plugin-extensions-dlnabrowser \
    enigma2-plugin-extensions-dlnaserver \
    enigma2-plugin-systemplugins-abmcustommiximporter \
    enigma2-plugin-systemplugins-firmwareupgrade \
    enigma2-plugin-systemplugins-fpgaupgrade \
    enigma2-plugin-systemplugins-vfdcontrol \
    enigma2-plugin-extensions-streamtv \
    enigma2-plugin-systemplugins-tempfancontrol \
    enigma2-plugin-systemplugins-fancontrol \
    enigma2-plugin-systemplugins-remotecontrolcode \
    enigma2-plugin-extensions-webbrowser \
    enigma2-plugin-extensions-ondemand-openuitzendinggemist \
    enigma2-plugin-systemplugins-satipclient \
    enigma2-plugin-systemplugins-terrestrialscan \
    enigma2-plugin-extensions-tunerserver \
    ${@bb.utils.contains('MACHINE_FEATURES', 'operahbbtv', 'enigma2-plugin-extensions-hbbtv ' , ' ', d)} \
    enigma2-plugin-systemplugins-transcodingsetup \
    enigma2-plugin-systemplugins-micomupgrade \
    enigma2-plugin-systemplugins-multitranscodingsetup \
    enigma2-plugin-extensions-ondemand \
    enigma2-plugin-extensions-fempa \
    enigma2-plugin-extensions-lcd4linux \
    enigma2-plugin-extensions-remotechannelstreamconverter \
    ${@bb.utils.contains('MACHINE_FEATURES', 'legacykernel', '' , 'enigma2-plugin-systemplugins-wirelessaccesspoint', d)} \
    ${@bb.utils.contains('MACHINE', 'spark7162', 'enigma2-plugin-systemplugins-uniontunertype ' , ' ', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'sh4booster', 'enigma2-plugin-systemplugins-sh4boostercontrol' , ' ', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'aml-plugins', 'enigma2-plugin-extensions-rcuselect enigma2-plugin-extensions-rezap' , ' ', d)} \
     "

DEPENDS = "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'blindscan-dvbc', 'virtual/blindscan-dvbc' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'blindscan-dvbs', 'virtual/blindscan-dvbs' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'transcoding', 'virtual/transtreamproxy' , '', d)} \
    python-dnspython python-beautifulsoup python-lxml python-simplejson python-pyamf python-icalendar python-pyusb \
    djmount \
    librtmp \
    minidlna \
    hddtemp \
    ppp \
    usbmodeswitch \
    usbmodeswitch-data \
    usbutils \
    satipclient \
    bluez-hidd \
    ${@bb.utils.contains('MACHINE_FEATURES', 'legacykernel', '' , 'hostapd bridge-utils', d)} \
    wvdial wvstreams \
    "

DESCRIPTION_enigma2-plugin-systemplugins-audioeffect = "Audio Effect setup"
DESCRIPTION_enigma2-plugin-extensions-btdevicesmanager = "this is bt devices manger to pair e.x keyboard or mouse"
RDEPENDS_enigma2-plugin-extensions-btdevicesmanager = "${BLUEZ}-testtools ${BLUEZ} bluez-hcidump bluez-hidd"
DESCRIPTION_enigma2-plugin-systemplugins-blindscan = "blindscan..."
RRECOMMENDS_enigma2-plugin-systemplugins-blindscan = "virtual/blindscan-dvbs"
DESCRIPTION_enigma2-plugin-extensions-dlnabrowser = "this is dlna/upnp browser using djmount"
RDEPENDS_enigma2-plugin-extensions-dlnabrowser = "djmount fuse-utils libfuse2 libupnp libneon27"
DESCRIPTION_enigma2-plugin-extensions-dlnaserver = "this is dlna server using minidlna"
RDEPENDS_enigma2-plugin-extensions-dlnaserver = "minidlna"
DESCRIPTION_enigma2-plugin-systemplugins-firmwareupgrade = "Upgrade your system Firmware"
DESCRIPTION_enigma2-plugin-systemplugins-fpgaupgrade = "Upgrade your system FPGA"
DESCRIPTION_enigma2-plugin-systemplugins-vfdcontrol = "vfd controller"
DESCRIPTION_enigma2-plugin-extensions-streamtv = "iptv player"
RDEPENDS_enigma2-plugin-extensions-streamtv = "librtmp1"
DESCRIPTION_enigma2-plugin-systemplugins-tempfancontrol = "Control your internal system fan."
DESCRIPTION_enigma2-plugin-systemplugins-terrestrialscan = "Selects the strongest transponders where there are duplicates and allows filtering by network id."
DESCRIPTION_enigma2-plugin-systemplugins-channelsimporter = "Imports a copy of the channel list from a remote receiver and loads it on the local receiver."
DESCRIPTION_enigma2-plugin-systemplugins-abmcustommiximporter = "Imports ABM CustomMix files from Github."
DESCRIPTION_enigma2-plugin-systemplugins-fancontrol = "Control your internal system fan."
RDEPENDS_enigma2-plugin-systemplugins-fancontrol_et9x00 = "hddtemp"
DESCRIPTION_enigma2-plugin-systemplugins-remotecontrolcode = "Change Remote Control Code"
RDEPENDS_enigma2-plugin-systemplugins-3gmodemmanager = "ppp usbmodeswitch usbmodeswitch-data wvdial wvstreams libwvutils4.6 libwvstreams-extras libuniconf4.6 kernel-module-ppp-async kernel-module-ppp-deflate kernel-module-ppp-synctty kernel-module-ppp-generic kernel-module-slhc kernel-module-usbserial kernel-module-cdc-acm kernel-module-ppp-mppe kernel-module-pppoe kernel-module-pppox kernel-module-option kernel-module-bsd-comp usbutils"
FILES_enigma2-plugin-systemplugins-3gmodemmanager_append = "$(sysconfdir)/ppp"
DESCRIPTION_enigma2-plugin-extensions-webbrowser = "Webbrowser launcher"
RDEPENDS_enigma2-plugin-extensions-webbrowser = "python-gdata libqtwebkite4 webbrowser-utils qt4-embedded-fonts qt4-embedded-plugin-imageformat-gif qt4-embedded-plugin-imageformat-ico qt4-embedded-plugin-imageformat-jpeg qt4-embedded-plugin-imageformat-mng qt4-embedded-plugin-imageformat-svg qt4-embedded-plugin-imageformat-tiff qt4-embedded-plugin-iconengine-svgicon"
FILES_enigma2-plugin-extensions-webbrowser_append = "${datadir}/keymaps"
DESCRIPTION_enigma2-plugin-extensions-ondemand-openuitzendinggemist = "Watch NL-IP TV"
DESCRIPTION_enigma2-plugin-systemplugins-satipclient = "Satip Client setup"
RDEPENDS_enigma2-plugin-systemplugins-satipclient = "satipclient"
DEPENDS_enigma2-plugin-systemplugins-satipclient = "satipclient"
REPLACES_enigma2-plugin-systemplugins-satipclient = "enigma2-plugin-extensions-satipclient"
DESCRIPTION_enigma2-plugin-extensions-tunerserver = "Builds a virtual channels list"
DESCRIPTION_enigma2-plugin-extensions-hbbtv = "HbbTV player"
RDEPENDS_enigma2-plugin-extensions-hbbtv = "vuplus-opera-browser-util"
DEPENDS_enigma2-plugin-extensions-hbbtv = "vuplus-opera-browser-util"
DESCRIPTION_enigma2-plugin-systemplugins-transcodingsetup = "Setup transcoding of your VU+"
RDEPENDS_enigma2-plugin-systemplugins-transcodingsetup = "virtual/transtreamproxy"
DESCRIPTION_enigma2-plugin-systemplugins-multitranscodingsetup = "Setup multitranscoding"
DESCRIPTION_enigma2-plugin-systemplugins-micomupgrade = "micomupgrade"
RDEPENDS_enigma2-plugin-extensions-ondemand = "python-dnspython python-beautifulsoup python-lxml python-simplejson python-pyamf"
DESCRIPTION_enigma2-plugin-extensions-ondemand = "Watch on demand TV."
DESCRIPTION_enigma2-plugin-extensions-fempa = "Norwegian P4 FEM PAA radio show player."
DESCRIPTION_enigma2-plugin-extensions-lcd4linux = "Web/DPF/Samsung LCD Ansteuerung"
DEPENDS_enigma2-plugin-extensions-lcd4linux = "lcd4linux png-util"
RDEPENDS_enigma2-plugin-extensions-lcd4linux = "lcd4linux enigma2-plugin-extensions-lcd4linux-src python-icalendar python-pyusb python-codecs python-datetime python-imaging python-textutils python-shell python-ctypes libusb-0.1-4 python-mutagen python-zlib python-email python-subprocess python-simplejson python-soco"
RDEPENDS_enigma2-plugin-extensions-lcd4linux_append_vuduo2 = " png-util"
FILES_enigma2-plugin-extensions-lcd4linux_append = "${libdir}/enigma2/python/Components/Renderer/*.pyo"
FILES_enigma2-plugin-extensions-lcd4linux-src_append = "${libdir}/enigma2/python/Components/Renderer/*.py"
DESCRIPTION_enigma2-plugin-extensions-remotechannelstreamconverter = "Fetch channels from remote bouquets and make them available locally"
RDEPENDS_enigma2-plugin-extensions-remotechannelstreamconverter = "python-shell"
RREPLACES_enigma2-plugin-extensions-remotechannelstreamconverter = "enigma2-plugin-extensions-remotestreamconvert"
DESCRIPTION_enigma2-plugin-systemplugins-wirelessaccesspoint = "Using a Wireless module as AP."
RDEPENDS_enigma2-plugin-systemplugins-wirelessaccesspoint = "hostapd bridge-utils"
DESCRIPTION_enigma2-plugin-extensions-rcuselect = "Change Remote for Wetek"
DESCRIPTION_enigma2-plugin-extensions-rezap = "ReZap Sync Tool for Wetek"

inherit autotools-brokensep gitpkgv pythonnative gettext

SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
PR = "r3"

SRC_URI = "${OEA_PLUGINS_URI}"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-boxtype=${MACHINE} \
    --with-arch=${TARGET_ARCH} \
    ${@bb.utils.contains("MACHINE_FEATURES", "aml-plugins", "--with-amlplugins" , "", d)} \
    "

ALLOW_EMPTY_${PN} = "1"
PACKAGES += "${PN}-meta"
FILES_${PN}-meta = "${datadir}/meta"

S = "${WORKDIR}/git"

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

INSANE_SKIP_${PN} += "build-deps"
