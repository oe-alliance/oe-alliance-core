DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r13"

inherit task

DEPENDS = "openhdf-feeds openhdf-plugins-meta openhdf-plugins-meta"

RRECOMMENDS = "\
	enigma2-plugin-hdf-toolbox \
	enigma2-plugin-extensions-autobackup \
	enigma2-plugin-extensions-audiosync \
	enigma2-plugin-extensions-autotimer \
	enigma2-plugin-extensions-devicemanager \
	enigma2-plugin-extensions-dvdplayer \
	enigma2-plugin-extensions-dlnabrowser \
	enigma2-plugin-extensions-epgsearch \
	enigma2-plugin-extensions-graphmultiepg \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-mediaplayer \
	enigma2-plugin-extensions-streamtv \
	enigma2-plugin-extensions-zaphistorybrowser \
	enigma2-plugin-extensions-webbouqueteditor \
	enigma2-plugin-extensions-webinterface \
	enigma2-plugin-picons-openhdf-19 \
	enigma2-plugin-pli-softcamsetup \
	enigma2-plugin-skins-fonts \
	enigma2-plugin-skins-nobile-mod \
	enigma2-plugin-softcam-cccam230 \
	enigma2-plugin-softcam-cccam230cardserver \
	enigma2-plugin-softcam-cccam230configs \
	enigma2-plugin-softcam-keys \
	enigma2-plugin-softcam-oscamemuconfigs \
	enigma2-plugin-softcam-oscamexp \
	enigma2-plugin-softcam-oscamexpcardserver \
	enigma2-plugin-softcam-oscamexpconfigs \
	enigma2-plugin-softcam-oscamymod \
	enigma2-plugin-softcam-oscamymodcardserver \
	enigma2-plugin-softcam-scam359 \
	enigma2-plugin-softcam-scam360 \
	enigma2-plugin-systemplugins-3dsettings \
	enigma2-plugin-systemplugins-autoshutdown \
	enigma2-plugin-systemplugins-hdmicec \
	enigma2-plugin-systemplugins-osd3dsetup \
	enigma2-plugin-systemplugins-skinselector \
	enigma2-plugin-systemplugins-softwaremanager \
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
	"
