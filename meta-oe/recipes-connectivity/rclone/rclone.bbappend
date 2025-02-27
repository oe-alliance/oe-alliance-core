GO_IMPORT = "rclone.org"
GO_INSTALL = "${GO_IMPORT}"

# generate standalone executable without dependencies to any shared objects at runtime
GO_LINKSHARED = ""

# Reduce go compiled binary file size
GO_EXTRA_LDFLAGS = "-s"
GOBUILDFLAGS:remove = "-buildmode=pie"
export CGO_ENABLED = "0"

# Speed up compression time for go compiled binaries
UPX_ARGS += "--lzma"

# Allow downloads during compile
do_compile[network] = "1"
