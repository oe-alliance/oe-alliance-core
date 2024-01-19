SUMMARY = "Dreambox EGL and GLES2 libraries"
LICENSE = "CLOSED"
DEPENDS = "${LIBEGLDEP}"
PROVIDES = "virtual/egl virtual/libgles2"
RPROVIDES:${PN} = "virtual-egl virtual-libgles2"

LIBEGLDEP = ""
LIBEGLDEP:bcm73625 = "libv3ddriver"
LIBEGLDEP:bcm7435 = "libv3ddriver"
LIBEGLDEP:bcm7252s = "libvc5driver"

LIBEGLRDEP = ""
LIBEGLRDEP:bcm7435 = "libv3ddriver"
LIBEGLRDEP:bcm7252s = "libvc5driver"

RDEPENDS:${PN} = "${LIBEGLRDEP}"

RPROVIDES:${PN} = "libegl libegl1 libgles2 libglesv2-2"
RPROVIDES:${PN}-dev = "libegl-dev libgles2-dev"

RCONFLICTS:${PN} = "libegl libegl1 libgles2 libglesv2-2"
RCONFLICTS:${PN}-dev = "libegl-dev libgles2-dev"

RREPLACES:${PN} = "libegl libegl1 libgles2 libglesv2-2"
RREPLACES:${PN}-dev = "libegl-dev libgles2-dev"

# As a workaround, keep .so symlinks in base package
# until all users were recompiled.
FILES:${PN} += "${libdir}/lib*${SOLIBSDEV}"
FILES_SOLIBSDEV = ""
INSANE_SKIP:${PN} += "already-stripped dev-so ldflags"
