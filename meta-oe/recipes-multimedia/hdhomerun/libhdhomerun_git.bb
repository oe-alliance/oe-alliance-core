SUMMARY = "SiliconDust HDHomeRun client library"
DESCRIPTION = "Open-source client library and configuration utility for SiliconDust HDHomeRun network tuners."
HOMEPAGE = "https://github.com/Silicondust/libhdhomerun"

LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fc178bcd425090939a8b634d1d6a9594"

# Keep this revision aligned with the dependency selected by pvr.hdhomerun
# 22.2.5.  The add-on's depends/common/hdhomerun/hdhomerun.txt pins the same
# upstream commit (SiliconDust release 20201023).
PV = "20201023+git"
SRCREV = "7c54382fb681d03888b469033e50bebaf4ce6bce"
SRC_URI = "git://github.com/Silicondust/libhdhomerun.git;branch=master;protocol=https"

# Upstream's small Makefile supports cross compilation when the complete OE
# compiler and flags are supplied on the command line.  Disable its explicit
# strip step so package.bbclass can split debug symbols normally.
EXTRA_OEMAKE = " \
    CC='${CC}' \
    STRIP=true \
    CFLAGS='${CPPFLAGS} ${CFLAGS}' \
    LDFLAGS='${LDFLAGS} -lpthread -lrt' \
"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir} ${D}${libdir} ${D}${includedir}/hdhomerun
    install -m 0755 ${S}/hdhomerun_config ${D}${bindir}/hdhomerun_config
    install -m 0755 ${S}/libhdhomerun.so ${D}${libdir}/libhdhomerun.so
    install -m 0644 ${S}/hdhomerun*.h ${D}${includedir}/hdhomerun/
}

# Upstream deliberately uses the unversioned name as its ELF SONAME.
SOLIBS = ".so"
FILES_SOLIBSDEV = ""
