DESCRIPTION = "Zonk.cc SD Picons (Blue)"

PICONS_FILENAME = "picons-sd-blue_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-blue*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "7d2d9edf62acf3b05f84a050608a8d9b"
SRC_URI[sha256sum] = "8a891a033c3e9b9cc5d8cb90d2d09614a4ec0562818efe17f76148f3db50c7a3"
