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

SRC_URI[md5sum] = "9f0f0385a3549d8a8af39fbae7c63a79"
SRC_URI[sha256sum] = "863bab7417a0e068f88835dbefc610de7eca4292c7dabcc2cbbb8799f8952288"
