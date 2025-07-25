MODULE = "YouTube"
DESCRIPTION = "Enigma2 plugin to manage your youtube account and wach videos"
MAINTAINER = "Taapat"

require conf/license/license-gplv2.inc

DEPENDS += "enigma2"
RDEPENDS:${PN} = "python3-core python3-datetime python3-email python3-io python3-json"

inherit python3-dir gitpkgv python3native gettext

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"

SRC_URI = "git://github.com/Taapat/enigma2-plugin-youtube.git;protocol=https;branch=master \
    file://0001-distutils-are-outdated-use-setuptools.patch \
"

# Just a quick hack to "compile" it
do_compile() {
    cd ${S}
    for f in $(find ./po -name *.po ); do
        l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*po\///')
        mkdir -p ${S}/src/locale/${l%}/LC_MESSAGES
        msgfmt -o ${S}/src/locale/${l%}/LC_MESSAGES/YouTube.mo ./po/$l.po
    done
    cd -
}

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/${MODULE}"
do_install:append() {
    install -d ${D}${PLUGINPATH}
    cp --no-preserve=ownership --recursive ${S}/src/* ${D}${PLUGINPATH}
    cp --no-preserve=ownership --recursive ${S}/po/* ${D}${PLUGINPATH}/locale
    install -d ${D}/etc/enigma2
    install -m 0644 ${S}/YouTube.key ${D}/etc/enigma2/YouTube.key
}

require conf/python/python3-compileall.inc

CONFFILES = "/etc/enigma2/YouTube.key"

python populate_packages:prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.pot$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

do_package_qa[noexec] = "1"
