MODULE = "Gigablue MultiQuickButton"
SUMMARY = "Multi Quickbutton editor/wizard/code interpreter for keyboard and RC" 
SECTION = "extra"
PRIORITY = "optional"
LICENSE = "GPLv2"

RCONFLICTS_${PN} = "gb-multiquickbutton"
RREPLACES_${PN} = "gb-multiquickbutton"

DEPENDS = "enigma2"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "2.7.11+git${SRCPV}"
PKGV = "2.7.11+git${GITPKGV}"
PR = "r3"

SRC_URI = "git://github.com/openmips/MultiQuickButton.git;protocol=git"

S = "${WORKDIR}/git"

require conf/license/license-gplv2.inc

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/tmp /etc /usr/lib"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/*.py"
FILES_${PN}-po = "/usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/locale/*.po"

inherit autotools-brokensep

EXTRA_OECONF = "\
    --with-libsdl=no --with-boxtype=${MACHINE} --with-po \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

# remove unused .pyc files
do_install_append() {
    find ${D}/usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/ -name '*.pyc' -exec rm {} \;
}

pkg_postinst_${PN}() {
#!/bin/sh

if ! test -d /etc/MultiQuickButton; then
    mkdir /etc/MultiQuickButton
fi


cd /tmp/mqb

for buttonfile in *.xml
do
    if ! test -f /etc/MultiQuickButton/$buttonfile; then
        cp /tmp/mqb/$buttonfile /etc/MultiQuickButton
    fi
done

cd /

rm -rf /tmp/mqb

echo "Backup /usr/share/enigma2/keymap.xml in /usr/share/enigma2/keymap_backup_mqb.xml"
cp /usr/share/enigma2/keymap.xml /usr/share/enigma2/keymap_backup_mqb.xml

echo "Setting flags in /usr/share/enigma2/keymap.xml ..."
sed -ie s!"<key id=\"KEY_TEXT\" mapto=\"startTeletext\" flags=\"m\" />"!"<key id=\"KEY_TEXT\" mapto=\"startTeletext\" flags=\"b\" />"!g "/usr/share/enigma2/keymap.xml"
sed -ie s!"<key id=\"KEY_HELP\" mapto=\"displayHelp\" flags=\"m\" />"!"<key id=\"KEY_HELP\" mapto=\"displayHelp\" flags=\"b\" />"!g "/usr/share/enigma2/keymap.xml"

grep -q 'config.plugins.QuickButton.okexitstate=true' /etc/enigma2/settings
if [ $? -eq 0 ]
    then
    echo "Ok/Exit state found in /etc/enigma2/settings => activate ok/exit buttons"
    cp -f /usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/keymap_ok.xml /usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/keymap.xml
fi

echo "Please restart your STB to load Menu Gigablue Multi QuickButton Plugin ..."
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
