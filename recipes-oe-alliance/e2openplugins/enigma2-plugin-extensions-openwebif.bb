MODULE = "OpenWebif"
DESCRIPTION = "Control your receiver with a browser"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;firstline=10;lastline=12;md5=9c14f792d0aeb54e15490a28c89087f7"

DEPENDS = "enigma2 python-cheetah-native"
RDEPENDS_${PN} = "python-cheetah python-json python-unixadmin python-misc aio-grab"

inherit gitpkgv
SRCREV_pn-${PN} ?= "${AUTOREV}"
PV = "0.1+git${SRCPV}"
PKGV = "0.1+git${GITPKGV}"
PR = "r2"

# Just a quick hack to "compile" it
do_compile() {
	cheetah-compile -R --nobackup ${S}/plugin
	python -O -m compileall ${S}
}

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git"

S="${WORKDIR}/git"

python do_package_prepend () {
	boxtypes = [
		('dm500hd', 'dm500hd.jpg', 'dm_normal.png'),
		('dm7020hd', 'unknown.jpg', 'dm_normal.png'),
		('dm8000', 'dm8000.jpg', 'dm_normal.png'),
		('dm800se', 'dm800se.jpg', 'dm_normal.png'),
		('et5x00', 'et5x00.jpg', 'et_rc5_normal.png'),
		('et6x00', 'et5x00.jpg', 'et_rc5_normal.png'),
		('et9x00', 'et9x00.jpg', 'et_rc7_normal.png'),
		('odinm9', 'et9x00.jpg', 'et_rc7_normal.png'),
		('tmtwin', 'twin.jpg', 'tm_twin.png'),
		('vuduo', 'duo.jpg', 'vu_normal.png'),
		('vusolo', 'solo.jpg', 'vu_normal.png'),
		('vuultimo', 'ultimo.jpg', 'vu_ultimo.png'),
		('vuuno', 'uno.jpg', 'vu_normal.png'),
		('gb800se', 'Gigablue.jpg', 'gigablue_black.png'),
		('gb800solo', 'Gigablue.jpg', 'gigablue_black.png'),
		('gb800ue', 'Gigablue.jpg', 'gigablue_black.png'),
		('gbquad', 'Gigablue.jpg', 'gigablue_black.png'),
		('ventonhdx', 'ini-3000', 'ini-3000.png'),
	]
	import os
	top = '${D}${PLUGINPATH}/public/images/'
	for x in boxtypes:
		if x[0] == '${MACHINE}':
			target_box = x[1]
			target_remote = x[2]
			break
	for root, dirs, files in os.walk(top + 'boxes', topdown=False):
		for name in files:
			if target_box != name and name != 'unknown.jpg':
				os.remove(os.path.join(root, name))
	for root, dirs, files in os.walk(top + 'remotes', topdown=False):
		for name in files:
			if target_remote != name and name != 'ow_remote.png':
				os.remove(os.path.join(root, name))
}

PLUGINPATH = "/usr/lib/enigma2/python/Plugins/Extensions/${MODULE}"
do_install() {
	install -d ${D}${PLUGINPATH}
	cp -rp ${S}/plugin/* ${D}${PLUGINPATH}
}

FILES_${PN} = "${PLUGINPATH}"

