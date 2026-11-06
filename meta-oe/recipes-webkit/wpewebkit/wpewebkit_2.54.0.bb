require wpewebkit.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# 310886@main added an unconditional <stdatomic.h> to libpas pas_utils.h. GCC
# resolves it to its C stdatomic.h when pas_utils.h is pulled into a C++ unit,
# which fails with "'_Atomic' does not name a type". Upstream fixed this for
# xcodebuild only (312138@main), the CMake build has no fix yet. Drop this
# patch once https://github.com/WebKit/WebKit/pull/74158 lands.
#
# WebDriver fails to build when both LOG_DISABLED and RELEASE_LOG_DISABLED are
# set, which is the case for a release build without journald. Drop this patch
# once https://github.com/WebKit/WebKit/pull/74228 lands.
# The source is the unmodified wpewebkit-${PV}.tar.xz release (first commit, tagged wpewebkit-${PV}) with the OpenViX patches on top, one commit
# each, on the branch openvix/2.54. Patch numbering below refers to the commit order. Updating to a new release: import the new tarball on a
# new branch and rebase the patch commits onto it.
# The first two commits are build fixes that are not upstream yet:
# 310886@main added an unconditional <stdatomic.h> to libpas pas_utils.h. GCC
# resolves it to its C stdatomic.h when pas_utils.h is pulled into a C++ unit,
# which fails with "'_Atomic' does not name a type". Upstream fixed this for
# xcodebuild only (312138@main), the CMake build has no fix yet. Drop the
# commit once https://github.com/WebKit/WebKit/pull/74158 lands.
#
# WebDriver fails to build when both LOG_DISABLED and RELEASE_LOG_DISABLED are
# set, which is the case for a release build without journald. Drop the commit
# once https://github.com/WebKit/WebKit/pull/74228 lands.
SRC_URI = "git://github.com/OpenViX/wpewebkit.git;protocol=https;branch=openvix/2.54"
# Always the tip of the branch: only OpenViX pushes there. For a reproducible build, pin it instead: SRCREV = "<full commit hash>".
SRCREV = "${AUTOREV}"
PV = "2.54.0+git${SRCPV}"

# The licence files are in the repo exactly as in the tarball; LICENSE and LIC_FILES_CHKSUM (Source/WebCore/LICENSE-LGPL-2.1, relative to S)
# are set in wpewebkit.inc.

# HbbTV 2.0 experiment: minimal WPE WebKit for a box with only the vendor
# EGL/GLES2 (no DRM/GBM, no Wayland). Goal of step 1: render a page into a
# buffer through the WPE Platform API headless platform.

# OpenViX opts out of the "opengl" distro feature distro-wide (every box uses a
# vendor EGL), so features_check would skip this recipe. Bypass it for wpewebkit
# only instead of flipping a distro-wide feature, which would rebuild much of
# the image.
REQUIRED_DISTRO_FEATURES = ""

# "video" is required, not optional: upstream 2.54 does not build with
# ENABLE_VIDEO=OFF (JSHTMLMediaElementCustom.cpp is compiled unconditionally but
# JSHTMLMediaElement is only generated with video). HbbTV needs media anyway.
# mediasource/webaudio/mediastream stay off until needed.
# mediasource = Media Source Extensions (window.MediaSource): HbbTV 2.0 applications and the ORB polyfill play DASH through it
PACKAGECONFIG = "dfg-jit jit mediasource remote-inspector unified-builds video woff2 wpe-platform wpe-platform-headless"

# No DRM/GBM device on this box: USE_LIBDRM defaults ON upstream and then demands
# libdrm even though the recipe's "gbm" PACKAGECONFIG (which adds it) is off.
# The libdrm-dependent code is USE(LIBDRM)-guarded; frames go via SHM buffers.
EXTRA_OECMAKE:append = " -DUSE_LIBDRM=OFF"

# The vendor EGL has no GL for gst-gl, and gst-plugins-bad (mpegts) is not needed
# yet. Upstream still lists both as REQUIRED find_package() components with
# video on, so the GStreamerChecks commit (commit 3) only requires them when their option is ON
# (avoids rebuilding the image's gst-plugins-base with GL just for a check).

