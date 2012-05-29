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

SRC_URI[md5sum] = "cdc2b62ec03301407003b0833b5ce456"
SRC_URI[sha256sum] = "409be011f6746d65f3a5b14eae0b7d34fcc42a7ef1a1c294b5ff32ac6b88ee92"
