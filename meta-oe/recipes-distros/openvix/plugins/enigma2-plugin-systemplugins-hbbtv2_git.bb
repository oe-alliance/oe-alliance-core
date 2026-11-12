SUMMARY = "HbbTV 2.0 for enigma2 (WPE WebKit + Open Red Button)"
DESCRIPTION = "Runs HbbTV applications on top of enigma2: wpe_fb renders them with WPE WebKit onto the framebuffer, the Open Red \
Button engine provides the HbbTV/OIPF objects, and <video> plays on the hardware video plane through dvbvideosink. The plugin \
forwards the remote control to the application and hands the decoder over from the live service and back."
AUTHOR = "xcentaurix"
HOMEPAGE = "https://github.com/OpenViX/HbbTv2.0"

require conf/python/python3-compileall.inc

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI = "git://github.com/OpenViX/HbbTv2.0.git;protocol=https;branch=master"
SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"


# r1: phase 1 (settings menu, persistent session, animation switch, repeat limiting switch); r2: applications of a channel start as broadcast-related
# applications of it (the channel comes from enigma2); r3: no scrollbars (a TV shows none), the ZDF start banner is skipped (its media library
# application starts at once); r4: the streams of the channel (video, audio tracks, subtitles) go to the application, and its choice of an audio
# track or subtitle to enigma2; r5: the ZDF start banner is opened again (the r3 shortcut to the main application is gone: every channel starts with
# its own start application); r6: the real AIT (read from the demux, given to the engine; a choice among the applications it signals). A new revision each
# time, so that opkg upgrades. r7: a "Loading HbbTV ..." message until the first page has loaded. r8: dvb://current.ait locators in hexadecimal.
# r9: the red button starts the application (InfoBar.onHBBTVActivation) and the automatic banner (autostart): the keys the application asks for go to
# it. Both can be switched off in the settings. r10: the automatic banner is shown by enigma2 itself: wpe_fb --overlay-png saves the visible part of each
# frame as a PNG, a skin screen of the plugin shows it in a Pixmap widget (writing into the framebuffer behind enigma2's back blinked and left remnants).
# r11: a screen created as a dialog is started once (its creator and its layout both started it: the second start failed, "could not start wpe_fb").
# r12: the banner's screen is 1x1 and then only as big as the picture (a window over the whole desktop made enigma2 redraw - flicker - the infobar).
# r13: the automatic banner really runs in overlay mode (WPE_OVERLAY=1 was lost when WPE_HWVIDEO=1 was set: wpe_fb then wrote the whole frame into the
# framebuffer and wiped enigma2's infobar after every page flip).
# r14: the banner's dialog is shown (instantiateDialog() alone leaves it invisible).
# r15: enigma2 crashed (GPU memory gone, "ION_IOC_ALLOC: Cannot allocate memory", segfault in libv3ddriver): (1) wpe_fb and its web processes stayed behind
# whenever enigma2 restarted or crashed (WPE_OWNER watchdog in run_wpe.sh), each holding GPU memory - the next enigma2 crashed on its first big texture; (2) an
# application that grows big (a colour key opens its whole UI) made enigma2 create a texture of 1920x1080 per picture: above 40 % of the screen wpe_fb now
# switches to the framebuffer (event "overlay fullscreen"); identical pictures are not sent, the picture interval grows with its size, the plugin does not
# resize/move/show its window when nothing changed.
# r16: the switch to the framebuffer happens at the first picture that is too big (a static page sends only one).
# r17: an application that has grown to full screen becomes the executing screen (execDialog), like the red button's: enigma2's infobar then does not show
# itself on the service restart after a video (five seconds of page flips, each repainted by wpe_fb's guard in a visible sweep).
# r18: the manual launcher opens the list of applications after its "Reading ..." box has really gone (opening it in the same call raised "Modal open are
# allowed only from a screen which is modal!", which left enigma2 without an executing screen).
# r19: wpe_fb gets a second presentation backend for Nexus-middleware Broadcom boxes (GigaBlue). Root cause (confirmed on real hardware 2026-09-23,
# see patch 0017/wpewebkit's own r7-r10 history and project memory project_hbbtv2_wpe_orb_plan.md): wpe_fb's ONLY presentation path was mmap()+memcpy()
# into /dev/fb0 - correct for the DM900's direct-fbdev vc5dream stack, but /dev/fb0 is not connected to a Nexus box's real display scanout at all
# (NXPL_RegisterNexusDisplayPlatform's own doc comment says it registers a display for "exclusive use": Nexus owns the physical display through its
# own IPC/compositor). Proved with two new standalone diagnostics before touching wpe_fb.c (tools/vuplus-probe/fb_paint_probe.c: a raw fb0 write is
# invisible on GigaBlue; tools/vuplus-probe/nxpl_window_probe.c: an NXPL native window + a real EGL window surface IS visible). New backend
# (nxpl_try_open/nxpl_finish_open/nxpl_present/nxpl_close, "NXPL/Nexus presentation backend" section): joins Nexus, registers the EGL platform (the
# same fix as patch 0017/r10, needed here too), creates+shows an NXPL native window, uploads the same already-composited pixel buffer the fb0 path
# would have memcpy'd (out.conv) as a GL texture drawn full-screen each frame, presented with eglSwapBuffers - Nexus's own compositor does the actual
# scanout. Auto-detected at runtime (dlopen probing only, no link-time dependency, no vendor headers): nxpl_try_open() safely returns FALSE with
# nothing registered on any box without Nexus, e.g. the DM900, which falls straight through to the existing /dev/fb0 path completely unchanged.
# Compiles and links clean (hand cross-compiled and checked against the real wpe-webkit-2.0/wpe-platform-2.0/libsoup-3.0 headers and runtime
# libraries); NXPL native-window presentation itself already proven on a real GigaBlue (nxpl_window_probe.c: solid cyan seen on the TV), but this
# integration into wpe_fb's actual per-frame pipeline is NOT YET CONFIRMED with real HbbTV content on the box - that is the next validation step.
# r20: r19 confirmed HbbTV content genuinely renders on a real GigaBlue via the new NXPL path - but "slow, lots of artefacts and flickering". Root
# cause (most likely, well-evidenced): nxpl_present() re-uploaded the ENTIRE out.ow x out.oh frame via glTexSubImage2D on every single WebKit frame
# event regardless of how small the actual change was - expensive on this SoC, and a long upload can miss vsync and show as tearing/flicker on its
# own. Fix: track only the union of ROW ranges (y0..y1, not a full x+y sub-rect - plain GLES2 has no GL_UNPACK_ROW_LENGTH to sub-range a row of
# out.conv's packed buffer without a CPU-side copy first, but a run of whole rows is already contiguous memory, no copy needed) touched since the
# last present() and re-upload only that (nxpl_mark_dirty()/nxpl_dirty_y0/y1) - covers the common case (banner, status line, blinking element,
# scrolling text) far more cheaply than a full-frame reupload. Also dropped the per-present glClear(): the quad always covers the whole screen, so
# it was pure wasted GPU time before every swap. Both changes are internal to nxpl_present()/nxpl_mark_dirty() only - the fb0 path (DM900/VU+) is
# untouched. Still pending: real re-test on GigaBlue to confirm this actually fixes the slowness/flicker, and clarification on what the reported
# "artefacts" specifically look like (wrong colours vs tearing vs stale content) in case a second, different root cause is also in play.
# r21: user report on GigaBlue: "again, the performance is very poor, about half the speed of dreambox" - and "if that can't be fixed, we don't
# need to analyze the other problems" (flickering/artefacts/distortion are lower priority until this is understood). Pure diagnostic change, no
# behaviour change: nxpl_present() now times itself in two halves - CPU-side GL command submission (glUseProgram/glTexSubImage2D/glDrawArrays,
# just queuing work) versus eglSwapBuffers alone (the one call that can plausibly block on Nexus's own client/server IPC/compositor sync) - printed
# as a new "== nxpl: N presents, X ms avg upload+draw, Y ms avg eglSwapBuffers" summary line alongside the existing "== sink:" line. This answers a
# concrete, previously-unknown question: is the ~2x slowdown vs. the DM900 coming from THIS session's own new presentation code (fixable - upload
# is already row-range-limited since r20), or from something this code can't reach at all (the WebProcess's own Nexus-registered Skia GPU
# compositing, in place since patch 0017/r10, or a hardware clock difference between the two boxes - neither fixable the same way, if at all). No
# other change in r21 - the exact same code path as r20 otherwise, so a regression on DM900 is not expected (nxpl_on stays FALSE there; these new
# counters and the new print line are always no-ops on that path).
# r22: r21's real result (GigaBlue, 2026-09-23): "== nxpl: 239 presents, 8.94 ms avg upload+draw, 1.33 ms avg eglSwapBuffers" - decisive. Nexus's
# own IPC/compositor-sync (eglSwapBuffers) is NOT the bottleneck; the CPU-side GL submission (almost certainly glTexSubImage2D) is, at roughly
# 268 MB/s effective throughput for that session's average ~30%-of-screen-per-frame upload (heavy scrolling/navigation). This IS fixable in
# principle (it's a solvable engineering cost, not an architectural IPC ceiling) - but which fix is right depends on whether the cost is
# bandwidth-bound (scales with bytes moved -> upload less data, e.g. real 2D sub-rect tracking instead of just row-range) or has a large fixed
# per-call cost regardless of size (-> upload/swap less often instead). Pure diagnostic addition, no behaviour change: nxpl_present() now also
# tracks the min/max upload+draw time seen and the pixel count of each, printed as a second "== nxpl: upload+draw min ... max ..." line - if even
# the smallest updates are still slow, that points at fixed overhead; if only large updates are slow, that points at bandwidth, and picks the
# right next optimization instead of guessing.
# r23: r22's real result (GigaBlue, 2026-09-23): "== nxpl: upload+draw min 2.02 ms (138240 px), max 52.95 ms (2073600 px)" - clearly bandwidth-bound
# (min stays fast, cost scales with pixel count: ~15x more pixels -> ~26x more time), not fixed-per-call-overhead-bound. Fix: real 2D (x AND y)
# bounding-box dirty tracking (nxpl_dirty_x0/x1 added alongside the existing y0/y1), not just row-range. When the touched width is under 90% of
# the full screen width, compact those rows' x0..x1 slice into a reusable scratch buffer (nxpl_scratch) first - plain GLES2 has no
# GL_UNPACK_ROW_LENGTH to sub-range a row directly out of out.conv's packed buffer - and upload only that smaller region; CPU memcpy bandwidth is
# far higher than the ~150-270 MB/s this GPU upload path measured at, so paying for the compaction copy to shrink the upload is a real net win.
# Skipped above 90% width (compacting would just add CPU cost for negligible saving). The upload-size instrumentation from r21/r22 now reports the
# ACTUAL pixel count uploaded (compacted or full-width) instead of always the full-width figure, so the next capture shows real effectiveness.
# Still no other behaviour change - fb0 path (DM900/VU+) untouched.
# r24: r23's real result (GigaBlue, 2026-09-23): average improved slightly (8.25 ms vs ~8.9 ms) but MAX got dramatically worse per byte - 48.28 ms
# for only 67500 px, vs r22's pre-compaction 52.95 ms for a FULL 2073600 px screen (~30x fewer bytes, almost the same time). No longer looks
# bandwidth-bound for that case - leading theory: a narrow-but-tall compacted region crosses many more of this tiled GPU's internal texture tile
# boundaries per byte than a wide update does, so compaction can backfire for that specific shape even though it moves fewer bytes. Pure
# diagnostic addition again, no behaviour change: min/max now also record the actual uploaded width/height (not just total px, and fixed a
# reporting bug where the full-width branch was under-reporting px using the narrow dirty-rect width instead of out.ow) - the next capture shows
# the real shape directly instead of guessing again before deciding whether/how to special-case narrow-tall regions.
# r30: new defaults (tolerant, GPU painting, cover area 2, Skia cache 32 MB, texture pool 16 MB, no repeat limit); the red button needs an EGL 1.4 / GLES 2.0
# that enigma2 reports (else a message); the AIT is read and parsed in a thread from evStart on (aborted by evEnd); no disk/USB stick => storage in /tmp/wpe-data.
PR = "r33"
# r33: the plugin is in its own repository (OpenViX/HbbTv2.0, src/) and is called enigma2-plugin-systemplugins-hbbtv2 (Plugins/SystemPlugins/HbbTV2,
# /etc/enigma2/hbbtv2.conf, config.plugins.hbbtv2); the package upgrades enigma2-plugin-extensions-wpehbbtv. Versioned by the git revision from now on.
# r31 (cont.): Dreambox (/dev/vc5) always runs with WEBKIT_DISABLE_GL_FENCE=1, also in EGL mode (the fence flush crashed the web process in libvc5dream).
# r31: the orb:// scheme is registered as secure, so that an https application's orb://player iframe (the video/ads player) is not blocked as mixed content

