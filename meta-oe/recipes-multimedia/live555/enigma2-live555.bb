SUMMARY = "Enigma2 Live555 RTSP/HLS D-Bus streaming daemon"
SECTION = "multimedia"
require conf/license/license-gplv2.inc

DEPENDS = "glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base live555 openssl"

# The daemon is controlled by the optional TranscodingSettings plugin.  It is
# deliberately not registered for boot startup and is not D-Bus activated.
inherit gittag meson pkgconfig upx-compress

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

SRC_URI = "git://github.com/oe-alliance/enigma2-live555.git;protocol=https;branch=main"

EXTRA_OEMESON += "-Dlive555-libdir=${STAGING_DIR_HOST}${libdir} \
                  -Dlive555-includedir=${STAGING_DIR_HOST}${includedir}"

do_configure:prepend() {
    if [ -n "${SOURCE_DATE_EPOCH}" ]; then
        find "${S}" -exec touch -h -d "@${SOURCE_DATE_EPOCH}" {} +
    else
        find "${S}" -exec touch -h {} +
    fi

    for lib in liveMedia groupsock BasicUsageEnvironment UsageEnvironment; do
        if [ ! -e "${STAGING_DIR_HOST}${libdir}/lib${lib}.a" ] && [ ! -e "${STAGING_DIR_HOST}${libdir}/lib${lib}.so" ]; then
            bbfatal "Missing Live555 library in recipe sysroot: lib${lib}"
        fi
    done
}

RDEPENDS:${PN} = "gstreamer1.0-plugins-base-app \
                  gstreamer1.0-plugins-base-gio \
                  gstreamer1.0-plugins-bad-hls \
                  gstreamer1.0-plugins-bad-codectimestamper \
                  gstreamer1.0-plugins-bad-mpegtsdemux \
                  gstreamer1.0-plugins-bad-mpegtsmux \
                  gstreamer1.0-plugins-bad-videoparsersbad \
                  gstreamer1.0-plugins-good-audioparsers \
                  gstreamer1.0-plugins-good-multifile \
                  gstreamer1.0-plugins-good-souphttpsrc"

FILES:${PN} += "${sysconfdir}/dbus-1/system.d/enigma2-live555.conf \
                ${sysconfdir}/init.d/enigma2-live555"

pkg_postinst:${PN} () {
if [ -z "$D" ]; then
    was_running=/tmp/enigma2-live555.was-running
    should_start=0

    # Remove startup links and the activation file from older package builds.
    if command -v update-rc.d >/dev/null 2>&1; then
        update-rc.d -f enigma2-live555 remove 2>/dev/null || true
    fi
    rm -f /etc/rc?.d/[SK][0-9][0-9]enigma2-live555 2>/dev/null || true
    rm -f /usr/share/dbus-1/system-services/org.enigma2.Live555.service
    /etc/init.d/dbus-1 reload 2>/dev/null || /etc/init.d/dbus reload 2>/dev/null || true

    [ -e "$was_running" ] && should_start=1

    # First upgrade from older packages cannot see the new prerm marker yet.
    # Fall back to saved TranscodingSettings when HLS/RTSP is enabled.
    settings_file=/etc/enigma2/settings
    if [ -f "$settings_file" ] && grep -Eiq '^config\.plugins\.transcodingsettings\.(hls|rtsp)\.enabled=(1|true|yes|on)$' "$settings_file"; then
        should_start=1
        if grep -Eiq '^config\.plugins\.transcodingsettings\.enabled=(0|false|no|off)$' "$settings_file"; then
            should_start=0
        fi
        if grep -Eiq '^config\.plugins\.transcodingsettings\.port=' "$settings_file" && ! grep -Eq '^config\.plugins\.transcodingsettings\.port=8001$' "$settings_file"; then
            should_start=0
        fi
    fi

    rm -f "$was_running"

    if [ "$should_start" -eq 1 ]; then
        if [ -x /etc/init.d/enigma2-live555 ]; then
            /etc/init.d/enigma2-live555 start 2>/dev/null || true
        fi
    fi
fi
}

pkg_prerm:${PN} () {
if [ -z "$D" ]; then
    was_running=/tmp/enigma2-live555.was-running

    if [ -x /etc/init.d/enigma2-live555 ] && /etc/init.d/enigma2-live555 status >/dev/null 2>&1; then
        touch "$was_running"
    else
        rm -f "$was_running"
    fi

    if [ -x /etc/init.d/enigma2-live555 ]; then
        /etc/init.d/enigma2-live555 stop 2>/dev/null || true
    fi
fi
}
