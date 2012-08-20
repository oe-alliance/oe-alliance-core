DESCRIPTION = "Ocram HD Picons (transparent)"
RREPLACES_${PN} = "enigma2-plugin-picons-tv-zonka.cc.hd.transparent"

PICONS_FILENAME = "picons-hd-transparent_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-hd-transparent*", d))
	except:
		pass
}

include enigma2-plugin-picons-ocram.inc

SRC_URI[md5sum] = "32934ca445e253b65613f3b3784cefab"
SRC_URI[sha256sum] = "ca6d2a0c2faab1b27b2107efe43efce79fc744b438819921ba4d75bc0da0c669"
