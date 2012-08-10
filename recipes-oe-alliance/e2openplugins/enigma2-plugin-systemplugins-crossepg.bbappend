MODULE = "CrossEPG"
PRINC = "2"

# Dunno why, but it sometime fails to build in parallel
PARALLEL_MAKE = ""

DEPENDS += "python-native"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git"

pkg_postrm() {
rm -fr /usr/lib/enigma2/python/Plugins/SystemPlugins/CrossEPG > /dev/null 2>&1
}
