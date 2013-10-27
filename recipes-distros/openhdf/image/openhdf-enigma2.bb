DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r48"

inherit task

DEPENDS = "enigma2-pliplugins openhdf-feeds openhdf-3rdparty-plugins"

RRECOMMENDS = "\
	openhdf-version-info \
	enigma2-skindefault \
	python-compression \
	enigma2-plugin-skins-nobile \
	enigma2-plugin-skins-army-mod \
	enigma2-plugin-drivers-usbserial \
	enigma2-plugin-extensions-autotimer \
	enigma2-plugin-extensions-epgsearch \
	enigma2-plugin-extensions-et-portal \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-hdftoolbox \
	enigma2-plugin-extensions-mediaplayer \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-extensions-dvdplayer \
	enigma2-plugin-extensions-virtualzap.mod \
	enigma2-plugin-extensions-volume-adjust \
	enigma2-plugin-pli-softcamsetup \
	enigma2-plugin-systemplugins-autoresolution \
	enigma2-plugin-systemplugins-devicemanager \
	enigma2-plugin-systemplugins-videotune \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-hotplug \
	enigma2-plugin-systemplugins-positionersetup \
	enigma2-plugin-systemplugins-videoenhancement \
	${@base_contains("MACHINE_FEATURES", "smallflash", "", \
	" \
	enigma2-plugin-extensions-enhancedmoviecenter \
	enigma2-plugin-extensions-cooltvguide \
	", d)} \
	"

RRECOMMENDS_append_gb800solo = ""
RRECOMMENDS_append_gb800se = ""
RRECOMMENDS_append_gb800ue = "python-imaging"
RRECOMMENDS_append_gbquad = "python-imaging"
RRECOMMENDS_append_tmtwin = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_append_tm2t = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_append_tmsingle = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_append_iqonios100hd = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_append_iqonios200hd = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_append_iqonios300hd = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_append_ixussone = ""
RRECOMMENDS_append_vuduo = "" 
RRECOMMENDS_append_vusolo = "" 
RRECOMMENDS_append_vusolo2 = "" 
RRECOMMENDS_append_et9x00 = "" 
RRECOMMENDS_append_et6x00 = "" 
RRECOMMENDS_append_et5x00 = "" 
RRECOMMENDS_append_et4x00 = "" 
