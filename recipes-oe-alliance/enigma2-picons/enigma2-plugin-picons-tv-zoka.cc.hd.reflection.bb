DESCRIPTION = "Zonk.cc HD Picons (Reflection)"

PICONS_FILENAME = "picons-hd-reflection_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-hd-reflection*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "c8cc5ada0a75f3741ee632a5a84ec31d"
SRC_URI[sha256sum] = "974997a6ef7bd9f5cbdd3982871ef917c150a8bc89717e121fd7da411eb3f85c"
