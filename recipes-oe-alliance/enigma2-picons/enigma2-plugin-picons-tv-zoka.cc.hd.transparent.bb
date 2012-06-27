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

SRC_URI[md5sum] = "cb3be90fc4bf786171e33c3a095236f3"
SRC_URI[sha256sum] = "dd6baba5fcb32614f400f803710c1973d0d8b12679e70f289a37c1c66a2e7314"