# wpewebkit: the browser engine and its headers; orb: the engine, orb_host.h and the libraries wpe_fb is linked against
DEPENDS = "wpewebkit orb glib-2.0 libsoup gettext-native"
# wpe_fb loads the web process extension and the platform library of ORB by path (dlopen), which the shared library dependencies do not show.
# No "enigma2" here: it is always there on a box, and bitbake would build everything a package runtime-depends on (a whole enigma2 build).
# orb r4 or later: orb_host_key_set_mask (r3: orb_host_service_selected / orb_host_ait_section) (r2: orb_host_set_components / orb_host_set_component_handler).
# In orb_git.bb SRCREV is locked at "8945a1301ca036f8fbc973876476bb1af14c591d"
RDEPENDS:${PN} = "orb (>= 1.0+git385+8945a130+8945a1301c-r5) wpewebkit"

inherit gitpkgv pkgconfig

PLUGINDIR = "${libdir}/enigma2/python/Plugins/SystemPlugins/HbbTV2"

RPROVIDES:${PN} = "enigma2-plugin-extensions-wpehbbtv"
RREPLACES:${PN} = "enigma2-plugin-extensions-wpehbbtv"
RCONFLICTS:${PN} = "enigma2-plugin-extensions-wpehbbtv"

do_compile() {
    # dlopen names of the EGL/GLES libraries: the SONAMEs of the staged libraries the headers come with (not hardcoded in wpe_fb.c)
    soname() { ${READELF} -d "$1" 2>/dev/null | sed -n 's/.*(SONAME).*\[\(.*\)\]/\1/p'; }
    EGL_SO=$(soname ${STAGING_LIBDIR}/libEGL.so)
    GLES_SO=$(soname ${STAGING_LIBDIR}/libGLESv2.so)
    SONAME_DEFS=""
    [ -n "$EGL_SO" ] && SONAME_DEFS="$SONAME_DEFS -DWPE_FB_EGL_LIB=\"$EGL_SO\""
    [ -n "$GLES_SO" ] && SONAME_DEFS="$SONAME_DEFS -DWPE_FB_GLES_LIB=\"$GLES_SO\""
    ${CC} ${CFLAGS} ${LDFLAGS} -Wall $SONAME_DEFS ${S}/lib/wpe_fb.c -o ${B}/wpe_fb \
        -I${STAGING_INCDIR}/orb \
        $(pkg-config --cflags --libs wpe-webkit-2.0 wpe-platform-2.0 libsoup-3.0 gio-2.0 glib-2.0) \
        -lORBHost -lm -ldl
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/wpe_fb ${D}${bindir}/wpe_fb

    install -d ${D}${PLUGINDIR}/pages
    install -m 0644 ${S}/src/*.py ${D}${PLUGINDIR}/
    install -m 0644 ${S}/src/keymap.xml ${D}${PLUGINDIR}/keymap.xml
    install -m 0755 ${S}/src/run_wpe.sh ${D}${PLUGINDIR}/run_wpe.sh
    install -m 0644 ${S}/src/fonts.conf ${D}${PLUGINDIR}/fonts.conf
    install -m 0644 ${S}/src/pages/orb_hello.html ${D}${PLUGINDIR}/pages/orb_hello.html
    install -m 0644 ${S}/src/pages/video_test.html ${D}${PLUGINDIR}/pages/video_test.html

    find ${S}/po/ -maxdepth 1 -type f -name '*.po' | while read po ; do
        filename=${po##*/}
        cc=${filename%%.*}
        folder=${D}${PLUGINDIR}/locale/${cc}/LC_MESSAGES
        install -d ${folder}
        msgfmt -o ${folder}/hbbtv2.mo ${po}
    done
}

