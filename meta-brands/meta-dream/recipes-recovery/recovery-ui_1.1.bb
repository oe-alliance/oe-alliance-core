SUMMARY = "Simple user interface for Dreambox rescue mode"
HOMEPAGE = "http://dreamboxupdate.com/"
LICENSE = "CLOSED"
DEPENDS = "libmnl xz"
SRCREV = "1cd69394b3702a06cadfce078289dd351a07b152"
SRCREV_dm820 = "${SRCREV_dm7080}"
SRCREV_dm7080 = "6f61849bf4e60266e313f76cbbab2f3278db5296"

SRC_URI_append = ";branch=${BRANCH}"

inherit opendreambox-git pkgconfig update-rc.d

do_install() {
    oe_runmake install DESTDIR=${D}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "dreambox-dvb-modules-${MACHINE}-lcd"

BRANCH = "master"
BRANCH_dm820 = "${BRANCH_dm7080}"
BRANCH_dm7080 = "dm7080"

COMPATIBLE_MACHINE = "^(dm820|dm7080)$"

INITSCRIPT_NAME = "${BPN}"
