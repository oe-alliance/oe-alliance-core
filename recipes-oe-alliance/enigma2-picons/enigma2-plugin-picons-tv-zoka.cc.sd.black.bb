DESCRIPTION = "Zonk.cc SD Picons (Black)"

PICONS_FILENAME = "picons-sd-black_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-black*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "6a50608e97e71276e706ad27c5919f3c"
SRC_URI[sha256sum] = "b12a89af38770a48d21e79a7e07b16df1f91c7e40dbf9509f0820ea48b2fc64a"
