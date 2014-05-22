MODULE = "OpenWebif"
DESCRIPTION = "Control your receiver with a browser"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;firstline=10;lastline=12;md5=9c14f792d0aeb54e15490a28c89087f7"

DEPENDS = "python-cheetah-native"
RDEPENDS_${PN} = "python-cheetah python-compression python-json python-unixadmin python-misc python-pyopenssl python-shell aio-grab oe-alliance-branding"

inherit gitpkgv pythonnative

SRCREV = "${AUTOREV}"
PV = "1+git${SRCPV}"
PKGV = "1+git${GITPKGV}"

PACKAGE_ARCH = "${MACHINEBUILD}"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git;branch=branding"

S="${WORKDIR}/git"

# Just a quick hack to "compile" it
do_compile() {
    cheetah-compile -R --nobackup ${S}/plugin
    python -O -m compileall ${S}
}

PLUGINPATH = "/usr/lib/enigma2/python/Plugins/Extensions/${MODULE}"
do_install_append() {
    install -d ${D}${PLUGINPATH}
    cp -rp ${S}/plugin/* ${D}${PLUGINPATH}
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
