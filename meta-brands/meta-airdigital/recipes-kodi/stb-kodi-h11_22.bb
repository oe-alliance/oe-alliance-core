require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"
RDEPENDS:${PN} += "airdigital-mali-${HICHIPSET}"

# The AirDigital H11 Mali userspace exports GLES2 only, although the shared
# sysroot also contains GLES3 headers.  Avoid Kodi's GLES3 CaptureBlit path,
# which otherwise leaves glBlitFramebuffer unresolved at final link time.
PR:append = ".1"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=mali-cortexa15 \
    -DOPENGLES_FORCE_GLES2=ON \
"
