SUMMARY = "Broadcom V3D driver"
PROVIDES = "virtual/egl virtual/libgles2"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RPROVIDES_${PN} = "libegl libegl1 libgles2 libglesv2-2"
RPROVIDES_${PN}-dev = "libegl-dev libgles2-dev"

RCONFLICTS_${PN} = "libegl libegl1 libgles2 libglesv2-2"
RCONFLICTS_${PN}-dev = "libegl-dev libgles2-dev"

RREPLACES_${PN} = "libegl libegl1 libgles2 libglesv2-2"
RREPLACES_${PN}-dev = "libegl-dev libgles2-dev"

FILES_${PN} = "${libdir}/lib*${SOLIBS} ${libdir}/lib*${SOLIBSDEV}"
FILES_${PN}-dev = "${includedir}"

EXCLUDE_FROM_WORLD = "1"

INSANE_SKIP_${PN} = "dev-so"
