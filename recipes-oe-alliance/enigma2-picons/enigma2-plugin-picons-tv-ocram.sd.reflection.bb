DESCRIPTION = "Ocram SD Picons (Reflection)"
RREPLACES_${PN} = "enigma2-plugin-picons-tv-zonka.cc.sd.reflection"

PICONS_FILENAME = "picons-sd-reflection_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-reflection*", d))
	except:
		pass
}

include enigma2-plugin-picons-ocram.inc

SRC_URI[md5sum] = "895f2172d7787ca58e35676d8b2dce17"
SRC_URI[sha256sum] = "aab8f49874890c883aeb2ff2c710220ae9b7e0b961b7dde9a24fdc712adfe6ee"
