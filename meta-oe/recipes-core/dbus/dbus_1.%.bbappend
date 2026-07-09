FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

inherit upx-compress

PR .= ".3"

# This SysV setup does not derive rc links from the LSB header; update-rc.d uses
# INITSCRIPT_PARAMS from the recipe. Enigma2 switches to runlevel 4 while the GUI
# is stopped/restarted, so keep the system bus active there too.
INITSCRIPT_PARAMS = "start 02 2 3 4 5 . stop 20 0 1 6 ."

# there is --with-system-socket=/run/dbus/system_bus_socket in EXTRA_OECONF in OE-core layer
# but /run/dbus does not exist, so dbus (and dependant avahi services) wont start

EXTRA_OECONF = "--disable-tests \
                --disable-xml-docs \
                --disable-doxygen-docs \
                --disable-libaudit \
                --enable-largefile \
                --with-system-socket=/var/run/dbus/system_bus_socket \
                "
