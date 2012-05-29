DESCRIPTION = "Zonk.cc HD Picons (transparent)"

PICONS_FILENAME = "picons-hd-transparent_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-hd-transparent*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "488ee1780f867c03d5dfb67c919e9001"
SRC_URI[sha256sum] = "8e73fdb7d035c88ccdf0ecae7639a3b59b4e841cf83d9ea3f6fe13f3e4489a27"
