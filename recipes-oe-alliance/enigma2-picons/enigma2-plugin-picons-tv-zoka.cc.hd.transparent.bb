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

SRC_URI[md5sum] = "2193a3bb1b4495343df9a2c9fefd0f2f"
SRC_URI[sha256sum] = "e572d97101c92a5df047c953d5795112ff2a3036848c96821d834c1cc0647a52"
