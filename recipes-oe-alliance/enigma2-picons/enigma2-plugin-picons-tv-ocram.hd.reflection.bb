DESCRIPTION = "Ocram HD Picons (Reflection)"
RREPLACES_${PN} = "enigma2-plugin-picons-tv-zonka.cc.hd.reflection"

PICONS_FILENAME = "picons-hd-reflection_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-hd-reflection*", d))
	except:
		pass
}

include enigma2-plugin-picons-ocram.inc

SRC_URI[md5sum] = "e6c29f82d416cdbec8420ca07f084d12"
SRC_URI[sha256sum] = "d79237e4eec209d24f9648ec3feb06cb1df344bb1cea47a13b33ff354fda35c1"
