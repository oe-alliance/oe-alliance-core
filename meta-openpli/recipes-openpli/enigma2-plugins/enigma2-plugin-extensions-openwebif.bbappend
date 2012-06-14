PRINC = "1"

python do_package_prepend () {
	boxtypes = [
		('dm500hd', 'dm500hd.jpg', 'dm_normal.png'),
		('dm7020hd', 'unknown.jpg', 'dm_normal.png'),
		('dm8000', 'dm8000.jpg', 'dm_normal.png'),
		('dm800se', 'dm800se.jpg', 'dm_normal.png'),
		('et5x00', 'et5x00.jpg', 'et_rc5_normal.png'),
		('et6x00', 'et5x00.jpg', 'et_rc5_normal.png'),
		('et9x00', 'et9x00.jpg', 'et_rc7_normal.png'),
		('vuduo', 'duo.jpg', 'vu_normal.png'),
		('vusolo', 'solo.jpg', 'vu_normal.png'),
		('vuultimo', 'ultimo.jpg', 'vu_ultimo.png'),
		('vuuno', 'uno.jpg', 'vu_normal.png'),
	]
	import os
	top = '${D}${PLUGINPATH}/public/images/'
	target_box = 'unknown.jpg'
	target_remote = 'ow_remote.png'
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
