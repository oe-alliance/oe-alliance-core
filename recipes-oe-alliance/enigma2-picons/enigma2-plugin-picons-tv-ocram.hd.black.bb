DESCRIPTION = "Ocram HD Picons (black)"

PICONS_FILENAME = "picons-hd-black_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-hd-black*", d))
	except:
		pass
}

include enigma2-plugin-picons-ocram.inc

SRC_URI[md5sum] = "b42ad454142ae3a00f22daf93b3a505d"
SRC_URI[sha256sum] = "4dfe09459215e00bf63d922a212c8ff23e11ec9a8e11f33114e562189d1f363c"
