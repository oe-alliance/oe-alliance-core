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

SRC_URI[md5sum] = "c83922aa4676835f599f423c055688e9"
SRC_URI[sha256sum] = "28e907af42af459fa465ccfa4eca43366d2208ae18dff3856797fae72ed7e1e2"
