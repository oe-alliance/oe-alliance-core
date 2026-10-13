FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
PR:append = ".stb1"

# Preserve the upstream Linux/USB adapters and add the legacy Enigma2 ABIs.
# Runtime device-node detection selects the backend; no model/driver dependency.
SRC_URI += "file://0001-add-enigma2-stb-cec-adapter.patch"
EXTRA_OECMAKE += "-DHAVE_STB_API=1"
