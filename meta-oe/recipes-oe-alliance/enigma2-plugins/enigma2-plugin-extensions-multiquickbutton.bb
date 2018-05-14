MODULE = "MultiQuickButton"
SUMMARY = "Multi Quickbutton editor/wizard/code interpreter for keyboard and RC ViX Version"
SECTION = "extra"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../LICENSE.GPLv2;md5=eb723b61539feef013de476e68b5c50a"

DEPENDS = "python"

inherit gitpkgv pythonnative gettext

SRCREV = "${AUTOREV}"
PV = "2.8.4+git${SRCPV}"
PKGV = "2.8.4+git${GITPKGV}"
PR = "r9"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git \
        file://LICENSE.GPLv2"

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/tmp /etc ${libdir}"
FILES_${PN}-src = "${libdir}/enigma2/python/Plugins/Extensions/MultiQuickButton/*.py"
FILES_${PN}-po = "${libdir}/enigma2/python/Plugins/Extensions/MultiQuickButton/locale/*.po"

inherit autotools-brokensep

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

pkg_postinst_${PN}() {
#!/bin/sh
if ! test -d /etc/MultiQuickButton; then
    mkdir /etc/MultiQuickButton
fi
cd /tmp/mqb
for buttonfile in *.xml; do
    if ! test -f /etc/MultiQuickButton/$buttonfile; then
        cp /tmp/mqb/$buttonfile /etc/MultiQuickButton
    fi
done
cd /
rm -rf /tmp/mqb

echo "Backup /usr/share/enigma2/keymap.xml in /usr/share/enigma2/keymap_backup_mqb.xml"
cp /usr/share/enigma2/keymap.xml /usr/share/enigma2/keymap_backup_mqb.xml

echo "Please restart your STB to load Menu Multi QuickButton Plugin ..."
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
echo "... Restore flags in /usr/share/enigma2/keymap.xml..."
sed -ie s!"<key id=\"KEY_TEXT\" mapto=\"startTeletext\" flags=\"b\" />"!"<key id=\"KEY_TEXT\" mapto=\"startTeletext\" flags=\"m\" />"!g "/usr/share/enigma2/keymap.xml"
sed -ie s!"<key id=\"KEY_HELP\" mapto=\"displayHelp\" flags=\"b\" />"!"<key id=\"KEY_HELP\" mapto=\"displayHelp\" flags=\"m\" />"!g "/usr/share/enigma2/keymap.xml"
rm -r /usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton > /dev/null 2>&1
rm -r /etc/MultiQuickButton > /dev/null 2>&1

echo "Please restart your STB to kick ass Multi Quickbutton Plugin to nirvana..."
exit 0
}
