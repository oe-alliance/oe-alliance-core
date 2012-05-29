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

SRC_URI[md5sum] = "5fe4b76feef338f5694635c8806ac804"
SRC_URI[sha256sum] = "e0d4a0d50aa59ca9ccd4111c223de66f2920c47b25189feced3fee8f31e49a1c"
