DESCRIPTION = "Zonk.cc HD Picons (Reflection)"

PICONS_FILENAME = "picons-hd-reflection_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-hd-reflection*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "1fe4a3770f891e6cd69849f81fef11cd"
SRC_URI[sha256sum] = "50894046e0261b7e307d704063713fcdd11ae758ce05f4650e57262734b15fab"
