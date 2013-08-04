MODULE = "CrossEPG"
PRINC = "6"

# Dunno why, but it sometime fails to build in parallel
PARALLEL_MAKE = ""

DEPENDS += "python-native"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git"

S = "${WORKDIR}/git"

pkg_postrm() {
rm -fr /usr/lib/enigma2/python/Plugins/SystemPlugins/CrossEPG > /dev/null 2>&1
}

FILES_${PN} += " /usr/crossepg /usr/python2.7"
FILES_${PN}-src += " /usr/lib/python2.7/crossepg.py"

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
