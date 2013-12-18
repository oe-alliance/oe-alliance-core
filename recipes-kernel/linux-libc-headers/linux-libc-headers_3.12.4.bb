require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "3"

SRC_URI += "file://0001-ptrace.h-remove-ptrace_peeksiginfo_args.patch"

SRC_URI[md5sum] = "2f9bc019c41e4f68be7438bc9cb1f410"
SRC_URI[sha256sum] = "d920a88e5a9b82400d2c4abc0f16ce1f9dc5a9f170f0b16619b9487c9ee2aa39"
