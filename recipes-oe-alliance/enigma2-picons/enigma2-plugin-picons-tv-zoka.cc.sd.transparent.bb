DESCRIPTION = "Zonk.cc SD Picons (transparent)"

PICONS_FILENAME = "picons-sd-transparent_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-transparent*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "82d55b71ea09a68049e91873b32293d8"
SRC_URI[sha256sum] = "646207e67ae86d230f8a73a2bb6013f68e3eefeecc314bb33549a5ebbd49a87e"
