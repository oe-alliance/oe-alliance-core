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

SRC_URI[md5sum] = "8aa7794040627bd0bf6121203c3e768e"
SRC_URI[sha256sum] = "ab28a502d7dbac5c369b0c50f59f310ab34709e25c72e6def9c97464bbea92e5"
