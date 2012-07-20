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

SRC_URI[md5sum] = "567a117943b2a99704801a24df2c7c90"
SRC_URI[sha256sum] = "199fa993dd6bce7181d69902ae916c9b6bfc1c6a7f3a6b54ef2a6eea430b9a06"
