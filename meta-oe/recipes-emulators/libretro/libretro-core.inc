PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming', '.*', '^$', d)}"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped"

LIBRETRO_CORE_FILE ??= "${BPN}_libretro.so"
LIBRETRO_CORE_PATH ??= "${S}/${LIBRETRO_CORE_FILE}"

do_install() {
    install -d ${D}${libdir}/libretro
    install -m 0755 ${LIBRETRO_CORE_PATH} ${D}${libdir}/libretro/${LIBRETRO_CORE_FILE}
}

FILES:${PN} = "${libdir}/libretro/${LIBRETRO_CORE_FILE}"
