require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI += "file://0001-ptrace.h-remove-ptrace_peeksiginfo_args.patch"

SRC_URI[md5sum] = "60335a029be49b6c92857deec0b15bec"
SRC_URI[sha256sum] = "c56317810e5716dd2be7ad947a6bd174460f7cf9afb33b700a052aa91f73f9bb"
