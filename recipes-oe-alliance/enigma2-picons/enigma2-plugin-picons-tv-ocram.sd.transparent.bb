DESCRIPTION = "Scram SD Picons (transparent)"
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

SRC_URI[md5sum] = "a2fc03e3ad4730069fe80f60ba697560"
SRC_URI[sha256sum] = "6efc55a10e38caec4c8a6c9bf8af12fc805a19cf2067390edfebca9f823b6e2f"
