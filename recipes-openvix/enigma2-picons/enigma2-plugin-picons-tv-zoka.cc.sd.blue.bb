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

SRC_URI[md5sum] = "f7e95a66e5aa7b329fcf6b0236110157"
SRC_URI[sha256sum] = "35ace76f009895a50f9f8b06c29bf615ebca45bd35234f68af7d5926706712bc"
