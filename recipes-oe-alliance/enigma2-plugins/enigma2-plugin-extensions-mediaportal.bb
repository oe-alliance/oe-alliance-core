DESCRIPTION = "MediaPortal "
RDEPENDS_${PN} = "python-json gst-plugins-good-flv gst-plugins-bad-rtmp librtmp"
MAINTAINER = "dhwz"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "4.2.2+git${SRCPV}"
PKGV = "4.2.2+git${GITPKGV}"
PR = "r2"

SRC_URI = "git://github.com/dhwz/MediaPortal.git;protocol=git"

S = "${WORKDIR}/git"

do_install() {
	mkdir -p ${D}/usr/lib/enigma2/python/Plugins/Extensions/MediaPortal
	cp -rp ${S}/additions ${D}/usr/lib/enigma2/python/Plugins/Extensions/MediaPortal
	cp -rp ${S}/icons ${D}/usr/lib/enigma2/python/Plugins/Extensions/MediaPortal
	cp -rp ${S}/icons_wall ${D}/usr/lib/enigma2/python/Plugins/Extensions/MediaPortal
	cp -rp ${S}/images ${D}/usr/lib/enigma2/python/Plugins/Extensions/MediaPortal
	cp -rp ${S}/resources ${D}/usr/lib/enigma2/python/Plugins/Extensions/MediaPortal
	cp -rp ${S}/userfiles ${D}/usr/lib/enigma2/python/Plugins/Extensions/MediaPortal
	cp -p ${S}/*.p?? ${D}/usr/lib/enigma2/python/Plugins/Extensions/MediaPortal
}

# Just a quick hack to "compile" the python parts.
do_compile_append() {
	python -O -m compileall ${S}
}

python populate_packages_prepend() {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

pkg_postinst_${PN} () {
#!/bin/sh
echo "********************************************************"
echo "*  MediaPortal installed                               *"
echo "*                                                      *"
echo "*  Restart the Engima2 GUI to activate the plugin      *"
echo "********************************************************"
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
echo "Removing MediaPortal Plugin from the system ..."
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/MediaPortal > /dev/null 2>&1
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "Checking for an older version of MediaPortal in the system..."
if [ -d /usr/lib/enigma2/python/Plugins/Extensions/mediaportal ]
then
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/mediaportal > /dev/null 2>&1
echo "An older version of MediaPortal was found and removed"
echo "Proceeding to installation..."
elif [ -d /usr/lib/enigma2/python/Plugins/Extensions/MediaPortal ]
then
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/MediaPortal > /dev/null 2>&1
echo "An older version of MediaPortal was found and removed"
echo "Proceeding to installation..."
else
echo "MediaPortal was not found in the system"
echo "Proceeding to installation..."
fi
exit 0
}