EXTRA_OECMAKE:append = " -DUSE_GSTREAMER_GL=OFF -DUSE_GSTREAMER_MPEGTS=OFF"

# Commit 4 (0002-AcceleratedBackingStore: guard DRM_FORMAT with USE(LIBDRM)): with -DUSE_LIBDRM=OFF, getImageInfoFromBuffer() still used DRM_FORMAT_XRGB8888
# (its include and the later ARGB8888 use are guarded, this one was not).

# Patches 0003-0022 (commits 5-24 on openvix/2.54):
# 0003-0006: vendor EGL (Broadcom V3D) without GBM/Mesa surfaceless: the default EGL display and the WPE platform fixes it needs.
# 0007: HbbTV - application/vnd.hbbtv.xhtml+xml is XHTML.
# 0008-0012: <video> plays on the hardware video plane (hole punch quirk for dvbvideosink/dvbaudiosink), decoder position and seeking,
#            decoder hand-over with the embedder, view scale, dry run for tests.
# 0013: frame read-back through an ION pixmap surface (4-9x faster than glReadPixels on the Dreambox V3D driver); Skia's readPixels() is the fallback.
# 0014: webkitwebsrc: a 416 for a range that starts at the end of the file is the end of the stream, not an error (an MP4 with a small box after a big
#       'mdat' made the demuxer ask for the byte at the size of the file: "Received 416 HTTP error code", the whole playback failed).
# 0015: webkitwebsrc: when a spurious at-EOF probe pre-empts a real, unserved request, surface a visible playback error instead of a silent
#       EOS (0014's own 416-to-EOS conversion was, unintentionally, also matching this case: the real content was never delivered, but the
#       whole element quietly ended as if it had). A safety net, not the actual fix - see 0016.
# 0016: never request unbounded "download" mode for progressive playback (use the same bounded ring buffer as blob URIs instead, and never
#       set GST_PLAY_FLAG_DOWNLOAD). This is the real fix for the >=4GiB-progressive-MP4-never-starts bug that 0014/0015 were only reacting
#       to symptoms of: for a multi-GiB file, downloadbuffer's own eager, unbounded prefetch starves qtdemux's small pull-mode probe reads
#       for tens of seconds to minutes (confirmed: 89s in one reproduction), during which nothing plays and nothing errors. Removing the
#       unbounded prefetch removes the starvation itself; as a side effect for progressive MP4, qtdemux also falls back to its simpler
#       push-mode/adapter state machine (transitions to MOVIE state right after moov, no full-file scan needed). 0014/0015 stay as a safety
#       net for any other timing collision on the same shared mutex; they should now rarely if ever trigger.
# 0017: joins Nexus (NxClient_Join) and registers the EGL memory/display interface (NXPL_RegisterNexusDisplayPlatform, libnxpl.so) before
#       any EGL call, fixing "Memory interface not registered" / eglInitialize abort on Nexus-based Broadcom boxes (GigaBlue gbquad4kpro).
#       v1 (r7) only hooked PlatformDisplayDefault::create() and still crashed on a real GigaBlue box - WebProcess::platformInitializeWebProcess()
#       actually constructs a PlatformDisplayLibWPE directly whenever a WPE Platform API renderer backend is in use (this fork's own wpe_fb
#       embedder always is), never touching PlatformDisplayDefault at all. v2 (r8) moves the logic into a shared header
#       (NexusEGLPlatformQuirk.h) and hooks both PlatformDisplayLibWPE::create() (the real path) and PlatformDisplayDefault::create() (kept as
#       a fallback). Pure runtime dlopen/dlsym detection, no link-time dependency - true no-op on the DM900 (libvc5dream, no Nexus at all),
#       regression-tested clean through both classes after the fix. v3 (r9) added dlerror()-based diagnostics to every failure branch and a
#       speculative libnexus.so preload; deployed to a real GigaBlue box, the diagnostics caught the actual cause: libnxpl.so's dlopen(RTLD_NOW)
#       failed on an undefined symbol, BEGL_PlatformAboutToShutdown (exported by libv3ddriver.so/libEGL.so, not yet loaded at that point because
#       this build's libepoxy lazily dlopens the real vendor EGL library only on the first actual EGL call, and the Nexus join/register logic
#       deliberately runs before any EGL call). v4 (r10) fixes it: explicitly preload an EGL-providing library (libEGL.so.1, libEGL.so, then
#       libv3ddriver.so as a last resort) immediately before dlopen("libnxpl.so"), mirroring the exact sequence already proven to work end-to-end
#       in tools/vuplus-probe/vu_egl_probe.c. Regression-tested clean on the DM900 (freshly reflashed); GigaBlue confirmation still pending.

