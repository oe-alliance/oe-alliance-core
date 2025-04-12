export KCFLAGS += "${@bb.utils.contains("TARGET_ARCH", "mipsel", "-std=gnu17", "", d)}"
