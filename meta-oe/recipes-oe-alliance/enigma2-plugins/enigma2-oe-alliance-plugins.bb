SUMMARY = "Additional plugins for Enigma2"
MAINTAINER = "oe-alliance team"
PACKAGE_ARCH = "${MACHINE_ARCH}"

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
    enigma2-plugin-systemplugins-abmcustommiximporter \
    enigma2-plugin-systemplugins-aboutboxbranding \
    enigma2-plugin-systemplugins-blindscan \
    enigma2-plugin-systemplugins-channelsimporter \
    enigma2-plugin-extensions-dlnabrowser \
    enigma2-plugin-extensions-dlnaserver \
    enigma2-plugin-systemplugins-firmwareupgrade \
    enigma2-plugin-systemplugins-fpgaupgrade \
    enigma2-plugin-systemplugins-vfdcontrol \
    enigma2-plugin-extensions-streamtv \
    enigma2-plugin-systemplugins-tempfancontrol \
    enigma2-plugin-systemplugins-fancontrol \
    enigma2-plugin-systemplugins-remotecontrolcode \
    enigma2-plugin-extensions-webbrowser \
    enigma2-plugin-extensions-openuitzendinggemist \
    enigma2-plugin-systemplugins-satipclient \
    enigma2-plugin-systemplugins-bluetoothsetup \
    enigma2-plugin-extensions-chromium \
    enigma2-plugin-extensions-tunerserver \
    enigma2-plugin-extensions-libvupldemo \
    enigma2-plugin-extensions-witaispeechtotext \
    enigma2-plugin-extensions-webkithbbtv \
    enigma2-plugin-extensions-stalkerclient \
    ${@bb.utils.contains('MACHINE_FEATURES', 'operahbbtv', 'enigma2-plugin-extensions-hbbtv ' , ' ', d)} \
    enigma2-plugin-systemplugins-transcodingsetup \
    enigma2-plugin-systemplugins-micomupgrade \
    enigma2-plugin-systemplugins-multitranscodingsetup \
    enigma2-plugin-extensions-ondemand \
    enigma2-plugin-extensions-fempa \
    enigma2-plugin-extensions-lcd4linux \
    enigma2-plugin-extensions-piconsupdater \
    enigma2-plugin-extensions-remotechannelstreamconverter \
    enigma2-plugin-extensions-tmdb \
    enigma2-plugin-extensions-tvspielfilm \
    ${@bb.utils.contains('MACHINE_FEATURES', 'legacykernel', '' , 'enigma2-plugin-systemplugins-wirelessaccesspoint', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'aml-plugins', 'enigma2-plugin-extensions-rcuselect enigma2-plugin-extensions-rezap' , ' ', d)} \
     "

DEPENDS = "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'blindscan-dvbc', 'virtual/blindscan-dvbc' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'blindscan-dvbs', 'virtual/blindscan-dvbs' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'transcoding', 'virtual/transtreamproxy' , '', d)} \
    ${PYTHON_PN}-dnspython ${PYTHON_PN}-beautifulsoup4 ${PYTHON_PN}-lxml ${PYTHON_PN}-simplejson ${PYTHON_PN}-pyamf ${PYTHON_PN}-icalendar ${PYTHON_PN}-pyusb ${PYTHON_PN}-six-native ${PYTHON_PN}-yt-dlp \
    djmount \
    dpflib \
    rtmpdump \
    minidlna \
    hddtemp \
    ppp \
    usbmodeswitch \
    usbmodeswitch-data \
    usbutils \
    satipclient \
    bluez-conf \
    bluez-hidd \
    bluez-alsa \
    ${@bb.utils.contains('MACHINE_FEATURES', 'legacykernel', '' , 'hostapd bridge-utils', d)} \
    wvdial wvstreams \
    ${@bb.utils.contains("MACHINE_FEATURES", "chromiumos", "chromium-browser", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gbbluetooth", "gb-bluetooth-util", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "vubluetooth", "vuplus-bluetooth-util", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "vuplus-webkithbbtv-dumpait webkit-hbbtv-browser", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "vuglesdemo", "libvupl-example-cube", "", d)} \
    "



