SUMMARY = "lcd4linux plugin for duo2"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://LCD4linux.tar.gz file://wetter.tar.gz file://duo2lcd4linux.patch;patch=1;pnum=1"

DEPENDS = "python python-imaging python-pyusb lcd4linux lcd4linuxsupport"
RDEPENDS_enigma2-plugin-extensions-lcd4linux-duo2 = "enigma2 python-codecs python-datetime python-imaging python-textutils python-shell python-ctypes python-pyusb lcd4linux lcd4linuxsupport"
DESCRIPTION_enigma2-plugin-extensions-lcd4linux-duo2 = "Duo2 LCD support driver and setup."

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

# Just a quick hack to "compile" the python parts.
do_compile_append() {
    python -O -m compileall ${S}
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s-duo2', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-duo2-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-duo2-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-duo2-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-duo2-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-duo2-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}
