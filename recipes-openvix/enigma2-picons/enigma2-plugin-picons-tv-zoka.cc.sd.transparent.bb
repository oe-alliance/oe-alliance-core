DESCRIPTION = "Zonk.cc SD Picons (transparent)"

PICONS_FILENAME = "picons-sd-transparent_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-transparent*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "38bad8fe2e7b2668b66fab81eea801f7"
SRC_URI[sha256sum] = "a3ba4273cb4f8554882d538767734222418451137055ae5dc582f71823a15522"
