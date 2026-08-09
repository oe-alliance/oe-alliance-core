PV = "16.2.0"
BINV = "16.2.0"
SRC_URI[sha256sum] = "e6738e29597f733270731aa90600f37ffdc045079dfc27ec7e8192cc81085c3e"

# Patches upstream in 16.2 merged
SRC_URI:remove = "file://0026-gcc-i386-fix-ICE-with-ffloat-store-PR125308.patch"

RECIPE_MAINTAINER:pn-gcc-source-16.2.0 = "OE-Alliance Team <noreply@github.com>"
