SUMMARY = "Nexus driver"

PACKAGE_ARCH = "${MACHINE_ARCH}"

FILES_${PN} = "${libdir}/*"
FILES_${PN}-dev = ""
FILES_${PN}-dbg = ""

ALLOW_EMPTY_${PN}-dev = "1"
ALLOW_EMPTY_${PN}-dbg = "1"

EXCLUDE_FROM_WORLD = "1"

INSANE_SKIP_${PN} = "dev-so"
