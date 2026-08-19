# Don't configure udev by default since it will cause a circular
# dependecy with udev package, which depends on libusb

PACKAGECONFIG:class-target = ""

do_install:append() {
    if [ ! -e ${D}${base_libdir}/libusb-1.0.so ]; then
        ln -sf libusb-1.0.so.0 ${D}${base_libdir}/libusb-1.0.so
    fi
}
