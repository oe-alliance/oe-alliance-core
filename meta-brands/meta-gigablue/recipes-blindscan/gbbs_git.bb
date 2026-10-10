SUMMARY = "Open-source blindscan helper for GigaBlue PnP satellite tuners"
DESCRIPTION = "Userspace helper for the TS3L10 and TS2L08 SiLabs channel-seek interface in the GigaBlue DVB driver. The caller configures LNB voltage and tone."
HOMEPAGE = "https://github.com/EB-TNAP/gbbs"
SECTION = "console/utils"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=75859989545e37968a99b631ef42722e"

inherit gittag

PV = "git"
PKGV = "V${GITPKGVTAG}"

SRC_URI = "git://github.com/EB-TNAP/gbbs.git;protocol=https;branch=main;destsuffix=gbbs"
SRCREV = "${AUTOREV}"

S = "${UNPACKDIR}/gbbs"
B = "${WORKDIR}/build"

# Quad 4K, Quad 4K Pro and UE 4K with a TS3L10 or TS2L08 PnP tuner.
COMPATIBLE_MACHINE = "^gb7252$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_compile() {
    # Upstream's Makefile overrides CC and strips during compilation. Keep the
    # OE toolchain/flags and let the normal packaging tasks split debug symbols.
    ${CC} ${CPPFLAGS} ${CFLAGS} "${S}/gbbs.c" -o "${B}/gbbs" ${LDFLAGS}
}

do_install() {
    install -d "${D}${bindir}"
    install -m 0755 "${B}/gbbs" "${D}${bindir}/gbbs"
}

FILES:${PN} = "${bindir}/gbbs"
