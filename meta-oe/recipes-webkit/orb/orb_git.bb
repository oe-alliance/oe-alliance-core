SUMMARY = "Open Red Button (ORB) HbbTV 2.0 engine with a WPE WebKit host"
DESCRIPTION = "The HbbTV 2.0 application engine of the Open Red Button project (application manager, OIPF/HbbTV request \
handlers, JavaScript polyfill), plus what connects it to WPE WebKit on an enigma2 box: an in-process host for the embedder, a \
web process extension that carries the polyfill's bridge requests, and the ORBPlatform implementation the engine loads."
HOMEPAGE = "https://github.com/OpenRedButtonProject/orb"
AUTHOR = "xcentaurix"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b1e01b26bacfc2232046c90a330332b3"

SRC_URI = "git://github.com/OpenViX/orb.git;protocol=https;branch=enigma2"
SRCREV = "${AUTOREV}"

# r1: patch 0004 (broadcast-related applications), the current channel in the platform layer; r2: the components (video, audio, subtitles) of the channel
# come from enigma2 (orb_host_set_components), component selection goes back to it (orb_host.h changed: the plugin needs this revision)
# r3: the real AIT (orb_host_service_selected / orb_host_ait_section / orb_host_broadcast_stopped, dvb://current.ait locators in orb_host_launch_broadcast)
# r4: orb_host_key_set_mask (the key set of the running application, for the automatic banner)
# r5: our own hbbtv.js (WPE tweaks, e.g. no fades) replaces the one the ORB makefile generates
# r6: orb is built from OpenViX/orb (upstream orb at 8945a13, our four patches as commits, our files in enigma2/); revision follows the git tree
# r7: move to own repo
PR = "r7"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

# our CMakeLists.txt is in enigma2/ of the tree (OpenViX/orb: upstream orb at 8945a13, our patches as commits, enigma2/)
OECMAKE_SOURCEPATH = "${S}/enigma2"

DEPENDS = "wpewebkit libxml2 curl util-linux-libuuid glib-2.0 libsoup"

inherit gitpkgv cmake pkgconfig

# The HbbTV JavaScript polyfill (hbbtv.js, iframe.js, dash.all.min.js, playerpage.html) is put together by ORB's own makefile, which
# only concatenates sources (no node/npm). ORB_NATIVE=rdk selects the RDK bridge; BBC_API_ENABLE=0 leaves the proprietary BBC API out.
do_compile:append() {
    oe_runmake -C ${S} build ORB_NATIVE=rdk BBC_API_ENABLE=0
}

do_install:append() {
    install -d ${D}${libdir}/orb
    for f in hbbtv.js iframe.js dash.all.min.js playerpage.html; do
        install -m 0644 ${S}/out/resources/assets/polyfill/$f ${D}${libdir}/orb/$f
    done
    # our modified polyfill replaces the generated hbbtv.js
    install -m 0644 ${S}/enigma2/hbbtv.js ${D}${libdir}/orb/hbbtv.js
    # forwards the application's console output, script errors and media events to the embedder's log; wpe_fb injects it when present
    install -m 0644 ${S}/enigma2/debug.js ${D}${libdir}/orb/debug.js
}

# ${libdir}/orb: the polyfill and the two modules that are loaded by path (web process extension in ext/, platform in wpe/)
FILES:${PN} = "${libdir}/libORBCore.so.* ${libdir}/libORBHost.so.* ${libdir}/orb"
FILES:${PN}-dev = "${includedir}/orb ${libdir}/libORBCore.so ${libdir}/libORBHost.so"
FILES:${PN}-dbg += "${libdir}/orb/*/.debug"