# 0018: GLContext::createOffscreenContext() falls back to a pbuffer context when the EGL implementation has neither EGL_KHR_surfaceless_context
#       nor EGL_KHR_surfaceless_opengl (VU+ Ultimo 4K: Nexus V3D, GLES 2.0 only). Without it no offscreen GL context could be created there and the
#       web process died with a SIGSEGV (null Skia GL context) right after the Nexus join/register. Also guards that null Skia context.

# 0019: PlatformDisplaySurfaceless::create() joins/registers with Nexus (patch 0017's quirk) before its first EGL call. On the VU+ Ultimo 4K that call
#       (eglQueryString(EGL_NO_DISPLAY)) made the vendor libEGL write EGL_BAD_DISPLAY through a pthread key that was still 0 - i.e. into mimalloc's main
#       heap - and the WebProcess died in the next big allocation (JSC::VM::create). The actual cause of "WEB PROCESS TERMINATED (reason 0)" there.

# 0020: WEBKIT_COVER_AREA_MULTIPLIER / WEBKIT_SKIA_RESOURCE_CACHE_MB / WEBKIT_TEXTURE_POOL_MAX_MB. Nothing changes unless they are set. Black rectangles
#       on the GigaBlue = the shared 256 MB GPU heap running out (dmesg: BMMA_Alloc failed); WebKit's own limits only look at system RAM.

# 0021: Skia could not make a GPU context (GrDirectContexts::MakeGL null): the ThreadedCompositor dereferenced it (VU+ Ultimo 4K, WebProcess SIGSEGV after the
#       Nexus join). Now the driver's GL strings are logged (stderr = /tmp/wpe_fb.log) and the compositor is invalidated instead of crashing.

# 0022: why Skia refused the V3D and Mali drivers: GrGLInterface::validate() requires glGetFloatv, which the epoxy interface asked eglGetProcAddress() for
#       (Broadcom's libEGL returns NULL for core functions). It is now taken from epoxy like the other core functions.

# Vendor eglplatform.h falls back to X11 types unless BROADCOM_PLATFORM is set
# (see the libepoxy bbappend).
CFLAGS:append = " -DBROADCOM_PLATFORM"
CXXFLAGS:append = " -DBROADCOM_PLATFORM"

# KEPT (measured 2026-09-22, see memory: ~2.5% faster on a synthetic JS benchmark, builds and smoke-tests clean, no downside found).
# ENABLE_C_LOOP's CMake option is OFF for our target (WebKitFeatures.cmake wants JSC's native ARMv7 LLInt assembly interpreter, not the
# portable C++ CLoop one) - but an "off" WebKit CMake option never reaches the compiler as a -D flag at all (only "on" options get a bare,
# unvalued one), so PlatformEnable.h never sees it and falls back to deriving it itself: since ENABLE_JIT is unconditionally forced to 0 for
# our 32-bit value representation and the CPU is neither x86-64 nor ARM64, that fallback picks CLoop (1) - the slower portable interpreter,
# silently overriding what CMake actually wanted. This line forces the value CMake already intended, explicitly.
CFLAGS:append = " -DENABLE_C_LOOP=0"
CXXFLAGS:append = " -DENABLE_C_LOOP=0"

