SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RCONFLICTS_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
RREPLACES_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

DEPENDS = "beyonwiz-feeds enigma2 enigma2-plugins enigma2-oe-alliance-plugins oe-alliance-feeds enigma2-3rdparty-plugins ${@base_contains("MACHINE_FEATURES", "wifi", "oe-alliance-wifi", "", d)}"

RDEPENDS_${PN} = "\
	oe-alliance-feeds-configs \
	aio-grab \
	enigma2 \
	tuxbox-links \
	tuxbox-common-aus \
	mtd-utils \
	${@base_conditional('MACHINE', 'dm800', '', 'mtd-utils-ubifs', d)} \
	procps \
	parted \
	\
	\
	\
	enigma2-skindefault \
	\
	beyonwiz-version-info \
	\
	enigma2-plugin-picons-tv-australia \
	\
	enigma2-plugin-extensions-autotimer \
	enigma2-plugin-extensions-iniecasa python-flickrapi \ 
	enigma2-plugin-extensions-inilastfm python-twisted-web python-crypt \
	enigma2-plugin-extensions-inishoutcast streamripper \
	enigma2-plugin-extensions-inizaphistorybrowser \
	enigma2-plugin-extensions-iniwebcamviewer \ 
	enigma2-plugin-extensions-gmailreader python-email python-subprocess \
	enigma2-plugin-extensions-accuweather \ 
	enigma2-plugin-extensions-airplayer \ 
	enigma2-plugin-extensions-mediaplayer \
	enigma2-plugin-extensions-epgsearch \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-extensions-inimytube \
	enigma2-plugin-extensions-filecommander \
	\
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-inilcnscanner \
	${@base_contains("MACHINE", "inihdx", "${HBBTV}" , "", d)} \
"

RRECOMMENDS_${PN} = "\
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-extensions-mediascanner \
    enigma2-plugin-extensions-pictureplayer \
    enigma2-plugin-extensions-openwebif \
    enigma2-plugin-systemplugins-networkbrowser \
    enigma2-plugin-systemplugins-networkwizard \
    \
    oe-alliance-drivers \
    \
    ${@base_contains("MACHINE_FEATURES", "hdmicec", "enigma2-plugin-systemplugins-hdmicec" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "wifi", "oe-alliance-wifi", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "dvd", "replex mjpegtools dvdauthor dvd+rw-tools cdrkit cdfs cdtextinfo enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdburn enigma2-plugin-extensions-dvdplayer", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "fanctrl", "enigma2-plugin-systemplugins-fancontrol", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "libpassthrough", "libpassthrough libdlsym", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "fan", "enigma2-plugin-systemplugins-tempfancontrol", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "transcoding", "enigma2-plugin-systemplugins-transcodingsetup", "", d)} \
    "
    
HBBTV = "\
	enigma2-plugin-extensions-inihbbtv \
	vuplus-opera-browser-util \
	"
