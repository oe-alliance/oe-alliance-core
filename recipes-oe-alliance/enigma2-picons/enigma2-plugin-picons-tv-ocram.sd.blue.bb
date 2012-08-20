DESCRIPTION = "Ocram SD Picons (blue)"


PICONS_FILENAME = "picons-sd-blue_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-blue*", d))
	except:
		pass
}

include enigma2-plugin-picons-ocram.inc

SRC_URI[md5sum] = "2776adbbc90b621e4a1567141427f685"
SRC_URI[sha256sum] = "faac278945cdca4f91d29a1aecc54682c74b91ca58d16164dce27f430bee563e"
