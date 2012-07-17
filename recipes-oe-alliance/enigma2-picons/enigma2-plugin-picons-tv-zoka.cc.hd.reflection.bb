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

SRC_URI[md5sum] = "3134bc144cc0447bf9583a9661071967"
SRC_URI[sha256sum] = "11d468cc85e36068f0a0a6e556858ce5500b36a538874837ce3cafa06713ec95"
