SUMMARY = "Bluetooth Audio ALSA Backend"
HOMEPAGE = "https://github.com/Arkq/bluez-alsa"
SECTION = "devel"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=88dc1c98120259ae67b82222d7aff5c1"

DEPENDS = "alsa-lib bluez5 glib-2.0 sbc"

SRCREV = "49e3502eba94714b2a18f93deb6c66ddba73bd74"
SRC_URI = "git://github.com/Arkq/bluez-alsa.git;protocol=https;branch=master \
           file://bluez-alsa.service"

FILESEXTRAPATHS_append := "${THISDIR}/files:"

S = "${WORKDIR}/git"

inherit systemd pkgconfig autotools

do_install () {
    autotools_do_install
    #install -d ${D}${systemd_unitdir}/system
    #install -m 0644 ${WORKDIR}/bluez-alsa.service ${D}${systemd_unitdir}/system
}

FILES_${PN} += "${libdir}/alsa-lib/lib*.so ${datadir}/alsa"
FILES_${PN}-dev += "${libdir}/alsa-lib/*.la"
FILES_${PN}-staticdev += "${libdir}/alsa-lib/lib*.a"
FILES_${PN}-dbg += "${libdir}/alsa-lib/.debug/*.so"

#SYSTEMD_SERVICE_${PN} = "bluez-alsa.service"
