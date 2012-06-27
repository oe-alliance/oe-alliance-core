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

SRC_URI[md5sum] = "8320b5e4c86056b11990567a3415ee9b"
SRC_URI[sha256sum] = "81590b207bd7bd7b538eab3e6b7c7cf2bf55fe20fc252d13cd59fdcaa5991ead"
