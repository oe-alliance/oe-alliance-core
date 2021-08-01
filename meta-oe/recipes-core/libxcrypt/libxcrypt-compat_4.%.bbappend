EXTRA_OECONF += "--disable-werror"
TARGET_CPPFLAGS:sh4 = "-I${STAGING_DIR_TARGET}${includedir}"
RPROVIDES:${PN} += "libcrypt1 libcrypt.so.1"
