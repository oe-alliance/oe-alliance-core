PRINC = "51"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git"

S="${WORKDIR}/git"

python do_package_prepend () {
	boxtypes = [
		('dm500hd', 'dm500hd.jpg', 'dm_normal.png'),
		('dm500hdv2', 'dm500hd.jpg', 'dm_normal.png'),
		('dm7020hd', 'dm7020hd.jpg', 'dm_normal.png'),
		('dm8000', 'dm8000.jpg', 'dm_normal.png'),
		('dm800se', 'dm800se.jpg', 'dm_normal.png'),
		('dm800sev2', 'dm800se.jpg', 'dm_normal.png'),
		('dm800', 'unknown.jpg', 'dm_normal.png'),
		('et4x00', 'et4x00.jpg', 'et_rc13_normal.png'),
		('et5x00', 'et5x00.jpg', 'et_rc5_normal.png'),
		('et6x00', 'et5x00.jpg', 'et_rc5_normal.png'),
		('et9x00', 'et9x00.jpg', 'et_rc7_normal.png'),
		('odinm7', 'odinm7.jpg', 'odinm7.png'),
		('odinm9', 'odinm9.jpg', 'odinm9.png'),
		('tmtwin', 'tmtwin.jpg', 'tm.png'),
		('tm2t', 'tm2t.jpg', 'tm.png'),
		('tmsingle', 'tmsingle.jpg', 'tm.png'),
		('tmnano', 'tmnano.jpg', 'tm.png'),
		('tmnano2t', 'tmnano2t.jpg', 'tm.png'),
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
		('gbquadplus', 'gbquad.jpg', 'gigablue_black.png'),
		('gb800seplus', 'gb800seplus.jpg', 'gigablue_black.png'),
		('gb800ueplus', 'gb800ueplus.jpg', 'gigablue_black.png'),
		('ventonhdx', 'ini-3000.jpg', 'ini-3000.png'),
		('inihde', 'ini-3000.jpg', 'ini-3000.png'),
		('inihdp', 'ini-3000.jpg', 'ini-3000.png'),
		('xp1000', 'xp1000.jpg', 'xp_rc14_normal.png'),
		('xp1000s', 'xp1000.jpg', 'xp_rc14_normal.png'),
		('ebox5000', 'ebox5000.jpg', 'ebox5000.png'),
		('ebox5100', 'ebox5100.jpg', 'ebox5000.png'),
		('eboxlumi', 'eboxlumi.jpg', 'ebox5000.png'),
		('ebox7358', 'ebox7358.jpg', 'ebox5000.png'),
		('ixussone', 'ixussone.jpg', 'ixussone.png'),
		('ixusszero', 'ixusszero.jpg', 'ixusszero.png'),
		('ixussduo', 'ixussone.jpg', 'ixussone.png'),
		('iqonios100hd', 'ios100hd.jpg', 'iqon.png'),
		('iqonios200hd', 'ios200hd.jpg', 'iqon.png'),
		('iqonios300hd', 'ios300hd.jpg', 'iqon.png'),
		('force1', 'ios200hd.jpg', 'iqon.png'),
		('mbtwin', 'ini-3000.jpg', 'ini-3000.png'),
		('mediabox', 'ios100hd.jpg', 'iqon.png'),
		('optimussos1', 'optimussos1.jpg', 'optimuss.png'),
		('optimussos2', 'optimussos2.jpg', 'optimuss.png'),
		('azboxme', 'me.jpg', 'me.png'),
		('azboxhd', 'premium.jpg', 'premium.png'),
		('azboxminime', 'minime.jpg', 'me.png'),
		('e3hd', 'e3hd.jpg', 'e3hd.png'),
		('cube', 'cube.jpg', 'cube.png'),
		('sogno8800hd', 'sogno8800hd.jpg', 'sogno.png'),
		('enfinity', 'enfinity.jpg', 'evo_small.png'),
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
					if name not in ('ini-1000.jpg', 'xpeedlx1.jpg', 'xpeedlx2.jpg' , 'ini-1000sv.jpg', 'ini-3000.jpg', 'ini-5000.jpg', 'ini-5000sv.jpg', 'ini-7000.jpg', 'sezam-1000.jpg', 'sezam-5000.jpg', 'sezam-9000.jpg'):
						os.remove(os.path.join(root, name))
				elif target_box == 'premium.jpg':
					if name not in ('elite.jpg', 'premium+.jpg', 'ultra.jpg'):
						os.remove(os.path.join(root, name))
				elif target_box == 'odinm7.jpg':
					if name not in ('starsatlx.jpg', 'axodin.jpg'):
						os.remove(os.path.join(root, name))
				else:
					os.remove(os.path.join(root, name))
	for root, dirs, files in os.walk(top + 'remotes', topdown=False):
		for name in files:
			if target_remote != name and name != 'ow_remote.png':
				if target_remote == 'ini-3000.png':
					if name not in ('ini-1000.png', 'ini-1000de.png', 'ini-3000.png', 'ini-5000.png', 'ini-7000.png', 'miraclebox.png', 'xpeedlx.png'):
						os.remove(os.path.join(root, name))
				elif target_remote == 'premium.png':
					if name != 'elite.png':
						os.remove(os.path.join(root, name))
				elif target_remote == 'odinm7.png':
					if name != 'starsatlx.png':
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
