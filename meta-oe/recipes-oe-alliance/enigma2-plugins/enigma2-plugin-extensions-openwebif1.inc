MODULE = "OpenWebif"
DESCRIPTION = "Control your receiver with a browser"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://README;firstline=10;lastline=12;md5=26abba37d1c2fcbf96a087ceb8e1db86"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

BRANCH="master"

DEPENDS += "${PYTHON_PN}-cheetah-native"
RDEPENDS:${PN} = "\
	aio-grab \
	${PYTHON_PN}-cheetah \
	${PYTHON_PN}-compression \
	${PYTHON_PN}-ipaddress \
	${PYTHON_PN}-json \
	${PYTHON_PN}-misc \
	${PYTHON_PN}-numbers \
	${PYTHON_PN}-pyopenssl \
	${PYTHON_PN}-pprint \
	${PYTHON_PN}-shell \
	${PYTHON_PN}-twisted-web \
	${PYTHON_PN}-unixadmin \
	oe-alliance-branding \
	"

inherit gittag setuptools3-openplugins gettext

DISTUTILS_INSTALL_ARGS = "--root=${D} --install-lib=${libdir}/enigma2/python/Plugins"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "${@bb.utils.contains_any('DISTRO', 'openhdf teamblue', 'git://github.com/openhdf/OpenWebif.git;protocol=https;branch=${BRANCH}', \
                'git://github.com/E2OpenPlugins/e2openplugin-${MODULE}.git;protocol=https;branch=${BRANCH} file://transcoding.py', d)} \
           file://setuptools-auto-discovery.patch"

S="${WORKDIR}/git"

# Just a quick hack to "compile" it
do_compile() {
	cp ${WORKDIR}/transcoding.py ${S}/plugin/controllers/transcoding.py >/dev/null 2>&1 || true
	rm -rf ${S}/plugin/public/static/remotes >/dev/null 2>&1 || true
	find ${S}/plugin/public/ -empty -type d -delete >/dev/null 2>&1 || true
	find ${S}/plugin/public/images/boxes/ ! -name 'unknown.png' -type f -exec rm -f {} +
	find ${S}/plugin/public/images/remotes/ ! -name 'ow_remote.png' -type f -exec rm -f {} +
	cheetah-compile -R --nobackup ${S}/plugin
}

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/${MODULE}"
do_install:append() {
	install -d ${D}${PLUGINPATH}
	cp -r ${S}/plugin/* ${D}${PLUGINPATH}
	chmod a+rX ${D}${PLUGINPATH}
}

require conf/python/python3-compileall.inc

python populate_packages:prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/public/themes/.*$', 'enigma2-plugin-%s-themes', '%s (Additional themes for OpenWebif)', recursive=True, match_path=True, prepend=True, extra_depends="${PN}")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/public/webtv/.*$', 'enigma2-plugin-%s-webtv', '%s (WebTV for OpenWebif)', recursive=True, match_path=True, prepend=True, extra_depends="${PN}")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/public/vxg/.*$', 'enigma2-plugin-%s-vxg', '%s (WebTV for Google Chrome)', recursive=True, match_path=True, prepend=True, extra_depends="${PN}-webtv")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.pexe$', 'enigma2-plugin-%s-vxg', '%s (WebTV support for Google Chrome)', recursive=True, match_path=True, prepend=True, extra_depends="${PN}-webtv")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

INSANE_SKIP:${PN} += "build-deps"
INSANE_SKIP:${PN}-terminal += "build-deps"
INSANE_SKIP:${PN}-vxg += "build-deps"

# Required empty packages for build compatibility with distros still using OWIF 0.x.y - 1.0.z
PACKAGES =+ "${PN}-terminal ${PN}-themes ${PN}-webtv"
RDEPENDS:${PN}-terminal = "${PN} shellinabox"
RDEPENDS:${PN}-themes = "${PN}"
RDEPENDS:${PN}-webtv = "${PN}"
ALLOW_EMPTY:${PN}-terminal = "1"
ALLOW_EMPTY:${PN}-themes = "1"
ALLOW_EMPTY:${PN}-webtv = "1"
