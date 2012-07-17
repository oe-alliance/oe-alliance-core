DESCRIPTION = "Zonk.cc HD Picons (transparent)"

PICONS_FILENAME = "picons-hd-transparent_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-hd-transparent*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "20daa1cde22441ac78e5da07cee7cd92"
SRC_URI[sha256sum] = "06c06c2c43d29c5ced79e04abbb7a68b7fc593adb3a9ff5618e24bfb94790741"
