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

SRC_URI[md5sum] = "0d9be7c5bc4fdaa88b94823a6f4e2718"
SRC_URI[sha256sum] = "4dfcbab9e3b18ae6572ba3571bc8d6a613171798f657a80976a5d63b1b2c0591"
