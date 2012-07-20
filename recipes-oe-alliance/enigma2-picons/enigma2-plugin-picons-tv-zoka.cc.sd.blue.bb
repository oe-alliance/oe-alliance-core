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

SRC_URI[md5sum] = "80b97ad4ea5bd4e46c2b59b38a8ff2f6"
SRC_URI[sha256sum] = "0008aa3489e3fa1423aa3c8e25e9bc6c3a6697b2a1f7798f67f14251a02e69d3"
