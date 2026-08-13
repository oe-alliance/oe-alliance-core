SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"

KODIADDONPLUGIN = "hdhomerun"

require kodi-addon-pvr.inc

# pvr.hdhomerun uses the system library in OE builds.  Declaring it here is
# required for hdhomerun.h and libhdhomerun.so to enter this recipe's sysroot.
DEPENDS:append = " libhdhomerun"
