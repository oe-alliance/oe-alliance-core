FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://set-multilib-paths.patch \
    file://0001-mips32-use-c11-atomics-for-64-bit-atomics.patch \
"

# The 64-bit atomics the patch above switches to live in libatomic.
EXTRA_OECMAKE:append:mipsarch = " -DCMAKE_CXX_STANDARD_LIBRARIES=-latomic"

OECMAKE_GENERATOR = "Unix Makefiles"

EXTRA_OECMAKE += "-DCMAKE_POSITION_INDEPENDENT_CODE=1"

COMPATIBLE_HOST = ""
