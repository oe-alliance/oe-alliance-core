DESCRIPTION = "Ocram SD Picons (black)"

PICONS_FILENAME = "picons-sd-black_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-black*", d))
	except:
		pass
}

include enigma2-plugin-picons-ocram.inc

SRC_URI[md5sum] = "72248d946267f02fa7dfb73d2f2a517d"
SRC_URI[sha256sum] = "86c81d8a7b2cde12341ddd5effa423b29cacb540b4baa1adeaed4b03a5e2a82a"
