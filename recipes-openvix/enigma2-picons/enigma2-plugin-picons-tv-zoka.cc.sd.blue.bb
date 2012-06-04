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

SRC_URI[md5sum] = "226428389ecf576021f765f5a2f62011"
SRC_URI[sha256sum] = "22a7d21249859062f7ce064c3202e90c317c0cc9131574023aa767948a0fc537"
