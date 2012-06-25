DESCRIPTION = "Zonk.cc SD Picons (Reflection)"

PICONS_FILENAME = "picons-sd-reflection_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-reflection*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "26d94379a327403cbe575d21bca64e34"
SRC_URI[sha256sum] = "9752662c7b6773b1211782203d88498e4f12ad0bce398cd2fca27c08c5342854"
