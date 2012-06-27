DESCRIPTION = "Zonk.cc SD Picons (Reflection)"

PICONS_FILENAME = "picons-sd-reflection_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-reflection*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "57ccc28d44026990fa479c4c51e1f88f"
SRC_URI[sha256sum] = "ace7a07140ae342adbab453ba9dde8b5090666d27f2a915e89f4914054c95089"