MODULE = "OpenWebif"
PRINC = "21"
DEPENDS += "enigma2 python-pyopenssl"
RDEPENDS_${PN} += " python-pyopenssl"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git"
SRC_URI_append_vusolo2 = " file://openwebif_transcoding.patch"
SRC_URI_append_vuduo2 = " file://openwebif_transcoding.patch"

S="${WORKDIR}/git"

python do_package_prepend () {
	boxtypes = [
		('dm500hd', 'dm500hd.jpg', 'dm_normal.png'),
		('dm7020hd', 'unknown.jpg', 'dm_normal.png'),
		('dm8000', 'dm8000.jpg', 'dm_normal.png'),
		('dm800se', 'dm800se.jpg', 'dm_normal.png'),
		('dm800', 'unknown.jpg', 'dm_normal.png'),
		('et4x00', 'et4x00.jpg', 'et_rc13_normal.png'),
		('et5x00', 'et5x00.jpg', 'et_rc5_normal.png'),
		('et6x00', 'et5x00.jpg', 'et_rc5_normal.png'),
		('et9x00', 'et9x00.jpg', 'et_rc7_normal.png'),
		('odinm9', 'odinm9.jpg', 'odinm9.png'),
		('tmtwin', 'tmtwin.jpg', 'tm_twin.png'),
		('tm2t', 'tm2t.jpg', 'tm_2t.png'),
		('tmsingle', 'tmsingle.jpg', 'tm_single.png'),
		('vuduo', 'duo.jpg', 'vu_normal.png'),
		('vuduo2', 'duo2.jpg', 'vu_normal.png'),
		('vusolo', 'solo.jpg', 'vu_normal.png'),
		('vusolo2', 'solo2.jpg', 'vu_normal.png'),
		('vuultimo', 'ultimo.jpg', 'vu_ultimo.png'),
		('vuuno', 'uno.jpg', 'vu_normal.png'),
		('gb800se', 'gb800se.jpg', 'gigablue_black.png'),
		('gb800solo', 'gb800solo.jpg', 'gigablue_black.png'),
		('gb800ue', 'gb800ue.jpg', 'gigablue_black.png'),
		('gbquad', 'gbquad.jpg', 'gigablue_black.png'),
		('ventonhdx', 'ini-3000.jpg', 'ini-3000.png'),
		('ventonhde', 'ini-3000.jpg', 'ini-3000.png'),
		('xp1000', 'xp1000.jpg', 'xp_rc14_normal.png'),
		('ebox5000', 'ebox5000.jpg', 'ebox5000.png'),
		('ebox7358', 'ebox5000.jpg', 'ebox5000.png'),		
		('odinm7', 'odinm7.jpg', 'odinm7.png'),
		('ixussone', 'ixussone.jpg', 'ixussone.png'),
		('ixusszero', 'ixusszero.jpg', 'ixusszero.png'),
		('ixussduo', 'ixussone.jpg', 'ixussone.png'),		
		('iqonios100hd', 'ios100hd.jpg', 'iqon.png'),
		('iqonios200hd', 'ios200hd.jpg', 'iqon.png'),
		('iqonios300hd', 'ios300hd.jpg', 'iqon.png'),
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
				if target_box == 'ini-3000.jpg':
					if not (name == 'ini-3000.jpg' or name == 'ini-5000.jpg' or name == 'ini-7000.jpg'):
						os.remove(os.path.join(root, name))
				else:
					os.remove(os.path.join(root, name))
	for root, dirs, files in os.walk(top + 'remotes', topdown=False):
		for name in files:
			if target_remote != name and name != 'ow_remote.png':
				if target_remote == 'ini-3000.png':
					if not (name == 'ini-3000.png' or name == 'ini-5000.png' or name == 'ini-7000.png'):
						os.remove(os.path.join(root, name))
				else:
					os.remove(os.path.join(root, name))
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
