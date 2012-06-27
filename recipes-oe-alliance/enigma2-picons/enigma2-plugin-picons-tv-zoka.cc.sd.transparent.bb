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

SRC_URI[md5sum] = "2b395b74cce73799ee11e6bc04bfc089"
SRC_URI[sha256sum] = "23172c6d5c7c558a564d53cce86a14464f295a07ae943fdb1ad67dc745a36014"