FILES:${PN} = "${bindir}/wpe_fb ${PLUGINDIR}"

pkg_postinst:${PN}() {
#!/bin/sh

# Create missing EGL/GLES library symlinks.
LIBDIR="${libdir}"

for group in \
    "libEGL.so.1 libEGL.so libv3ddriver.so" \
    "libGLESv2.so.2 libGLESv2.so libv3ddriver.so"
do
    set -- $group
    real=""

    # Find an existing library and resolve its real path.
    for n in "$@"; do
        if [ -e "$LIBDIR/$n" ]; then
            real=$(readlink -f "$LIBDIR/$n")
            break
        fi
    done

    # Create any missing names as symlinks to that file.
    if [ -n "$real" ] && [ -f "$real" ]; then
        for n in "$@"; do
            if [ ! -e "$LIBDIR/$n" ]; then
                ln -sf "$real" "$LIBDIR/$n"
            fi
        done
    fi
done

echo "***********************************"
echo "*          HbbTV 2.0 (WPE)        *"
echo "*   WPE WebKit + Open Red Button  *"
echo "*               by                *"
echo "*           xcentaurix            *"
echo "***********************************"
echo ""
echo "Plugin successfully installed."
echo "Please restart Enigma2 now!"
exit 0
}

pkg_postrm:${PN}() {
#!/bin/sh
# what is left after the files are gone: compiled python. Not on an upgrade, which runs this with something else than "remove".
if [ "$1" = "remove" ]; then
	rm -rf $D${libdir}/enigma2/python/Plugins/SystemPlugins/HbbTV2 > /dev/null 2>&1
fi
exit 0
}
