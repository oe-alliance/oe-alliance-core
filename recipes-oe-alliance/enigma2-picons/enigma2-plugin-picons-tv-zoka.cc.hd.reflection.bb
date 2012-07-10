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

SRC_URI[md5sum] = "fcb4864e65c00b0f7de79731ab79d12c"
SRC_URI[sha256sum] = "86582f772dd631b564fbafb034690f28541c63e87927ea39cc6fd093424ff82c"
