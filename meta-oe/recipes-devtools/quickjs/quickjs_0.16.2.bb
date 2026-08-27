SUMMARY = "QuickJS-ng, a small embeddable Javascript engine"
DESCRIPTION = "Fork of Fabrice Bellard's QuickJS with ES2023 support. Provides the \
qjs command line interpreter, used on the box to evaluate the obfuscated Javascript \
that some streaming portals wrap their video URLs in."
HOMEPAGE = "https://github.com/quickjs-ng/quickjs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7d9f1f9af65f3cca7f8bcc065cc4c4e3"

SRC_URI = "git://github.com/quickjs-ng/quickjs.git;protocol=https;branch=master"
SRCREV = "1ab8676f4b6d6d669baeb5f21790fb9734636a20"

UPSTREAM_CHECK_URI = "https://github.com/quickjs-ng/quickjs/releases"
UPSTREAM_CHECK_GITTAGREGEX = "v(?P<pver>\d+(\.\d+)+)"

inherit cmake

# The interpreter is built from a pregenerated gen/repl.c, so nothing has to run
# on the build host and no native variant is needed.
EXTRA_OECMAKE = "-DBUILD_SHARED_LIBS=OFF"

PACKAGES =+ "${PN}-compiler"

# qjsc turns Javascript into C and needs a compiler, which no box has.
FILES:${PN}-compiler = "${bindir}/qjsc"
FILES:${PN} = "${bindir}/qjs"
