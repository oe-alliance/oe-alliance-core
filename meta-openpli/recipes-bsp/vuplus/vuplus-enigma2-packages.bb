DESCRIPTION = "Vuplus Specific plugins"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c9e255efa454e0155c1fd758df7dcaf3"

DEPENDS = "python-native"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "0c5ce99c4f07836ab35fb0675a63c8cd4341a72c"
inherit gitpkgv
 
PV = "experimental-git${SRCPV}"
PKGV = "experimental-git${GITPKGV}"
PR = "r2"
BRANCH = "vuplus_experimental"

SRC_URI = "git://code.vuplus.com/git/dvbapp.git;protocol=http;branch=${BRANCH};tag=${SRCREV} \
	file://vuplus_enigma2_packages.patch;striplevel=1 \
"

S = "${WORKDIR}/git"

do_install() {
	install -d  ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ManualFancontrol
	install -d  ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/FPGAUpgrade
	install -d  ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/Blindscan
	install -d  ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/FirmwareUpgrade
	install -d  ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/RemoteControlCode
	install -d  ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/LEDBrightnessSetup

	install -m 0644 ${S}/lib/python/Plugins/SystemPlugins/ManualFancontrol/*.py \
	${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ManualFancontrol

	install -m 0644 ${S}/lib/python/Plugins/SystemPlugins/FPGAUpgrade/*.py \
	${S}/lib/python/Plugins/SystemPlugins/FPGAUpgrade/*.conf \
	${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/FPGAUpgrade

	install -m 0644 ${S}/lib/python/Plugins/SystemPlugins/Blindscan/*.py \
	${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/Blindscan

	install -m 0644 ${S}/lib/python/Plugins/SystemPlugins/FirmwareUpgrade/*.py \
	${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/FirmwareUpgrade

	install -m 0644 ${S}/lib/python/Plugins/SystemPlugins/RemoteControlCode/*.py \
	${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/RemoteControlCode

	install -m 0644 ${S}/lib/python/Plugins/SystemPlugins/LEDBrightnessSetup/*.py \
	${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/LEDBrightnessSetup
	
	python -O -m compileall ${D}/usr/lib/enigma2/python/Plugins/
}

FILES_enigma2-plugin-systemplugins-manualfancontrol = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ManualFancontrol"
FILES_enigma2-plugin-systemplugins-fpgaupgrade = "/usr/lib/enigma2/python/Plugins/SystemPlugins/FPGAUpgrade"
FILES_enigma2-plugin-systemplugins-firmwareupgrade = "/usr/lib/enigma2/python/Plugins/SystemPlugins/FirmwareUpgrade"
FILES_enigma2-plugin-systemplugins-remotecontrolcode = "/usr/lib/enigma2/python/Plugins/SystemPlugins/RemoteControlCode"
FILES_enigma2-plugin-systemplugins-ledbrightnesssetup = "/usr/lib/enigma2/python/Plugins/SystemPlugins/LEDBrightnessSetup"

PACKAGES = "\
	enigma2-plugin-systemplugins-manualfancontrol \
	enigma2-plugin-systemplugins-fpgaupgrade \
	enigma2-plugin-systemplugins-firmwareupgrade \
	enigma2-plugin-systemplugins-remotecontrolcode \
	enigma2-plugin-systemplugins-ledbrightnesssetup \
	"

PROVIDES="${PACKAGES}"

