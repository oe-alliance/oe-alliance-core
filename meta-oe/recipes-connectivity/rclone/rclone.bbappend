# This build uses go, which will download modules and, by default,
# place them in the HOME of build user!
# It will even make some of them read-only!!!
# So put them within the build tree, and undo the read-only setting.
#

GOPATH = "${TMPDIR}/go/"
export GOPATH

do_compile:append() {
    chmod -R +w "$GOPATH"
}
