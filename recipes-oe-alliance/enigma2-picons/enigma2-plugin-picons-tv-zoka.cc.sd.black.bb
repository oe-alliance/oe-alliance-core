DESCRIPTION = "Zonk.cc SD Picons (Black)"

PICONS_FILENAME = "picons-sd-black_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-black*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "5f362fefc2fde4d38ca8156cd834d3d8"
SRC_URI[sha256sum] = "6521b2fbb92496e7f3a33b0286d6be482943ad0edf507e73706d8dd8502ab3ce"
