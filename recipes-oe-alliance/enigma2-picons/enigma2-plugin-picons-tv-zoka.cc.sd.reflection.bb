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

SRC_URI[md5sum] = "bfb0d95a9230aeed65283787960cf99b"
SRC_URI[sha256sum] = "fc825abe7755b140811a418610a15bf0baecb3e5a566b5dbd0372cb4e961d4ae"
