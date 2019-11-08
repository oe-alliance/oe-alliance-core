EXTRA_OECONF += "--disable-werror"
TARGET_CPPFLAGS_sh4 = "-I${STAGING_DIR_TARGET}${includedir}"
RPROVIDES_${PN} += "libcrypt1 libcrypt.so.1"
