PRINC = "1"

DEPENDS += "python-native"

SRC_URI = "git://github.com/OpenViX/e2openplugin-CrossEPG.git;protocol=git"

pkg_postrm() {
rm -fr /usr/lib/enigma2/python/Plugins/SystemPlugins/CrossEPG > /dev/null 2>&1
}
