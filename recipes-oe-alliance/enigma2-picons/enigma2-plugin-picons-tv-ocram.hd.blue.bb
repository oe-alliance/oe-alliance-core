DESCRIPTION = "Ocram HD Picons (blue)"

PICONS_FILENAME = "picons-hd-blue_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-hd-blue*", d))
	except:
		pass
}

include enigma2-plugin-picons-ocram.inc

SRC_URI[md5sum] = "daaf6cb8064019c4e85d15018120e60c"
SRC_URI[sha256sum] = "a90f993d3ead50cb5c8f3d35e6a6e56038851e9c5968156854df830cf3444fb5"
