CFLAGS:append:class-target:arm = " -lm"
CFLAGS:append:class-target:aarch64 = " -lm"
CFLAGS:append:class-target:mipsarch = " -lm"

INSANE_SKIP = "32bit-time"
INSANE_SKIP:${PN} = "already-stripped ldflags"
