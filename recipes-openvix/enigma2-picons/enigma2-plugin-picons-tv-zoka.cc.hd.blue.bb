DESCRIPTION = "Zonk.cc HD Picons (Blue)"

PICONS_FILENAME = "picons-hd-blue_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-hd-blue*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc
