DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r2"

inherit task

DEPENDS = "enigma2-pliplugins openhdf-feeds openhdf-plugins-meta openhdf-picons-meta"

RRECOMMENDS = "\
	enigma2-plugin-systemplugins-3dsettings \
	enigma2-plugin-extensions-zaphistorybrowser \
	enigma2-plugin-systemplugins-skinselector \
	enigma2-plugin-hdf-toolbox \
	enigma2-plugin-picons-openhdf-19 \
	enigma2-plugin-skins-nobile \
	enigma2-plugin-skins-fonts \
	enigma2-plugin-settings-koivo \
	enigma2-plugin-extensions-ppanel \
	enigma2-plugin-pli-softcamsetup \
	enigma2-plugin-extensions-autobackup \
	enigma2-plugin-extensions-audiosync \
	enigma2-plugin-extensions-autotimer \
	enigma2-plugin-extensions-epgsearch \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-streamtv \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-autoshutdown \
	enigma2-plugin-systemplugins-hdmicec \
	enigma2-plugin-systemplugins-osd3dsetup \
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
	"
