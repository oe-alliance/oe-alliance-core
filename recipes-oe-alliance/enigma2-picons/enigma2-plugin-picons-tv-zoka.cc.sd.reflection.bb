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

SRC_URI[md5sum] = "746834f1469f38163c6233c16e6d7ab1"
SRC_URI[sha256sum] = "8f603638c7d37c3f2b96918d142c00e10cf8c6fe626c27c20c08fe24e58a0d46"
