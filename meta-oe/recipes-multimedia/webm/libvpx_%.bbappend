# Drop .d files from prior builds — they hard-code the gcc install path
# (via `gcc -M`) and break after a gcc-cross bump.
do_compile:prepend() {
    find ${B} -name '*.d' -delete 2>/dev/null || true
}
