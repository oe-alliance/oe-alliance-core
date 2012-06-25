DESCRIPTION = "Zonk.cc HD Picons (Black)"

PICONS_FILENAME = "picons-hd-black_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-hd-black*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc
