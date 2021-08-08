SUMMARY = "Broadcom V3D driver"

FILES:${PN} = "${libdir}/lib*${SOLIBSDEV}"
FILES:${PN}-dev = "${includedir}"

EXCLUDE_FROM_WORLD = "1"

INSANE_SKIP:${PN} += "already-stripped dev-so ldflags"
