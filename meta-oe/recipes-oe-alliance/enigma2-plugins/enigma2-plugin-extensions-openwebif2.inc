MODULE = "OpenWebif"
DESCRIPTION = "Control your receiver with a browser"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://README;firstline=10;lastline=12;md5=eb66cb719ed579d6523cf9c3e000d811"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

BRANCH="main"

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
	"

inherit gittag setuptools3-openplugins gettext

DISTUTILS_INSTALL_ARGS = "--root=${D} --install-lib=${libdir}/enigma2/python/Plugins"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/oe-alliance/${MODULE}.git;protocol=https;branch=${BRANCH} \
           file://setuptools-auto-discovery.patch"

S="${WORKDIR}/git"

# Just a quick hack to "compile" it
do_compile() {
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
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/public/vxg/.*$', 'enigma2-plugin-%s-vxg', '%s (WebTV for Google Chrome)', recursive=True, match_path=True, prepend=True, extra_depends="${PN}")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/controllers/views/.*\.tmpl$', 'enigma2-plugin-%s-src', '%s (Template sources for OpenWebif)', recursive=True, match_path=True, prepend=True)
}

FILES:${PN}-themes += " ${libdir}/enigma2/python/Plugins/Extensions/OpenWebif/public/modern \
                        ${libdir}/enigma2/python/Plugins/Extensions/OpenWebif/controllers/views/responsive \
"

RPROVIDES:${PN}  = "${PN}-terminal ${PN}-webtv"
RREPLACES:${PN}  = "${PN}-terminal ${PN}-webtv"
RCONFLICTS:${PN} = "${PN}-terminal ${PN}-webtv"

INSANE_SKIP:${PN} += "build-deps"
INSANE_SKIP:${PN}-vxg += "build-deps"

PACKAGES =+ "${PN}-themes"
RDEPENDS:${PN}-themes = "${PN}"
ALLOW_EMPTY:${PN}-themes = "1"
