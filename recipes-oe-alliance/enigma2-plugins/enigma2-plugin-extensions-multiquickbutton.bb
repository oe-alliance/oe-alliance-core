MODULE = "MultiQuickButton"
DESCRIPTION = "Multi Quickbutton editor/wizard/code interpreter for keyboard and RC ViX Version"
SECTION = "extra"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../LICENSE.GPLv2;md5=eb723b61539feef013de476e68b5c50a"

DEPENDS = "enigma2"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "2.8.4+git${SRCPV}"
PKGV = "2.8.4+git${GITPKGV}"
PR = "r2"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git \
		file://LICENSE.GPLv2"

S = "${WORKDIR}/git"

FILES_${PN} = "/etc /usr"

inherit autotools

EXTRA_OECONF = "\
	--with-libsdl=no --with-boxtype=${MACHINE} \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	--without-debug \
"

do_compile() {
	python -O -m compileall ${S}
}

python populate_packages_prepend() {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', 'Enigma2 Plugin: %s', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', 'Enigma2 Plugin: %s', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', 'Enigma2 Plugin: %s', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

pkg_postinst() {
#!/bin/sh
echo "Backup /usr/share/enigma2/keymap.xml in /usr/share/enigma2/keymap_backup_mqb.xml"
cp -f /usr/share/enigma2/keymap.xml /usr/share/enigma2/keymap_backup_mqb.xml
echo "Please restart your STB to load Multi QuickButton plugin"
exit 0
}

pkg_postrm() {
#!/bin/sh
echo "Restore flags in /usr/share/enigma2/keymap.xml"
sed -ie s!"<key id=\"KEY_TEXT\" mapto=\"startTeletext\" flags=\"b\" />"!"<key id=\"KEY_TEXT\" mapto=\"startTeletext\" flags=\"m\" />"!g "/usr/share/enigma2/keymap.xml"
sed -ie s!"<key id=\"KEY_HELP\" mapto=\"displayHelp\" flags=\"b\" />"!"<key id=\"KEY_HELP\" mapto=\"displayHelp\" flags=\"m\" />"!g "/usr/share/enigma2/keymap.xml"
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton > /dev/null 2>&1
rm -rf /etc/MultiQuickButton > /dev/null 2>&1
echo "Please restart your STB to remove Multi Quickbutton plugin"
exit 0
}