# The source is the tarball import plus the 24 patch commits (the four before 0003 and 0003-0022), in this order, up to the tip of the branch.
#
# Development mode (incremental rebuilds): OE wipes the build directory whenever SRC_URI (or SRCREV) changes, and every source fix would cost a
# clean ~2h rebuild. Instead, build from a persistent, already-patched tree with externalsrc, in local.conf like the other packages:
#     EXTERNALSRC:pn-wpewebkit = "/home/alpha/wpe-src/wpewebkit-2.54.0"
#     EXTERNALSRC_BUILD:pn-wpewebkit = "/home/alpha/wpe-src/build"
# (the tree = a clone of the repo on branch openvix/2.54, or the tarball + the patches made by tools/build/prep_src.sh). SRC_URI and SRCREV
# are ignored while it is set, but a change to either still changes the task signatures and re-runs do_configure, which
# deletes the build directory (cmake.bbclass: cleandirs = ${B}). Do not touch them while a dev build is in progress. Commit fixes in the clone,
# and push them (SRCREV follows the branch).

# WebKit compile jobs are memory hungry (unified builds: several GB each).
# -k 0: keep going after a failed compile so one run reports every error.
PARALLEL_MAKE = "-j 6 -k 0"

# r1: patch 0013 (fast frame read-back). A new package revision so that opkg upgrades the package on the box. PKGR, not PR: PR reaches
# do_configure, whose re-run would delete the development build directory (see above); PKGR only reaches the packaging tasks.
# r2: patch 0014 (416 at the end of the file).
# r3: EXPERIMENT, KEEP (measured 2026-09-22: real but small, ~2.5% faster on a synthetic JS benchmark; builds and smoke-tests clean, no downside found) -
# CFLAGS/CXXFLAGS += -DENABLE_C_LOOP=0, forcing JSC's native ARMv7 LLInt assembly interpreter instead of the portable CLoop it silently falls back to
# with JIT off. No source patch, so no PKGR-worthy change in the usual sense - bumped anyway so opkg can tell this build apart from r2.
# r4: TRIED AND REVERTED 2026-09-22 - restored the ARMv7/hardfp/Linux baseline-JIT ENABLE_JIT carve-out that existed through at least
# wpewebkit-2.52.0 and was silently dropped by 2.54.0. It built, linked, and ran (real ~1.6x JS speedup measured), and several genuine
# 32-bit-JIT bugs were found and fixed along the way - but a real HbbTV page (a Redux-style immutable-update helper doing a first-property
# write on a fresh object, AccessType::PutByValStrict) reliably crashes the WebProcess, and the exact cause was not found despite deep
# investigation (see memory: project_hbbtv2_wpe_orb_plan.md). Upstream WebKit has since deleted 32-bit JSVALUE32_64 support from the
# baseline JIT entirely rather than fix bugs like this one, so there is no newer reference implementation to compare against either.
# Reverted back to r3's behaviour (PlatformEnable.h's carve-out removed again); the JIT source-level fixes stay in the dev tree
# (/home/alpha/wpe-src/wpewebkit-2.54.0, all now dead code with ENABLE_JIT back at 0) in case this is revisited later.
# r5: patch 0015 (don't EOS a spurious at-EOF probe that pre-empted real content) - safety net, see r6.
# r6: patch 0016 (bounded buffering instead of unbounded "download" mode) - the real fix: >=4GiB progressive MP4 playback silently never
# starting, verified 2026-09-23 against a synthetic 5,014,522,180-byte file (matching a real failing ZDF film's size) and its
# 4,238,966,549-byte (working) counterpart; both now reach MOVIE state and deliver frames within ~5s, reproducibly.
# r7: patch 0017 v1 - BROKEN, DO NOT USE. Only hooked PlatformDisplayDefault::create(); installed on a real GigaBlue box (via
# opkg install --force-depends, see below) and the WebProcess still crashed with the identical "Memory interface not registered" abort,
# because PlatformDisplayLibWPE::create() (the class actually constructed whenever a WPE Platform API renderer backend is in use, i.e.
# always for this fork's own wpe_fb embedder) never goes through PlatformDisplayDefault at all. Left installed on the box briefly during
# diagnosis; superseded by r8 below.
# r8: patch 0017 v2 - real progress but not the whole fix. Same join+register sequence, now in a shared header
# (NexusEGLPlatformQuirk.h) called from BOTH PlatformDisplayLibWPE::create() (the real path) and PlatformDisplayDefault::create()
# (fallback). Regression-tested clean on DM900. On a real GigaBlue box: NxClient_Join() confirmed running and returning 0 (real
# progress - WebProcess got as far as loading a page and starting an ORB app before crashing, vs. immediately before), but the
# expected NXPL_RegisterNexusDisplayPlatform() log line never appeared and v2 had no diagnostics for why - see r9.
# NOTE (opkg on non-DM900 boxes, two real gotchas hit installing r7/r8 on GigaBlue): (1) libwpewebkit-2.0-1's auto-generated
# RDEPENDS on "libgles2" does not resolve on GigaBlue's own feed - it provides the same libEGL.so/libGLESv2.so functionality via
# its own gb-v3ddriver-gb7252pro package under a different name/Provides (a real packaging gap, no GigaBlue-specific MACHINE/BSP
# build exists in this project yet; `opkg install --force-depends` is the workaround). (2) `--force-depends` only skips the
# dependency check for packages named ON THE COMMAND LINE - it does not transitively force-upgrade libwpewebkit-2.0-1 itself
# unless THAT package is named explicitly too, and plain `opkg install <name>` never upgrades an already-installed package
# regardless (only `opkg upgrade` compares versions) - this cost a full round of "testing r7/r8" that was actually still
# exercising the pre-project r0 build. Install/upgrade this package by pointing opkg directly at the .ipk file, or use
# `opkg upgrade --force-depends libwpewebkit-2.0-1` after `opkg update`; verify with `opkg list-installed | grep wpewebkit`.
# r9: patch 0017 v3 - diagnostics + a real candidate fix. Every dlopen/dlsym failure branch now logs via dlerror()/a named
# message (v2 went completely silent on the libnxpl.so step failing, with NxClient_Join's own success proving the code runs).
# Also added an explicit dlopen("libnexus.so") before libnxpl.so, since libnxpl.so's own symbol table imports many libnexus.so
# symbols that RTLD_NOW must resolve immediately - a real, evidence-backed candidate for why libnxpl.so's dlopen was failing
# where libnxclient.so's did not. Regression-tested clean on DM900 (all three new dlopen attempts fail cleanly there, as
# expected, no crash). GigaBlue confirmation of r9, this time verified actually installed via `opkg list-installed`, found the
# real cause: dlopen("libnxpl.so") failed with "undefined symbol: BEGL_PlatformAboutToShutdown" - see r10.
# r10: patch 0017 v4 - the actual fix. BEGL_PlatformAboutToShutdown is exported by libv3ddriver.so/libEGL.so, which this
# build's libepoxy only dlopens lazily on the first real EGL call - but ensureNexusPlatformJoined() deliberately runs BEFORE
# any EGL call, so that library isn't loaded yet when libnxpl.so's own dlopen(RTLD_NOW) needs it. Explicitly preload an
# EGL-providing library (tries libEGL.so.1, then libEGL.so, then libv3ddriver.so) immediately before dlopen("libnxpl.so"),
# mirroring the exact sequence already verified end-to-end in tools/vuplus-probe/vu_egl_probe.c. Regression-tested clean on a
# freshly reflashed DM900 (preload finds the DM900's own libEGL.so harmlessly; libnxpl.so still fails cleanly since it
# doesn't exist there; page loads normally, no regression). GigaBlue confirmation still pending - this is the critical
# remaining validation step for the whole 0017 saga.
# r13: patch 0018. The VU+ Ultimo 4K EGL (libv3ddriver.so, GLES 2.0) has no EGL_KHR_surfaceless_context: WebKit could not create any offscreen
# context, Skia's GL context stayed null and the web process crashed (silently, SIGSEGV) right after the Nexus join/register of 0017.
# 0018 falls back to the existing pbuffer context in that case and guards the null Skia context. Other boxes are unaffected.
# r14: patch 0019. The WebProcess of the VU+ Ultimo 4K crashed in mimalloc: its first EGL call (PlatformDisplaySurfaceless::create) ran before the Nexus
# join/register, so the vendor EGL stored an error code through an uninitialised pthread key into mimalloc's main heap. 0019 joins/registers first.
# r15: patch 0020 (runtime GPU memory limits, off by default).
# r16: patch 0021 (diagnostic + no crash when Skia refuses the GL driver).
# r17: patch 0022 (Skia gets a GPU context on the Broadcom V3D driver).
PKGR = "r17"
