SUMMARY = "lcd4linux plugin for duo2"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://LCD4linux.tar.gz file://wetter.tar.gz file://duo2lcd4linux.patch;patch=1;pnum=1"

DEPENDS = "${PYTHON_PN} ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-imaging", "${PYTHON_PN}-pillow", d)} ${PYTHON_PN}-pyusb lcd4linux lcd4linuxsupport"
RDEPENDS:enigma2-plugin-extensions-lcd4linux-duo2 = "enigma2 ${PYTHON_PN}-codecs ${PYTHON_PN}-datetime ${PYTHON_PN}-pillow ${PYTHON_PN}-shell ${PYTHON_PN}-ctypes ${PYTHON_PN}-pyusb lcd4linux lcd4linuxsupport"
DESCRIPTION:enigma2-plugin-extensions-lcd4linux-duo2 = "Duo2 LCD support driver and setup."

S = "${WORKDIR}/LCD4linux"

PR = "r11"

PLUGINPATH = "/usr/lib/enigma2/python/Plugins/Extensions/LCD4linux"

do_install() {
    install -d  ${D}${PLUGINPATH}
    install -m 0600 ${S}/*.* ${D}${PLUGINPATH}
    install -m 0600 ${S}/refreshrate ${D}${PLUGINPATH}
    install -d  ${D}${PLUGINPATH}/locale/de/LC_MESSAGES
    install -m 0600 ${S}/locale/de/LC_MESSAGES/* ${D}${PLUGINPATH}/locale/de/LC_MESSAGES
    install -d  ${D}${PLUGINPATH}/wetter
    install -m 0600 ${S}/wetter/* ${D}${PLUGINPATH}/wetter
}


python populate_packages:prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s-duo2', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-duo2-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-duo2-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-duo2-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-duo2-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}
