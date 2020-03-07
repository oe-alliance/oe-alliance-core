SUMMARY = "Kodi Media Center PVR plugins"

PV = "3.28.9+git${SRCPV}"
PKGV = "3.28.9+git${GITPKGV}"

KODIADDONPLUGIN = "vuplus"

DEPENDS_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "nlohmann-json", "", d)}"

require kodi-addon-pvr.inc
