inherit upx-compress

# Speed up compression time for go compiled binaries
UPX_ARGS += "--lzma"

# Go 1.27 enables jsonv2 experiment by default, but encoding/json/v2
# in Go 1.27 is missing SkipFunc and DiscardUnknownMembers that
# go-json-experiment/json aliases from the stdlib. Disable the
# experiment so the module uses its own standalone implementations.
export GOEXPERIMENT = "nojsonv2"
