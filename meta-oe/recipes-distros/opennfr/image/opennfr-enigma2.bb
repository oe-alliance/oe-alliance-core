SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r40"

inherit packagegroup

DEPENDS = "opennfr-feeds opennfr-3rdparty-plugins 3rd-party-feed-configs"

RDEPENDS_${PN} = "\
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-menusort \
    enigma2-plugin-extensions-infopanel \
    enigma2-plugin-extensions-nfr4xboot \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-customsubservices \    
    \
    enigma2-plugin-systemplugins-fastscan \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-hotplug \    
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-extensions-openwebif-themes enigma2-plugin-extensions-openwebif-terminal", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "enigma2-plugin-extensions-openwebif-webtv", "enigma2-plugin-extensions-openwebif-vxg", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "nextv-hbbtv-browser", " enigma2-plugin-extensions-hbbtv-nextv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "chromiumos", "enigma2-plugin-extensions-chromium", "", d)} \ 
    ${@bb.utils.contains("MACHINE_FEATURES", "quadpip", "enigma2-plugin-extensions-quadpip", "", d)} \
    \
    enigma2-plugin-opennfrskins-smokedefault-hd \
    enigma2-plugin-opennfrskins-utopia-hd \
    opennfr-base-files \
	nfr4xmultiboot \
    \
    ${@bb.utils.contains("MACHINE_FEATURES", "boxmodel", "boxmodel" , "", d)} \
"


