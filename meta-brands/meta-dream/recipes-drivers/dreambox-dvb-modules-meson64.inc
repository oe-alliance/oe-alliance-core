SUMMARY = "Hardware drivers for Dreambox"
SECTION = "base"
LICENSE = "CLOSED"
DEPENDS += "virtual/kernel"
PRIORITY = "required"

inherit opendreambox-precompiled-binary4

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES =+ "${PN}-firmware ${PN}-fp ${PN}-lcd ${PN}-stb-core"

RDEPENDS:${PN} = "${PN}-fp ${PN}-lcd ${PN}-stb-core"
RDEPENDS:${PN}-firmware = "${PN}-firmware"

RDEPENDS:${PN}-fp = "${PN}-stb-core"
RREPLACES:${PN}-fp = "${PN} (< 4.9-20201130r0-r0.0)"
RBREAKS:${PN}-fp = "${PN} (< 4.9-20201130r0-r0.0)"

RDEPENDS:${PN}-lcd = "${PN}-fp ${PN}-stb-core"
RREPLACES:${PN}-lcd = "${PN} (< 4.9-20201130r0-r0.0)"
RBREAKS:${PN}-lcd = "${PN} (< 4.9-20201130r0-r0.0)"

RRECOMMENDS:${PN} = "${PN}-firmware"

FILES:${PN}-src = "${sysconfdir}/modules-load.d/03-${PRECOMPILED_NAME}-lcd.conf"

FILES:${PN} = "${sysconfdir}/modules-load.d/10-${PRECOMPILED_NAME}.conf \
               /lib/modules/${DM_LOCALVERSION}/extra/"

FILES:${PN}-firmware = "/lib/firmware"

# XXX: Remove this line once a binary package is available
ALLOW_EMPTY:${PN}-fp = "1"
FILES:${PN}-fp = "${sysconfdir}/modules-load.d/*-${PRECOMPILED_NAME}-fp.conf \
                   /lib/modules/${DM_LOCALVERSION}/extra/fp/fp-dev.ko"

ALLOW_EMPTY:${PN}-lcd = "1"
FILES:${PN}-lcd = " \
                   /lib/modules/${DM_LOCALVERSION}/extra/lcd/lcd.ko \
                   /lib/modules/${DM_LOCALVERSION}/extra/lcd.ko"

FILES:${PN}-stb-core = "${sysconfdir}/modules-load.d/01-${PRECOMPILED_NAME}-stb-core.conf \
                        /lib/modules/${DM_LOCALVERSION}/extra/stb-core.ko \
                        /lib/modules/${DM_LOCALVERSION}/extra/core/boxsec.ko \
                        /lib/modules/${DM_LOCALVERSION}/extra/stb/stb-core.ko"

# We don't use KERNEL_VERSION in this recipe, because the
# precompiled modules depend on a specific version.
DM_LOCALVERSION = "${@d.getVar('PV', True).rsplit('-', 1)[0]}"

pkg_postinst:${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi
}
pkg_postinst:${PN}-lcd () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi
}
pkg_postinst:${PN}-stb-core () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi
}
