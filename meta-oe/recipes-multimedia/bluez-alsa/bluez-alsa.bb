SUMMARY = "Bluetooth Audio ALSA Backend"
HOMEPAGE = "https://github.com/Arkq/bluez-alsa"
SECTION = "devel"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3d7d6ac7e2dbd2505652dceb3acdf1fe"

DEPENDS = "alsa-lib bluez5 glib-2.0 sbc"

PV = "1.4.0"

SRCREV = "2725b4e8a0301aedb267d3db5850ab62586e6148"
SRC_URI = "git://github.com/Arkq/bluez-alsa.git;protocol=https;branch=master \
           file://bluez-alsa.service \
           file://init \
"

FILESEXTRAPATHS_append := "${THISDIR}/files:"

S = "${WORKDIR}/git"

inherit update-rc.d systemd pkgconfig autotools

do_install () {
    autotools_do_install
    install -d ${D}${sysconfdir}/init.d/
    install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/bluealsa
    #install -d ${D}${systemd_unitdir}/system
    #install -m 0644 ${WORKDIR}/bluez-alsa.service ${D}${systemd_unitdir}/system
}

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "bluealsa"
INITSCRIPT_PARAMS_${PN} = "defaults 80"

FILES_${PN} += "${libdir}/alsa-lib/lib*.so ${datadir}/alsa"
FILES_${PN}-dev += "${libdir}/alsa-lib/*.la"
FILES_${PN}-staticdev += "${libdir}/alsa-lib/lib*.a"
FILES_${PN}-dbg += "${libdir}/alsa-lib/.debug/*.so"

#SYSTEMD_SERVICE_${PN} = "bluez-alsa.service"
