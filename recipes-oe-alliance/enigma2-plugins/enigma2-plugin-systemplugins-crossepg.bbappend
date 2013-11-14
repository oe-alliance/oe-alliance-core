PACKAGE_ARCH = "${MACHINE_ARCH}"
SRCREV = "${AUTOREV}"
PV = "0.7.04"
PKGV = "${PV}+git${GITPKGV}"
PRINC = "5"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git"

# Dunno why, but it sometime fails to build in parallel
PARALLEL_MAKE = ""
CFLAGS_append = " -I${STAGING_INCDIR}/libxml2/ -I${STAGING_INCDIR}/${PYTHON_DIR}/"
CFLAGS_append = " ${@base_contains('MACHINE_BRAND', 'XTrend', ' -DNO_DVB_POLL' , '', d)}"
CFLAGS_append = " ${@base_contains('MACHINE_BRAND', 'MaxDigital', ' -DNO_DVB_POLL' , '', d)}"

S = "${WORKDIR}/git"

do_compile() {
	echo ${PV} > ${S}/VERSION
	oe_runmake SWIG="swig"
}

do_install() {
	oe_runmake 'D=${D}' install
}

pkg_postrm() {
rm -fr /usr/lib/enigma2/python/Plugins/SystemPlugins/CrossEPG > /dev/null 2>&1
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

FILES_enigma2-plugin-systemplugins-crossepg += " /usr/crossepg /usr/python2.7"
FILES_enigma2-plugin-systemplugins-crossepg-src += " /usr/lib/python2.7/crossepg.py"
FILES_enigma2-plugin-systemplugins-crossepg-dbg += "/usr/crossepg/scripts/mhw2epgdownloader/.debug"
