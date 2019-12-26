inherit upx-compress

# there is --with-system-socket=/run/dbus/system_bus_socket in EXTRA_OECONF in OE-core layer
# but /run/dbus does not exist, so dbus (and dependant avahi services) wont start

EXTRA_OECONF = "--disable-tests \
                --disable-xml-docs \
                --disable-doxygen-docs \
                --disable-libaudit \
                --enable-largefile \
                --with-system-socket=/var/run/dbus/system_bus_socket \
                "
