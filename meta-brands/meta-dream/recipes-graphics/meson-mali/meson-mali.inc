SUMMARY = "amlogic mali opengl provider"
LICENSE = "CLOSED"
PROVIDES = "virtual/egl virtual/libgles2"

RRECOMMENDS:${PN} = "${PN}-module-bifrost-r12p0"

RPROVIDES:${PN} = "libegl libegl1 libgles2 libglesv2-2"
RPROVIDES:${PN}-dev = "libegl-dev libgles2-dev"

RCONFLICTS:${PN} = "libegl libegl1 libgles2 libglesv2-2"
RCONFLICTS:${PN}-dev = "libegl-dev libgles2-dev"

RREPLACES:${PN} = "libegl libegl1 libgles2 libglesv2-2"
RREPLACES:${PN}-dev = "libegl-dev libgles2-dev"

FILES:${PN} += "${libdir}/lib*${SOLIBSDEV}"
FILES_SOLIBSDEV = ""
INSANE_SKIP:${PN} = "dev-so"

COMPATIBLE_MACHINE = "^(meson64)$"
