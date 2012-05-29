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

SRC_URI[md5sum] = "98e77ce8eca314c63a3a8e12e90e2527"
SRC_URI[sha256sum] = "a35e15c55f1f586cfa6c29203d8c9a579916df50b51351a34b2c63aafd2558ef"
