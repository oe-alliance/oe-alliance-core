SUMMARY = "blindscan for dinobot"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

SRCDATE = "20181008"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "http://source.mynonpublic.com/dinobot/dinobot-blindscan-${SRCDATE}.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/dinobot-blindscan ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/dinobot-blindscan"

SRC_URI[md5sum] = "343e65906a17f677c97c54e927c9b4fb"
SRC_URI[sha256sum] = "abcd6c4eab116c558afb28a6893d1be2bc1b983ded2796205fdf53e90bd7d6ea"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
