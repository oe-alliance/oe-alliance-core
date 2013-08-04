MODULE = "MultiQuickButton-vu"
DESCRIPTION = "Multi Quickbutton editor/wizard/code interpreter for keyboard and RC VU+ Version"
ARCHITECTURE = "mipsel"
SECTION = "extra"
PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gitpkgv
SRCREV = ""
PV = "2.7.12+git${SRCPV}"
PKGV = "2.7.12+git${GITPKGV}"
PR = "r0"

require openplugins.inc

inherit autotools

require assume-gplv2.inc

do_install_append() {
	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton
	rm ${D}/usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/function.py
	rm ${D}/usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/function.pyo
}


pkg_postinst_${PN}() {
#!/bin/sh

if ! test -d /etc/MultiQuickButton
	then
		mkdir /etc/MultiQuickButton
fi


cd /tmp/mqb

for buttonfile in *.xml
do
	if ! test -f /etc/MultiQuickButton/$buttonfile
		then
			cp /tmp/mqb/$buttonfile /etc/MultiQuickButton
	fi
done

cd /

rm -rf /tmp/mqb

echo "Backup /usr/share/enigma2/keymap.xml in /usr/share/enigma2/keymap_backup_mqb.xml"
cp /usr/share/enigma2/keymap.xml /usr/share/enigma2/keymap_backup_mqb.xml

echo "Setting flags in /usr/share/enigma2/keymap.xml ..."
sed -ie s!"<key id=\"KEY_YELLOW\" mapto=\"timeshiftStart\" flags=\"m\" />"!"<key id=\"KEY_YELLOW\" mapto=\"timeshiftStart\" flags=\"b\" />"!g "/usr/share/enigma2/keymap.xml"
sed -ie s!"<key id=\"KEY_YELLOW\" mapto=\"timeshiftActivateEndAndPause\" flags=\"m\" />"!"<key id=\"KEY_YELLOW\" mapto=\"timeshiftActivateEndAndPause\" flags=\"b\" />"!g "/usr/share/enigma2/keymap.xml"
sed -ie s!"<key id=\"KEY_VIDEO\" mapto=\"showMovies\" flags=\"m\" />"!"<key id=\"KEY_VIDEO\" mapto=\"showMovies\" flags=\"b\" />"!g "/usr/share/enigma2/keymap.xml"
sed -ie s!"<key id=\"KEY_RADIO\" mapto=\"showRadio\" flags=\"m\" />"!"<key id=\"KEY_RADIO\" mapto=\"showRadio\" flags=\"b\" />"!g "/usr/share/enigma2/keymap.xml"
sed -ie s!"<key id=\"KEY_TEXT\" mapto=\"startTeletext\" flags=\"m\" />"!"<key id=\"KEY_TEXT\" mapto=\"startTeletext\" flags=\"b\" />"!g "/usr/share/enigma2/keymap.xml"
sed -ie s!"<key id=\"KEY_HELP\" mapto=\"displayHelp\" flags=\"m\" />"!"<key id=\"KEY_HELP\" mapto=\"displayHelp\" flags=\"b\" />"!g "/usr/share/enigma2/keymap.xml"

echo "... deactivating timeshift buttons YELLOW/RED not needed for VU+"
grep -q '<!-- <key id="KEY_RED" mapto="timeshiftActivateEnd" flags="b" /> -->' /usr/share/enigma2/keymap.xml
if [ $? -eq 0 ]
    then
	echo "timeshift button RED already deactivated"
    else
	sed -ie s!"<key id=\"KEY_RED\" mapto=\"timeshiftActivateEnd\" flags=\"b\" />"!"<\!\-\- <key id=\"KEY_RED\" mapto=\"timeshiftActivateEnd\" flags=\"b\" /> \-\->"!g "/usr/share/enigma2/keymap.xml"
fi

grep -q '<!-- <key id="KEY_YELLOW" mapto="timeshiftActivateEndAndPause" flags="b" /> -->' /usr/share/enigma2/keymap.xml
if [ $? -eq 0 ]
    then
	echo "timeshift button YELLOW already deactivated"
    else
	sed -ie s!"<key id=\"KEY_YELLOW\" mapto=\"timeshiftActivateEndAndPause\" flags=\"b\" />"!"<\!\-\- <key id=\"KEY_YELLOW\" mapto=\"timeshiftActivateEndAndPause\" flags=\"b\" /> \-\->"!g "/usr/share/enigma2/keymap.xml"
fi

grep -q 'config.plugins.QuickButton.okexitstate=true' /etc/enigma2/settings
if [ $? -eq 0 ]
    then
	echo "Ok/Exit state found in /etc/enigma2/settings => activate ok/exit buttons"
	cp -f /usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/keymap_ok.xml /usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/keymap.xml
fi

echo "Please restart VU+ STB to load Menu Multi QuickButton Plugin ..."
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
echo "... activating Timeshift Buttons YELLOW/RED"
sed -ie s!"<\!\-\- <key id=\"KEY_RED\" mapto=\"timeshiftActivateEnd\" flags=\"b\" /> \-\->"!"<key id=\"KEY_RED\" mapto=\"timeshiftActivateEnd\" flags=\"b\" />"!g "/usr/share/enigma2/keymap.xml"
sed -ie s!"<\!\-\- <key id=\"KEY_YELLOW\" mapto=\"timeshiftActivateEndAndPause\" flags=\"b\" /> \-\->"!"<key id=\"KEY_YELLOW\" mapto=\"timeshiftActivateEndAndPause\" flags=\"b\" />"!g "/usr/share/enigma2/keymap.xml"
rm -r /usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton > /dev/null 2>&1
rm -r /etc/MultiQuickButton > /dev/null 2>&1

echo "Please restart VU+ STB to kick ass Multi Quickbutton Plugin to nirvana..."
exit 0
}

FILES_${PN} = "/"