DESCRIPTION:enigma2-plugin-systemplugins-audioeffect = "Audio Effect setup"
DESCRIPTION:enigma2-plugin-systemplugins-aboutboxbranding = "View Boxbranding data from the GUI"
DESCRIPTION:enigma2-plugin-extensions-btdevicesmanager = "this is bt devices manger to pair e.x keyboard or mouse"
RDEPENDS:enigma2-plugin-extensions-btdevicesmanager = "bluez5-testtools bluez5 bluez-hcidump bluez-conf bluez-hidd bluez-alsa alsa-utils-aplay ${PYTHON_PN}-pexpect"
DESCRIPTION:enigma2-plugin-systemplugins-blindscan = "blindscan..."
RRECOMMENDS:enigma2-plugin-systemplugins-blindscan = "virtual/blindscan-dvbs"
DESCRIPTION:enigma2-plugin-extensions-dlnabrowser = "this is dlna/upnp browser using djmount"
RDEPENDS:enigma2-plugin-extensions-dlnabrowser = "djmount fuse-utils libfuse2 libupnp1.6 libneon27"
DESCRIPTION:enigma2-plugin-extensions-dlnaserver = "this is dlna server using minidlna"
RDEPENDS:enigma2-plugin-extensions-dlnaserver = "minidlna"
DESCRIPTION:enigma2-plugin-systemplugins-firmwareupgrade = "Upgrade your system Firmware"
DESCRIPTION:enigma2-plugin-systemplugins-fpgaupgrade = "Upgrade your system FPGA"
DESCRIPTION:enigma2-plugin-systemplugins-vfdcontrol = "vfd controller"
DESCRIPTION:enigma2-plugin-extensions-streamtv = "iptv player"
RDEPENDS:enigma2-plugin-extensions-streamtv = "rtmpdump"
DESCRIPTION:enigma2-plugin-systemplugins-tempfancontrol = "Control your internal system fan."
DESCRIPTION:enigma2-plugin-systemplugins-channelsimporter = "Imports a copy of the channel list from a remote receiver and loads it on the local receiver."
DESCRIPTION:enigma2-plugin-systemplugins-abmcustommiximporter = "Imports ABM CustomMix files from Github."
DESCRIPTION:enigma2-plugin-systemplugins-fancontrol = "Control your internal system fan."
RDEPENDS:enigma2-plugin-systemplugins-fancontrol_et9x00 = "hddtemp"
DESCRIPTION:enigma2-plugin-systemplugins-remotecontrolcode = "Change Remote Control Code"
RDEPENDS:enigma2-plugin-systemplugins-3gmodemmanager = "ppp usbmodeswitch usbmodeswitch-data wvdial wvstreams libwvutils4.6 libwvstreams-extras libuniconf4.6 kernel-module-ppp-async kernel-module-ppp-deflate kernel-module-ppp-synctty kernel-module-ppp-generic kernel-module-slhc kernel-module-usbserial kernel-module-cdc-acm kernel-module-ppp-mppe kernel-module-pppoe kernel-module-pppox kernel-module-option kernel-module-bsd-comp usbutils"
FILES:enigma2-plugin-systemplugins-3gmodemmanager:append = "/etc/ppp/"
DESCRIPTION:enigma2-plugin-extensions-webbrowser = "Webbrowser launcher"
RDEPENDS:enigma2-plugin-extensions-webbrowser = "${PYTHON_PN}-gdata-python3 libqtwebkite4 webbrowser-utils qt4-embedded-fonts qt4-embedded-plugin-imageformat-gif qt4-embedded-plugin-imageformat-ico qt4-embedded-plugin-imageformat-jpeg qt4-embedded-plugin-imageformat-mng qt4-embedded-plugin-imageformat-svg qt4-embedded-plugin-imageformat-tiff qt4-embedded-plugin-iconengine-svgicon"
FILES:enigma2-plugin-extensions-webbrowser:append = "${datadir}/keymaps"
DESCRIPTION:enigma2-plugin-extensions-openuitzendinggemist = "Watch NL-IP TV"
DESCRIPTION:enigma2-plugin-systemplugins-satipclient = "Satip Client setup"
RDEPENDS:enigma2-plugin-systemplugins-satipclient = "satipclient"
DEPENDS:enigma2-plugin-systemplugins-satipclient = "satipclient"
REPLACES:enigma2-plugin-systemplugins-satipclient = "enigma2-plugin-extensions-satipclient"
DESCRIPTION:enigma2-plugin-extensions-tunerserver = "Builds a virtual channels list"
DESCRIPTION:enigma2-plugin-extensions-hbbtv = "HbbTV player"
RDEPENDS:enigma2-plugin-extensions-hbbtv = "vuplus-opera-browser-util"
DEPENDS:enigma2-plugin-extensions-hbbtv = "vuplus-opera-browser-util"
DESCRIPTION:enigma2-plugin-systemplugins-transcodingsetup = "Setup transcoding of your VU+"
RDEPENDS:enigma2-plugin-systemplugins-transcodingsetup = "virtual/transtreamproxy"
DESCRIPTION:enigma2-plugin-systemplugins-multitranscodingsetup = "Setup multitranscoding"
DESCRIPTION:enigma2-plugin-systemplugins-micomupgrade = "micomupgrade"
RDEPENDS:enigma2-plugin-extensions-ondemand = "${PYTHON_PN}-dnspython ${PYTHON_PN}-beautifulsoup4 ${PYTHON_PN}-lxml ${PYTHON_PN}-simplejson ${PYTHON_PN}-pyamf"
DESCRIPTION:enigma2-plugin-extensions-ondemand = "Watch on demand TV."
DESCRIPTION:enigma2-plugin-extensions-fempa = "Norwegian P4 FEM PAA radio show player."
DESCRIPTION:enigma2-plugin-extensions-lcd4linux = "Web/DPF/Samsung LCD Ansteuerung"
DEPENDS:enigma2-plugin-extensions-lcd4linux = "png-util"
RDEPENDS:enigma2-plugin-extensions-lcd4linux = "enigma2-plugin-extensions-lcd4linux-src dpflib ${PYTHON_PN}-icalendar ${PYTHON_PN}-pyusb ${PYTHON_PN}-codecs ${PYTHON_PN}-datetime ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-imaging", "${PYTHON_PN}-pillow", d)} ${PYTHON_PN}-image ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-textutils", "", d)} ${PYTHON_PN}-shell ${PYTHON_PN}-ctypes libusb-0.1-4 ${PYTHON_PN}-mutagen ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-zlib", "", d)} ${PYTHON_PN}-email ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-subprocess", "", d)} ${PYTHON_PN}-simplejson ${PYTHON_PN}-soco"
RDEPENDS:enigma2-plugin-extensions-lcd4linux:append:vuduo2 = " png-util"
FILES:enigma2-plugin-extensions-lcd4linux:append = " /usr/lib/enigma2/python/Components/Renderer/*.pyc"  
FILES:enigma2-plugin-extensions-lcd4linux-src:append = " /usr/lib/enigma2/python/Components/Renderer/*.py"
DESCRIPTION:enigma2-plugin-extensions-remotechannelstreamconverter = "Fetch channels from remote bouquets and make them available locally"
RDEPENDS:enigma2-plugin-extensions-remotechannelstreamconverter = "${PYTHON_PN}-shell"
RREPLACES:enigma2-plugin-extensions-remotechannelstreamconverter = "enigma2-plugin-extensions-remotestreamconvert"
DESCRIPTION:enigma2-plugin-extensions-tmdb = "Show TMDb information"
RDEPENDS:enigma2-plugin-extensions-tmdb = "${PYTHON_PN}-json ${PYTHON_PN}-requests"
DESCRIPTION:enigma2-plugin-systemplugins-wirelessaccesspoint = "Using a Wireless module as AP."
RDEPENDS:enigma2-plugin-systemplugins-wirelessaccesspoint = "hostapd bridge-utils"
DESCRIPTION:enigma2-plugin-extensions-rcuselect = "Change Remote for Wetek"
DESCRIPTION:enigma2-plugin-extensions-rezap = "ReZap Sync Tool for Wetek"
DESCRIPTION:enigma2-plugin-extensions-piconsupdater = "Download and install new Picons for your current bouquet channels. PiconsUpdater coded by svox and jbleyel, idea by arn354 and picons by mike99"
RDEPENDS:enigma2-plugin-extensions-piconsupdater = "${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-imaging ${PYTHON_PN}-textutils", "${PYTHON_PN}-pillow", d)} ${PYTHON_PN}-io ${PYTHON_PN}-compression pngquant"
DESCRIPTION:enigma2-plugin-systemplugins-bluetoothsetup = "E2 bluetooth plugin"
RDEPENDS:enigma2-plugin-systemplugins-bluetoothsetup = "libcurl libsqlite3 libssl libcrypto libudev libusb-compat libusb1 ${@bb.utils.contains("MACHINE_FEATURES", "vubluetooth", "vuplus-bluetooth-util", "", d)} ${@bb.utils.contains("MACHINE_FEATURES", "gbbluetooth", "gb-bluetooth-util", "", d)} "
DESCRIPTION:enigma2-plugin-extensions-chromium = "E2 Chromium Plugin"
RDEPENDS:enigma2-plugin-extensions-chromium = "chromium-browser"
DESCRIPTION:enigma2-plugin-extensions-libvupldemo = "Plugin for libvupl Demo"
RDEPENDS:enigma2-plugin-extensions-libvupldemo = "libvupl-example-cube"
DESCRIPTION:enigma2-plugin-extensions-witaispeechtotext = "Vuplus wit.ai speech to text plugin"
RDEPENDS:enigma2-plugin-extensions-witaispeechtotext = "${PYTHON_PN}-requests"
DESCRIPTION:enigma2-plugin-extensions-webkithbbtv = "E2 HbbTV Plugin"
RDEPENDS:enigma2-plugin-extensions-webkithbbtv = "vuplus-webkithbbtv-dumpait webkit-hbbtv-browser libupnp1.6"
DESCRIPTION:enigma2-plugin-extensions-stalkerclient = "E2 Staker Client Plugin"
DESCRIPTION:enigma2-plugin-extensions-tvspielfilm = "TVSpielfilm Plugin"
RDEPENDS:enigma2-plugin-extensions-tvspielfilm = "${PYTHON_PN}-yt-dlp"

inherit autotools-brokensep gitpkgv gettext ${PYTHON_PN}targetconfig

SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+gitr${SRCPV}"
PKGV = "${IMAGE_VERSION}+gitr${GITPKGV}"
PR = "r1"

SRC_URI = "${OEA_PLUGINS_URI}"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-boxtype=${MACHINE} \
    --with-arch=${TARGET_ARCH} \
    --with-pythonver=python3 \
    ${@bb.utils.contains("MACHINE_FEATURES", "aml-plugins", "--with-amlplugins" , "", d)} \
    "

ALLOW_EMPTY:${PN} = "1"
PACKAGES += "${PN}-meta"
FILES:${PN}-meta = "${datadir}/meta"

S = "${WORKDIR}/git"

python populate_packages:prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

do_package_qa() {
}
