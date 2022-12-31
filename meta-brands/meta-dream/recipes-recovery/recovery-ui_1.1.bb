SUMMARY = "Simple user interface for Dreambox rescue mode"
HOMEPAGE = "http://dreamboxupdate.com/"
LICENSE = "CLOSED"
DEPENDS = "libmnl xz"
SRCREV = "1cd69394b3702a06cadfce078289dd351a07b152"
SRCREV:dm820 = "dm7080"
SRCREV:dm7080 = "ed7dd69f2d24c040b2a4ebfbeeb63135132abde7"
SRCREV:dreamone = "dm7080"
SRCREV:dreamtwo = "dm7080"

SRC_URI:append = ";branch=${BRANCH}"

inherit opendreambox-git pkgconfig update-rc.d

do_install() {
    oe_runmake install DESTDIR=${D}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

RRECOMMENDS:${PN} = "dreambox-dvb-modules-${MACHINE}-lcd"
RRECOMMENDS:${PN}:meson64 = "dreambox-dvb-modules-meson64-lcd"

BRANCH = "master"
BRANCH:dm820 = "dm7080"
BRANCH:dm7080 = "dm7080"
BRANCH:dreamone = "dm7080"
BRANCH:dreamtwo = "dm7080"

COMPATIBLE_MACHINE = "^(dm820|dm7080|dreamone|dreamtwo)$"

INITSCRIPT_NAME = "${BPN}"
