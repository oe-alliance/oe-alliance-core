require libretro-mupen64plus-next.inc

SUMMARY = "Mupen64Plus-Next GLES3 libretro core"
DESCRIPTION = "GLES3 build of Mupen64Plus-Next for high-performance AArch64 receivers."

COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-highperformance', '.*', '^$', d)}"
MUPEN_GRAPHICS = "FORCE_GLES3=1"

LIBRETRO_CORE_FILE = "mupen64plus_next_gles3_libretro.so"
