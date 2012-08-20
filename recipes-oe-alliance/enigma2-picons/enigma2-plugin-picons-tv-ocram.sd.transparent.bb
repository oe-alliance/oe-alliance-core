DESCRIPTION = "Ocram SD Picons (transparent)"
RREPLACES_${PN} = "enigma2-plugin-picons-tv-zonka.cc.sd.transparent"

PICONS_FILENAME = "picons-sd-transparent_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-transparent*", d))
	except:
		pass
}

include enigma2-plugin-picons-ocram.inc

SRC_URI[md5sum] = "47c3bc760e3d119201d85edf15aca167"
SRC_URI[sha256sum] = "93b9849564d27779290dcb65676b2ccbb8047f61e4786d72d7e87be6cd130512"
