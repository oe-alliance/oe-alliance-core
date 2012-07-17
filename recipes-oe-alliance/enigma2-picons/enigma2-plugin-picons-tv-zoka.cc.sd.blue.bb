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

SRC_URI[md5sum] = "cc78b48944d25eeaf9547af8f0bc5b58"
SRC_URI[sha256sum] = "b6013a6559e4a128639c251a2e3b987353ffbb1a3cc59daa54d9c993e0d0576c"
